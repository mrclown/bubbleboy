package com.clowndata.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

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
    Long id;

    @XmlAttribute
    @NotNull
    @Size(min = 1, max = 20)
    String name;

    public Player() {

    }

    public Player(String name) {
        this.name = name;
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
}
