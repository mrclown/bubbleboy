package com.clowndata.model.valueobject;

import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;

/**
 * Created by 2014.
 */

//TODO: Remove class? Not really used
public class GameEventAssist extends GameEvent {
    public GameEventAssist(Game game, GameEventGoal relatedGoal) {
        super(game, GameEvent.ASSIST);
        this.gameEventLink = relatedGoal;
    }
}
