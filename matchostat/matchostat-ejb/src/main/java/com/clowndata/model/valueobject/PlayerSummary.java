package com.clowndata.model.valueobject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 2014.
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class PlayerSummary {

    @XmlAttribute
    private int points;

    @XmlAttribute
    private int goals;

    @XmlAttribute
    private String name;

    public PlayerSummary(String name, int goals, int points) {
        this.name = name;
        this.goals = goals;
        this.points = points;
    }

    public String getName() {
        return this.name;
    }

    public int getGoals() {
        return this.goals;
    }

    public int getPoints() {
        return this.points;
    }

}
