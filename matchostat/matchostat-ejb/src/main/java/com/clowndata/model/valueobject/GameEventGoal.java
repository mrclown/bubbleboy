package com.clowndata.model.valueobject;

import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;

/**
 * Created by 2014.
 */

//TODO: Remove class? Not really used
public class GameEventGoal extends GameEvent {

    public GameEventGoal(Game game) {
        super(game, GameEvent.GOAL);
    }
}
