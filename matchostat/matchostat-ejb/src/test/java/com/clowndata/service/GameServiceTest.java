package com.clowndata.service;

import com.clowndata.exception.ObjectNotFoundException;
import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import com.clowndata.model.Team;
import com.clowndata.model.valueobject.GameSummary;
import com.clowndata.repository.GameRepository;
import com.clowndata.repository.PlayerRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 2014.
 */
@RunWith(Arquillian.class)
public class GameServiceTest {

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class).addPackage(GameServiceImpl.class.getPackage()).addPackage(GameRepository.class.getPackage())
                .addPackage(Game.class.getPackage()).addPackage(GameSummary.class.getPackage()).addPackage(ObjectNotFoundException.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml").addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
        System.out.println(jar.toString(true));
        return jar;
    }

    private GameService gameService;
    private Game game1;
    private Game game2;
    private Player zora;
    private Player pris;
    private Player rachael;

    @Before
    public void init() throws SystemException, NotSupportedException {
        utx.begin();
        em.joinTransaction();

        PlayerRepository playerRepository = new PlayerRepository(em);
        gameService = new GameServiceImpl(new GameRepository(em), playerRepository);

        Team replicants = new Team();
        Team bladerunners = new Team();

        zora = new Player("Zora");
        pris = new Player("Pris");
        rachael = new Player("Rachael");
        replicants.addPlayer(zora);
        replicants.addPlayer(pris);
        replicants.addPlayer(rachael);

        Player deckard = new Player("Deckard");
        bladerunners.addPlayer(deckard);

        playerRepository.createPlayer(zora);
        playerRepository.createPlayer(pris);
        playerRepository.createPlayer(rachael);
        playerRepository.createPlayer(deckard);

        game1 = new Game(replicants, bladerunners);
        game2 = new Game(bladerunners, replicants);
    }

    @Test
    public void createGame() {

        Long gameId1 = gameService.createGame(game1);
        assertTrue(gameId1 != null);
        Long gameId2 = gameService.createGame(game2);
        assertTrue(gameId1 != gameId2);
    }

    @Test
    public void getGame() {
        Long gameId = gameService.createGame(game1);
        Game game = gameService.getGame(gameId);
        assertEquals(gameId, game.getId());
    }

    @Test
    public void getAllGames() {


        int gamesBefore = gameService.getAllGames().size();

        gameService.createGame(game1);
        gameService.createGame(game2);

        List<Game> games = gameService.getAllGames();
        assertEquals(gamesBefore + 2, games.size());
    }

    @Test
    public void testUpdateGame() {

        Long gameId1 = gameService.createGame(game1);

        Date gameEnd = new Date();
        gameEnd.setTime(game2.getGameStart().getTime() + 1234L);
        game2.setGameEnd(gameEnd);

        assertNull(game1.getGameEnd());

        gameService.updateGame(gameId1, this.game2);

        Game game = gameService.getGame(gameId1);

        assertEquals(game2.getGameEnd().getTime(), game.getGameEnd().getTime());
    }

    @Test
    public void testDeleteGame() {

        Long gameId = gameService.createGame(game1);

        int gamesBefore = gameService.getAllGames().size();

        gameService.deleteGame(gameId);

        List<Game> games = gameService.getAllGames();

        assertEquals(gamesBefore - 1, games.size());
    }

    @Test
    public void testAddPlayerGameEvent() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {

        assertEquals(0, zora.getGameEvents().size());

        Long gameId = gameService.createGame(game1);
        GameEvent goal = new GameEvent(game1, GameEvent.GOAL);
        Long eventId = gameService.addPlayerGameEvent(zora.getId(), gameId, goal);

        assertEquals(1, zora.getGameEvents().size());

        List<GameEvent> events = zora.getGameEvents();

        GameEvent persistedGoal = events.get(0);

        assertEquals(eventId, persistedGoal.getId());

        GameEvent assist = new GameEvent(game1, GameEvent.ASSIST, persistedGoal);
        gameService.addPlayerGameEvent(pris.getId(), gameId, assist);

        assertEquals(1, pris.getGameEvents().size());
    }

    @Test
    public void testGetGameSummary() {

        Long gameId = gameService.createGame(game1);

        GameSummary gameSummary = gameService.getGameSummary(gameId);

        Assert.assertNotNull(gameSummary);
    }

    @After
    public void commit() throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException {
        utx.commit();
    }
}
