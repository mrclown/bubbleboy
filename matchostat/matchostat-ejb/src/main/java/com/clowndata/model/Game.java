package com.clowndata.model;

/**
 * Created 2014.
 */

import com.clowndata.util.DateDeserializer;
import com.clowndata.util.DateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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

    @JsonSerialize(using = DateSerializer.class)
    public Date getGameStart() {
        return gameStart;
    }

    @JsonDeserialize(using = DateDeserializer.class)
    public void setGameStart(Date gameStart) {
        this.gameStart = gameStart;
    }

    public void increaseScoreForTeamWithPlayer(Player player) {
        if (team1.getPlayers().contains(player)) {
            team1.increaseScore();
        } else if (team2.getPlayers().contains(player)) {
            team2.increaseScore();
        } else {
            //TODO log
        }
    }
}
