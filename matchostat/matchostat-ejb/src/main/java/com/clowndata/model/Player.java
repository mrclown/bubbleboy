package com.clowndata.model;

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

    public void addGameEvent(Game game, GameEvent gameEvent) {
        if (gameEvent.getEventType() == GameEvent.SCORE) {
            game.increaseScoreForTeamWithPlayer(this);
        }
        this.gameEvents.add(gameEvent);
  }
}
