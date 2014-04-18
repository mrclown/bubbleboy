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
    @NotNull
    @Size(min = 1, max = 20)
    private String name;

    @XmlAttribute
    private Boolean active;

    @JsonIgnore
    @OneToMany
    private List<GameEvent> gameEvents;

    public Player() {
        this(null);
    }

    public Player(String name) {
        this.name = name;
        this.active = true;
        this.gameEvents = new ArrayList<>();
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

    public boolean addGameEvent(GameEvent gameEvent) {

        boolean addEvent = true;

        switch (gameEvent.getEventType()) {
            case GameEvent.GOAL:
                gameEvent.getGame().increaseScoreForTeamWithPlayer(this);
                // a goal cannot be linked
                gameEvent.setGameEventLink(null);
                break;
            case GameEvent.ASSIST:
                this.getGameEvents().size();
                GameEvent linkedEvent = gameEvent.getGameEventLink();
                if ((linkedEvent == null) || (linkedEvent.getEventType() != gameEvent.GOAL)) {
                    addEvent = false;
                    log.error("The assist by player: " + this.getName() + " id: " + this.getId() + " is not linked to a goal");
                } else {
                    Game game = linkedEvent.getGame();
                    if (!game.areEventsByPlayersWithinTheSameTeam(gameEvent, linkedEvent)) {
                        addEvent = false;
                        log.error("The assist must be to a player within the same team and same game");
                    }
                }
                break;
            default:

        }

        if (addEvent) {
            this.gameEvents.add(gameEvent);
        }

        return addEvent;
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

    @JsonIgnore
    public boolean isDeletable() {
        return ((this.gameEvents == null) || (this.gameEvents.size() == 0));
    }
}
