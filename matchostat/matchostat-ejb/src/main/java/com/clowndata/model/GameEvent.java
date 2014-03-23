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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    private Long id;

    private Long gameId;

    @XmlAttribute
    private String eventType;

    @XmlAttribute
    private Date eventTime;

    public GameEvent() {
    }

    public GameEvent(String eventType) {
        this.eventType = eventType;
        this.eventTime = new Date();
    }

    public GameEvent(Date eventTime, String eventType) {
        this.eventTime = eventTime;
        this.eventType = eventType;
    }

    public GameEvent(Long gameId, Date eventTime, String eventType) {
        this.gameId = gameId;
        this.eventTime = eventTime;
        this.eventType = eventType;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
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
