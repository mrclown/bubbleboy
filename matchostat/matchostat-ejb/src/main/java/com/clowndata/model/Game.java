package com.clowndata.model;

/**
 * Created 2014.
 */

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    Long id;

    @XmlAttribute
    @OneToOne
    private Team team1;

    @XmlAttribute
    @OneToOne
    private Team team2;

    private Date gameStart;

    private Date gameEnd;

    public Game() {
        this(null, null);
    }

    public Game(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
        gameStart = new Date();
    }

    public void setTeam1(Team team1) {

        this.team1 = team1;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
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
}
