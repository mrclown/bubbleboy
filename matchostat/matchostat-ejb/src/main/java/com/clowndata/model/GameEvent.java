package com.clowndata.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Date;

/**
 * Created by 2014.
 */
@Entity
public class GameEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    Long id;

    @OneToOne
    Game game;

    private Date eventTime;

    public GameEvent(Game game, Date eventTime) {
        this.game = game;
        this.eventTime = eventTime;
    }

}
