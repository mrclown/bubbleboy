package com.clowndata.model;

/**
 * Created 2014.
 */

import com.clowndata.util.DateDeserializer;
import com.clowndata.util.DateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

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

    //todo: bean validation to prohibit team = 0
    @XmlAttribute
    @OneToOne
    private Team team1;

    @XmlAttribute
    @OneToOne
    private Team team2;

    //todo: bean validation to prohibit gameStart = 0
    @XmlAttribute
    private Date gameStart;

    @XmlAttribute
    private Date gameEnd;

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

    @JsonDeserialize(using = DateDeserializer.class)
    public void setGameStart(Date gameStart) {
        this.gameStart = gameStart;
    }

    public boolean isGameEnded() {
        return this.gameEnd != null;
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

        setGameStartAndEnd(game.gameStart, game.gameEnd);

        Team persistedTeam = this.getTeam1();
        if (persistedTeam != null) {
            updateTeam(persistedTeam, game.getTeam1());
        }

        persistedTeam = this.getTeam2();
        if (persistedTeam != null) {
            updateTeam(persistedTeam, game.getTeam2());
        }

        log.info("Updated Game: " + this.id);
    }

    private void updateTeam(Team persistedTeam, Team team) {
        persistedTeam.setPlayers(team.getPlayers());
    }

    public void setGameStartAndEnd(Date gameStart, Date gameEnd) {

        if (gameStart == null) {
            //todo: use bean validation
            throw new IllegalStateException("Game start cannot be null");
        }

        if (gameEnd == null) {
            this.gameStart = gameStart;
            this.gameEnd = null;
        } else {
            if (gameEnd.getTime() < gameStart.getTime()) {
                String msg = "Game end: " + gameEnd.toString() + " cannot be before Game start: " + gameStart.toString();
                log.error(msg);
                throw new IllegalStateException(msg);
            }
            this.gameStart = gameStart;
            this.gameEnd = gameEnd;
        }
    }
}

