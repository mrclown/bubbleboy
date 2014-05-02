package com.clowndata.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Transient
    final Logger log = LoggerFactory.getLogger(GameEvent.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    private Long id;

    @NotNull
    @ManyToOne
    private Game game;

    @XmlAttribute
    private int eventType;

    @NotNull
    @XmlAttribute
    private Date eventTime;

    @OneToOne
    @XmlAttribute
    protected GameEvent gameEventLink;


    public GameEvent() {
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
            String msg = "Not possible to create a GameEvent not within the Game time";
            log.error(msg);
            throw new IllegalStateException(msg);
        }

        this.game = game;
        this.eventType = eventType;
        this.gameEventLink = gameEventLink;
        this.eventTime = eventTime;
    }

    public Game getGame() {
        return this.game;
    }

    public int getEventType() {
        return eventType;
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

    public static GameEvent createGameEvent(Game game, GameEvent event) {

        GameEvent gameEvent;

        if (event.getEventTime() == null) {
            gameEvent = new GameEvent(game, event.getEventType(), event.getGameEventLink());
        } else {
            gameEvent = new GameEvent(game, event.getEventType(), event.getEventTime(), event.getGameEventLink());
        }

        return gameEvent;
    }
}
