package com.clowndata.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 2014.
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
@Entity
@NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p")
public class Player {

    @Transient
    final Logger log = LoggerFactory.getLogger(Player.class);

    @XmlAttribute
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @XmlAttribute
    @Size(min = 2, max = 20)
    private String name;

    @XmlAttribute
    @NotNull
    private Boolean active;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<GameEvent> gameEvents = new ArrayList<>();

    public Player() {
        this(null);
    }

    public Player(String name) {
        this.name = name;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public List<GameEvent> getGameEvents() {
        return gameEvents;
    }

    public void addGameEvent(GameEvent gameEvent) {

        Game game = gameEvent.getGame();

        game.addGameEvent(gameEvent);

        Team team = gameEvent.getGame().getTeamWithPlayer(this);

        if (team == null) {
            String msg = "Player id: " + this.id + " is not in the Game: " + gameEvent.getGame().getId();
            log.error(msg);
            throw new IllegalStateException(msg);
        }
        switch (gameEvent.getEventType()) {
            case GameEvent.GOAL:
                team.increaseScore();
                // a goal cannot be linked
                gameEvent.setGameEventLink(null);
                break;
            case GameEvent.ASSIST:
                GameEvent linkedEvent = gameEvent.getGameEventLink();
                if ((linkedEvent == null) || (linkedEvent.getEventType() != gameEvent.GOAL)) {
                    String msg = "The assist by player: " + this.getName() + " id: " + this.getId() + " is not linked to a goal";
                    log.error(msg);
                    throw new IllegalStateException(msg);
                } else {
                    Game linkedGame = linkedEvent.getGame();
                    if (!linkedGame.isEventByPlayerWithinTheSameTeam(team, linkedEvent)) {
                        String msg = "The assist by player: " + this.getName() + " id: " + this.getId() + " is not to a player in the same team";
                        log.error(msg);
                        throw new IllegalStateException(msg);
                    }
                }
                break;
            case GameEvent.OWNGOAL:
                game.increaseScoreForOppositeTeam(team);
                // an own goal cannot be linked
                gameEvent.setGameEventLink(null);
                break;
            default:

        }
        this.gameEvents.add(gameEvent);

    }

    public int getGoals(Game game) {

        int goals = 0;

        for (GameEvent gameEvent : getGameEvents()) {
            if (gameEvent.getGame().equals(game)) {
                if (gameEvent.getEventType() == GameEvent.GOAL) {
                    goals++;
                }
            }
        }

        return goals;
    }

    public int getPoints(Game game) {

        int points = 0;

        for (GameEvent gameEvent : getGameEvents()) {
            if (gameEvent.getGame().equals(game)) {
                if ((gameEvent.getEventType() == GameEvent.GOAL) || (gameEvent.getEventType() == GameEvent.ASSIST)) {
                    points++;
                }
            }
        }

        return points;
    }

    public List<GameEvent> deleteEventsForPlayerInGame(Long gameId) {

        List<GameEvent> gameEventsToDelete = new ArrayList<>();

        for (GameEvent gameEvent : gameEvents) {
            if (gameEvent.getGame().getId() == gameId) {
                gameEventsToDelete.add(gameEvent);
            }
        }

        deleteGameEvents(gameEventsToDelete);

        return gameEventsToDelete;
    }

    private void deleteGameEvents(List<GameEvent> gameEventsToDelete) {

        log.info("Deleting events for player id: " + id);

        for (GameEvent gameEvent : gameEventsToDelete) {
            gameEvents.remove(gameEvent);
        }
    }


    @JsonIgnore
    public boolean isDeletable() {
        return ((this.gameEvents == null) || (this.gameEvents.size() == 0));
    }

    public boolean hasEvent(GameEvent gameEvent) {
        return gameEvents.contains(gameEvent);
    }

    public void updatePlayer(Player player) {
        log.info("Updating player: " + this.getName() + " id: " + id);
        this.setName(player.getName());
        this.setActive(player.getActive());
        log.info("Updated player: " + player.getName() + " id: " + id + " active: " + player.getActive());
    }
}
