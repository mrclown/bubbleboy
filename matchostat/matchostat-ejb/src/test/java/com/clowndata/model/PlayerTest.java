package com.clowndata.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by 2014.
 */
public class PlayerTest {

    @Test
    public void testPlayerScores(){

        Team replicants = new Team();
        Team bladerunners = new Team();

        Player zora = new Player("Zora");
        Player pris = new Player("Pris");
        replicants.addPlayer(zora);
        replicants.addPlayer(pris);

        Player deckard = new Player("Deckard");
        bladerunners.addPlayer(deckard);

        Game game = new Game(replicants, bladerunners);

        assertEquals(0, replicants.getScore());
        assertEquals(0, bladerunners.getScore());

        GameEvent gameEvent = new GameEvent(GameEvent.SCORE);

        zora.addGameEvent(game, gameEvent);

        assertEquals(1, replicants.getScore());
        assertEquals(0, bladerunners.getScore());

        zora.addGameEvent(game, gameEvent);

        assertEquals(2, replicants.getScore());
        assertEquals(0, bladerunners.getScore());

        deckard.addGameEvent(game, gameEvent);

        assertEquals(2, replicants.getScore());
        assertEquals(1, bladerunners.getScore());
    }
}
