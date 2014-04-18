package com.clowndata.model.valueobject;

import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import com.clowndata.model.Team;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by 2014.
 */
public class GameSummaryTest {

    @Test
    public void testGameSummary() {

        Set<Player> playersTeam1 = new HashSet<>();
        Set<Player> playersTeam2 = new HashSet<>();

        Player ralf = new Player("Ralf");
        Player florian = new Player("Florian");
        Player karl = new Player("Karl");
        Player wolfgang = new Player("Wolfgang");

        playersTeam1.add(ralf);
        playersTeam1.add(florian);

        playersTeam2.add(karl);
        playersTeam2.add(wolfgang);

        Team kling = new Team(playersTeam1);
        Team klang = new Team(playersTeam2);

        Date startTime = new Date();
        startTime.setTime(1396810805);

        //Game on
        Game game = new Game(kling, klang, startTime);

        //Team1 events
        ralf.addGameEvent(new GameEvent(game, GameEvent.GOAL));

        //Team2 events
        karl.addGameEvent(new GameEvent(game, GameEvent.GOAL));
        GameEvent goal = new GameEvent(game, GameEvent.GOAL);
        karl.addGameEvent(goal);
        wolfgang.addGameEvent(new GameEvent(game, GameEvent.ASSIST, goal));
        karl.addGameEvent(new GameEvent(game, GameEvent.GOAL));
        goal = new GameEvent(game, GameEvent.GOAL);
        wolfgang.addGameEvent(goal);
        karl.addGameEvent(new GameEvent(game, GameEvent.ASSIST, goal));

        GameSummary gameSummary = new GameSummary(game);

        assertFalse(gameSummary.isGameEnded());

        //Game off
        Date endTime = new Date();
        endTime.setTime(1396910805);

        game.setGameEnd(endTime);

        gameSummary = new GameSummary(game);

        assertTrue(gameSummary.isGameEnded());

        TeamSummary team1Summary = gameSummary.getTeam1Summary();
        TeamSummary team2Summary = gameSummary.getTeam2Summary();

        assertEquals(1, team1Summary.getTeamGoals());
        assertEquals(4, team2Summary.getTeamGoals());

        assertEquals(100, gameSummary.getDurationInSeconds());

        List<PlayerSummary> playerTeam1Summary = team1Summary.getPlayerSummaries();
        List<PlayerSummary> playerTeam2Summary = team2Summary.getPlayerSummaries();

        for (PlayerSummary player : playerTeam1Summary) {
            if (player.getName().equals("Ralf")) {
                assertEquals(1, player.getGoals());
                assertEquals(1, player.getPoints());
            } else if (player.getName().equals("Florian")) {
                assertEquals(0, player.getGoals());
                assertEquals(0, player.getPoints());
            } else {
                assertFalse(true);
            }
        }

        for (PlayerSummary player : playerTeam2Summary) {
            if (player.getName().equals("Karl")) {
                assertEquals(3, player.getGoals());
                assertEquals(4, player.getPoints());
            } else if (player.getName().equals("Wolfgang")) {
                assertEquals(1, player.getGoals());
                assertEquals(2, player.getPoints());
            } else {
                assertFalse(true);
            }
        }
    }
}
