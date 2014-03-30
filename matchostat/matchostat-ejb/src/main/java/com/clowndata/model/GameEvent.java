package com.clowndata.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by 2014.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
@Entity
public class GameEvent {

    public static final int SCORE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    private Long id;

    @ManyToOne
    private Game game;

    @XmlAttribute
    private int eventType;

    @XmlAttribute
    private Date eventTime;

    //TODO: clean up constructors
    public GameEvent() {
    }

    public GameEvent(int eventType) {
        this.eventType = eventType;
        this.eventTime = new Date();
    }

    public GameEvent(Date eventTime, int eventType) {
        this.eventTime = eventTime;
        this.eventType = eventType;
    }

    public GameEvent(Game game, int eventType) {
        this.game = game;
        this.eventType = eventType;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Long getId() {
        return id;
    }
}
