package com.clowndata.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created 2014.
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
@Entity
@NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t")
public class Team {

    @XmlAttribute
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @XmlAttribute
    @ManyToMany
//    @ElementCollection(fetch = FetchType.EAGER)
    Set<Player> players = new HashSet<Player>();

    @XmlAttribute
    int score;

    public Team() {
        this.score = 0;
    }

    public Team(Set<Player> players) {
        this.players = players;
        this.score = 0;
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        //TODO: Do not allow two players with same name
        players.add(player);
    }

    public int getScore() {
        return score;
    }

    public Long getId() {
        return id;
    }

    public void increaseScore() {
        this.score++;
    }

    public boolean isEventByPlayerInTeam(GameEvent gameEvent) {
        boolean result = false;

        for (Player player : players) {
            if (player.hasEvent(gameEvent)) {
                result = true;
                break;
            }
        }

        return result;
    }
}
