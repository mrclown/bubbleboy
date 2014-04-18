package com.clowndata.model;

import org.junit.Test;

import java.util.Date;

/**
 * Created by 2014.
 */
public class GameEventTest {

    @Test(expected = IllegalStateException.class)
    public void testNotPossibleWithEventsBeforeGame() {

        Game game = new Game();

        Date date = new Date();
        date.setTime(111);
        new GameEvent(game, GameEvent.GOAL, date);
    }

    @Test(expected = IllegalStateException.class)
    public void testNotPossibleWithEventsAfterGame() {

        Game game = new Game();

        Date gameEnd = new Date();
        gameEnd.setTime(gameEnd.getTime() + 10000);
        game.setGameEnd(gameEnd);

        Date date = new Date();
        date.setTime(date.getTime() + 20000);
        new GameEvent(game, GameEvent.GOAL, date);
    }

}
