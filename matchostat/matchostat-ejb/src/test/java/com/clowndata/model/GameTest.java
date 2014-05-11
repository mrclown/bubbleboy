package com.clowndata.model;

import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by 2014.
 */
public class GameTest {

    @Test
    public void testGameEnded() {

        Game game = new Game(new Team(), new Team());

        assertFalse(game.isGameEnded());

        game.setGameEnd(new Date());

        assertTrue(game.isGameEnded());
    }

    @Test
    public void testTimeWithinGame() {

        Game game = new Game(new Team(), new Team());

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

    @Test
    public void testUpdateGame() {

        Set<Player> players = new HashSet<>();
        Player kalle = new Player("Kalle");
        players.add(kalle);
        Team team = new Team(players);
        team.increaseScore();

        Date gameStart = new Date();
        gameStart.setTime(100000);

        Game game = new Game(team, new Team(), gameStart);

        Date newGameStart = new Date();
        newGameStart.setTime(150000);
        Game newGame = new Game(new Team(), new Team(players), newGameStart);
        Date gameEnd = new Date();
        gameEnd.setTime(200000);
        newGame.setGameEnd(gameEnd);

        game.updateGame(newGame);

        // gameStart is never updated
        assertEquals(100000, game.getGameStart().getTime());
        assertEquals(200000, game.getGameEnd().getTime());
        assertTrue(game.isGameEnded());
        assertEquals(0, game.getTeam1().players.size());
        assertEquals(1, game.getTeam2().players.size());
        //Score is not updated
        assertEquals(1, game.getTeam1().getScore());
        assertEquals(0, game.getTeam2().getScore());
    }

    @Test
    public void testDeleteEventsForPlayers() {
        Set<Player> players = new HashSet<>();
        Player kalle = new Player("Kalle");
        players.add(kalle);
        Team team = new Team(players);
        Game game = new Game(team, new Team());
        GameEvent goal = new GameEvent(game, GameEvent.GOAL);
        kalle.addGameEvent(goal);

        assertEquals(1, kalle.getGameEvents().size());
        assertEquals(1, game.getGameEvents().size());
        game.deleteGameEvents();
        assertEquals(0, kalle.getGameEvents().size());
        assertEquals(0, game.getGameEvents().size());
    }
}
