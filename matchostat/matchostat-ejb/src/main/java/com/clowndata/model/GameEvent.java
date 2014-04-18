package com.clowndata.model;

import com.clowndata.util.DateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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

    public static final int GOAL = 0;
    public static final int ASSIST = 1;
    public static final int OWNGOAL = 2;

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

    @OneToOne
    protected GameEvent gameEventLink;

    public GameEvent() {
    }

    public GameEvent(Long id) {
        this.id = id;
    }

    public GameEvent(Game game, int eventType) {
        this(game, eventType, new Date(), null);
    }

    public GameEvent(Game game, int eventType, Date eventTime) {
        this(game, eventType, eventTime, null);
    }

    public GameEvent(Game game, int eventType, GameEvent gameEventLink) {
        this(game, eventType, new Date(), gameEventLink);
    }

    private GameEvent(Game game, int eventType, Date eventTime, GameEvent gameEventLink) {

        if (!game.isWithinGame(eventTime)) {
            //Todo: fix exception
            throw new IllegalStateException();
        }

        this.game = game;
        this.eventType = eventType;
        this.gameEventLink = gameEventLink;
        this.eventTime = eventTime;
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

    //    @JsonSerialize(using = DateSerializer.class)
    public Date getEventTime() {
        return eventTime;
    }

    //    @JsonSerialize(using = DateSerializer.class)
    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Long getId() {
        return id;
    }

    public GameEvent getGameEventLink() {
        return gameEventLink;
    }

    public void setGameEventLink(GameEvent gameEventLink) {
        this.gameEventLink = gameEventLink;
    }
}
