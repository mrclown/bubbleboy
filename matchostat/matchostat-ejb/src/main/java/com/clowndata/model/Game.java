package com.clowndata.model;

/**
 * Created 2014.
 */

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
@Entity
@NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g")
public class Game {

    public void setTeam1(Team team1) {

        this.team1 = team1;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

/*    public void setId(Long id) {
        this.id = id;
    }
*/
    @XmlAttribute
//    @NotNull
    @OneToOne
    private Team team1;

    @XmlAttribute
//    @NotNull
    @OneToOne
    private Team team2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    private Long id;

    public Game() {
    }

    public Game(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;

        //TODO: replace code
        //this.id = new Long(10);
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
