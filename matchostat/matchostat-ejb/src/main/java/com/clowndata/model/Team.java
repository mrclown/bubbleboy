package com.clowndata.model;

import javax.persistence.*;
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
public class Team {

    @XmlAttribute
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @XmlAttribute
    @ManyToMany
//    @ElementCollection(fetch=FetchType.EAGER)
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

    public void printTeam() {
        for (Player player : this.players) {
            System.err.println(player.getId());
        }
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

}
