package com.clowndata.model.valueobject;

import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import com.clowndata.model.Team;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by 2014.
 */
public class GamesStatisticsTest {


    private Player lukeSkywalker;
    private Player hanSolo;
    private Player landoCalrissian;
    private Player darthVader;
    private Player darthMaul;
    private Player savageOpress;
    private Player r2d2;
    private Player c3po;

    private Game thePhantomMenaceGame;
    private Game attackOfTheClonesGame;
    private Game revengeOfTheSithGame;
    private Game unfinishedGame;

    private Long thePhantomMenaceGameStart = 100000L;
    private Long attackOfTheClonesGameStart = 300000L;
    private Long revengeOfTheSithGameStart = 500000L;
    private Long unfinishedGameStart = 700000L;

    private List<Game> starWarsGames = new ArrayList<>();

    private GamesStatistics gamesStatistics;

    @Before
    public void setUp() {

        lukeSkywalker = new Player("Luke Skywalker");
        hanSolo = new Player("Han Solo");
        landoCalrissian = new Player("Lando Calrissian");
        Team rebellion1 = new Team();
        rebellion1.addPlayer(lukeSkywalker);
        rebellion1.addPlayer(hanSolo);
        Team rebellion2 = new Team();
        rebellion2.addPlayer(lukeSkywalker);
        rebellion2.addPlayer(hanSolo);
        Team rebellion3 = new Team();
        rebellion3.addPlayer(lukeSkywalker);
        rebellion3.addPlayer(hanSolo);

        darthVader = new Player("Darth Vader");
        darthMaul = new Player("Darth Maul");
        savageOpress = new Player("Savage Opress");
        Team empire1 = new Team();
        empire1.addPlayer(darthVader);
        empire1.addPlayer(darthMaul);
        Team empire2 = new Team();
        empire2.addPlayer(darthVader);
        empire2.addPlayer(darthMaul);
        Team empire3 = new Team();
        empire3.addPlayer(darthVader);
        empire3.addPlayer(savageOpress);

        r2d2 = new Player("R2D2");
        c3po = new Player("C-3PO");
        Team droids1 = new Team();
        droids1.addPlayer(r2d2);
        droids1.addPlayer(c3po);
        Team droids2 = new Team();
        droids2.addPlayer(r2d2);
        droids2.addPlayer(c3po);

        thePhantomMenaceGame = createGame(rebellion1, empire1, thePhantomMenaceGameStart, 200000L);
        attackOfTheClonesGame = createGame(rebellion2, empire2, attackOfTheClonesGameStart, 400000L);
        revengeOfTheSithGame = createGame(empire3, droids1, revengeOfTheSithGameStart, 600000L);
        unfinishedGame = createGame(rebellion3, droids2, unfinishedGameStart, null);

        starWarsGames.add(thePhantomMenaceGame);
        starWarsGames.add(attackOfTheClonesGame);
        starWarsGames.add(revengeOfTheSithGame);
        starWarsGames.add(unfinishedGame);
    }

    private Game createGame(Team team1, Team team2, Long start, Long end) {

        Date gameStart = new Date();
        gameStart.setTime(start);
        Game game = new Game(team1, team2, gameStart);

        Date gameEnd = new Date();
        if (end != null) {
            gameEnd.setTime(end);
            game.setGameEnd(gameEnd);
        }

        return game;
    }

    private GameEvent createGameEvent(Game game, int eventType, long gameStart) {

        Date date = new Date();
        date.setTime(gameStart);

        return new GameEvent(game, eventType, date);
    }

    @Test
    public void testTotalGameTime() {

        gamesStatistics = new GamesStatistics(starWarsGames);

        assertEquals(300, gamesStatistics.getTotalGameTimeInSeconds());
    }

    @Test
    public void testTotalNumberOfGoals() {

        GameEvent goal1 = createGameEvent(thePhantomMenaceGame, GameEvent.GOAL, thePhantomMenaceGameStart);
        lukeSkywalker.addGameEvent(goal1);

        GameEvent goal2 = createGameEvent(attackOfTheClonesGame, GameEvent.GOAL, attackOfTheClonesGameStart);
        lukeSkywalker.addGameEvent(goal2);

        GameEvent goal3 = createGameEvent(revengeOfTheSithGame, GameEvent.GOAL, revengeOfTheSithGameStart);
        c3po.addGameEvent(goal3);

        GameEvent goal4 = createGameEvent(unfinishedGame, GameEvent.GOAL, unfinishedGameStart);
        c3po.addGameEvent(goal4);

        gamesStatistics = new GamesStatistics(starWarsGames);

        assertEquals(3, gamesStatistics.getTotalNumberOfGoals());
    }

    @Test
    public void testTotalNumberOfOwnGoals() {
        GameEvent goal1 = createGameEvent(attackOfTheClonesGame, GameEvent.OWNGOAL, attackOfTheClonesGameStart);
        lukeSkywalker.addGameEvent(goal1);

        GameEvent goal2 = createGameEvent(revengeOfTheSithGame, GameEvent.OWNGOAL, revengeOfTheSithGameStart);
        c3po.addGameEvent(goal2);

        gamesStatistics = new GamesStatistics(starWarsGames);

        assertEquals(2, gamesStatistics.getTotalNumberOfOwnGoals());
    }

    @Test
    public void testFrequentPlayers() {


        //todo: use
        gamesStatistics = new GamesStatistics(starWarsGames);

        //gamesStatistics.getMostFrequentPlayer();
    }
}