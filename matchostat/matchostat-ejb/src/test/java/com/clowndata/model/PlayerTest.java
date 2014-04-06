package com.clowndata.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by 2014.
 */
public class PlayerTest {


    Game game;
    Team replicants;
    Team bladerunners;
    Player zora;
    Player pris;
    Player deckard;

    @Before
    public void createTestData() {

        replicants = new Team();
        bladerunners = new Team();

        zora = new Player("Zora");
        pris = new Player("Pris");
        replicants.addPlayer(zora);
        replicants.addPlayer(pris);

        deckard = new Player("Deckard");
        bladerunners.addPlayer(deckard);

        game = new Game(replicants, bladerunners);
    }

    @Test
    public void testPlayerScores() {

        assertEquals(0, replicants.getScore());
        assertEquals(0, bladerunners.getScore());

        zora.addGameEvent(new GameEvent(game, GameEvent.GOAL));

        assertEquals(1, replicants.getScore());
        assertEquals(0, bladerunners.getScore());

        zora.addGameEvent(new GameEvent(game, GameEvent.GOAL));

        assertEquals(2, replicants.getScore());
        assertEquals(0, bladerunners.getScore());

        deckard.addGameEvent(new GameEvent(game, GameEvent.GOAL));

        assertEquals(2, replicants.getScore());
        assertEquals(1, bladerunners.getScore());
    }

    @Test
    public void testPlayerPoints() {

        assertEquals(0, zora.getPoints(game));
        assertEquals(0, pris.getPoints(game));
        assertEquals(0, deckard.getPoints(game));

        zora.addGameEvent(new GameEvent(game, GameEvent.GOAL));

        assertEquals(1, zora.getPoints(game));
        assertEquals(0, pris.getPoints(game));
        assertEquals(0, deckard.getPoints(game));

        pris.addGameEvent(new GameEvent(game, GameEvent.ASSIST));
        deckard.addGameEvent(new GameEvent(game, GameEvent.GOAL));
        pris.addGameEvent(new GameEvent(game, GameEvent.GOAL));

        assertEquals(1, zora.getPoints(game));
        assertEquals(2, pris.getPoints(game));
        assertEquals(1, deckard.getPoints(game));

        assertEquals(2, replicants.getScore());
        assertEquals(1, bladerunners.getScore());
    }

}
