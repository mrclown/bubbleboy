package com.clowndata.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by 2014.
 */
public class PlayerTest {


    private Game game;
    private Team replicants;
    private Team bladerunners;
    private Player zora;
    private Player pris;
    private Player deckard;

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
    public void testTeamScores() {

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

        deckard.addGameEvent(new GameEvent(game, GameEvent.GOAL));
        GameEvent goal = new GameEvent(game, GameEvent.GOAL);
        pris.addGameEvent(goal);
        pris.addGameEvent(new GameEvent(game, GameEvent.ASSIST, goal));

        assertEquals(1, zora.getPoints(game));
        assertEquals(2, pris.getPoints(game));
        assertEquals(1, deckard.getPoints(game));

        assertEquals(2, replicants.getScore());
        assertEquals(1, bladerunners.getScore());

        deckard.addGameEvent(new GameEvent(game, GameEvent.OWNGOAL));

        assertEquals(1, zora.getPoints(game));
        assertEquals(2, pris.getPoints(game));
        assertEquals(1, deckard.getPoints(game));

        assertEquals(3, replicants.getScore());
        assertEquals(1, bladerunners.getScore());
    }

    @Test
    public void testMostAssistFromOnePlayerToAnother() {

        GameEvent goal = new GameEvent(game, GameEvent.GOAL);
        zora.addGameEvent(goal);
        GameEvent assist = new GameEvent(game, GameEvent.ASSIST, goal);
        pris.addGameEvent(assist);
//todo
    }

    @Test
    public void testPlayerDeletable() {

        assertTrue(zora.isDeletable());
        assertTrue(pris.isDeletable());

        zora.addGameEvent(new GameEvent(game, GameEvent.GOAL));

        assertFalse(zora.isDeletable());
        assertTrue(pris.isDeletable());
    }

    @Test(expected = IllegalStateException.class)
    public void testNotPossibleToAssistPlayerInOtherTeam() {

        GameEvent goal = new GameEvent(game, GameEvent.GOAL);
        pris.addGameEvent(goal);
        deckard.addGameEvent(new GameEvent(game, GameEvent.ASSIST, goal));
    }

    @Test(expected = IllegalStateException.class)
    public void testNotPossibleToAssistPlayerNotInGame() {

        GameEvent goal = new GameEvent(game, GameEvent.GOAL);
        pris.addGameEvent(goal);

        Player sebastian = new Player("Sebastian");
        sebastian.addGameEvent(new GameEvent(game, GameEvent.ASSIST, goal));
    }
}
