package com.clowndata.model;

/**
 * Created 2014.
 */

import com.clowndata.util.DateDeserializer;
import com.clowndata.util.DateSerializer;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
@Entity
@NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g")
public class Game {

    @Transient
    final Logger log = LoggerFactory.getLogger(Game.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    Long id;

    @XmlAttribute
    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Team team1 = new Team();

    @XmlAttribute
    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Team team2 = new Team();

    @XmlAttribute
    @NotNull
    private Date gameStart;

    @XmlAttribute
    private Date gameEnd;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<GameEvent> gameEvents = new ArrayList<>();

    public Game() {
        this(new Team(), new Team(), new Date());
    }

    public Game(Team team1, Team team2) {
        this(team1, team2, new Date());
    }

    public Game(Team team1, Team team2, Date gameStart) {
        this.team1 = team1;
        this.team2 = team2;
        this.gameStart = gameStart;
        this.gameEnd = null;
    }

    public Team getTeam1() {
        return this.team1;
    }

    public Team getTeam2() {
        return this.team2;
    }

    public Long getId() {
        return id;
    }

    @JsonSerialize(using = DateSerializer.class)
    public Date getGameStart() {
        return gameStart;
    }

    @JsonDeserialize(using = DateDeserializer.class)
    public void setGameEnd(Date gameEnd) {
        this.gameEnd = gameEnd;
    }

    @JsonSerialize(using = DateSerializer.class)
    public Date getGameEnd() {
        return gameEnd;
    }

    @JsonIgnore
    public boolean isGameEnded() {
        return this.gameEnd != null;
    }

    @JsonIgnore
    public List<GameEvent> getGameEvents() {
        return this.gameEvents;
    }

    public boolean isWithinGame(Date time) {

        return ((gameStart.getTime() <= time.getTime())) && ((gameEnd == null) || (gameEnd.getTime() >= time.getTime()));
    }

    public void increaseScoreForOppositeTeam(Team team) {
        if (team.equals(team1)) {
            team2.increaseScore();
        } else {
            team1.increaseScore();
        }
    }

    public boolean isEventByPlayerWithinTheSameTeam(Team team, GameEvent event) {

        Team eventTeam = null;

        if (team1.isEventByPlayerInTeam(event)) {
            eventTeam = team1;
        } else if (team2.isEventByPlayerInTeam(event)) {
            eventTeam = team2;
        }
        return team.equals(eventTeam);
    }

    public Team getTeamWithPlayer(Player player) {

        Team team = null;

        if (team1.getPlayers().contains(player)) {
            team = team1;
        } else if (team2.getPlayers().contains(player)) {
            team = team2;
        }
        return team;
    }

    public void updateGame(Game game) {
        // gameStart is ignored in updates
        updateGameEnd(game.gameEnd);

        Team persistedTeam = this.getTeam1();
        persistedTeam.setPlayers(game.getTeam1().getPlayers());

        persistedTeam = this.getTeam2();
        persistedTeam.setPlayers(game.getTeam2().getPlayers());

        log.info("Updated Game: " + this.id);
    }

    public void updateGameEnd(Date gameEnd) {
        if (gameEnd == null) {
            this.gameEnd = null;
        } else {
            if (gameEnd.getTime() < this.gameStart.getTime()) {
                String msg = "Game end: " + gameEnd.toString() + " cannot be before Game start: " + this.gameStart.toString();
                log.error(msg);
                throw new IllegalStateException(msg);
            }
            this.gameEnd = gameEnd;
        }
    }

    public void addGameEvent(GameEvent gameEvent) {
        this.gameEvents.add(gameEvent);
    }

    public void deleteGameEvents() {

        for (Player player : team1.getPlayers()) {
            player.deleteEventsForPlayerInGame(id);
        }

        for (Player player : team2.getPlayers()) {
            player.deleteEventsForPlayerInGame(id);
        }
        this.gameEvents.clear();
    }

    @JsonIgnore
    public int getNumberOfOwnGoals() {
        int nrOfOwnGoals = 0;

        for (GameEvent gameEvent : this.gameEvents) {
            if (gameEvent.getEventType() == GameEvent.OWNGOAL) {
                nrOfOwnGoals++;
            }
        }

        return nrOfOwnGoals;
    }
}

