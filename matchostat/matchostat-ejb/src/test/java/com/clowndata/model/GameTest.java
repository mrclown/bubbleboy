package com.clowndata.model;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by 2014.
 */
public class GameTest {

    @Test
    public void testGameEnded() {

        Game game = new Game();

        assertFalse(game.isGameEnded());

        game.setGameEnd(new Date());

        assertTrue(game.isGameEnded());
    }

    @Test
    public void testTimeWithinGame() {

        Game game = new Game();

        Date beforeGame = new Date();
        beforeGame.setTime(beforeGame.getTime() - 100000);
        assertFalse(game.isWithinGame(beforeGame));

        Date inGame = new Date();
        inGame.setTime(inGame.getTime() + 100000);
        assertTrue(game.isWithinGame(inGame));

        Date endGame = new Date();
        endGame.setTime(endGame.getTime() + 200000);
        game.setGameEnd(endGame);

        Date afterGame = new Date();
        afterGame.setTime(afterGame.getTime() + 300000);
        game.setGameEnd(endGame);
        assertFalse(game.isWithinGame(afterGame));
    }

}
