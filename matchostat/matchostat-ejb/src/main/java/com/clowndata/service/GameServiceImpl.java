package com.clowndata.service;

import com.clowndata.exception.ObjectNotFoundException;
import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import com.clowndata.model.Team;
import com.clowndata.model.valueobject.GameSummary;
import com.clowndata.repository.GameRepository;
import com.clowndata.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Transient;
import javax.xml.ws.http.HTTPException;
import java.util.Date;
import java.util.List;

/**
 * Created by 2014.
 */

@Stateless
public class GameServiceImpl implements GameService {

    @Inject
    private PlayerRepository playerRepository;

    @Inject
    private GameRepository gameRepository;

    @Transient
    final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    @Override
    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }

    @Override
    public Game getGame(Long id) {
        return gameRepository.getGame(id);
    }

    @Override
    public Long createGame(Game game) {
        return gameRepository.createGame(game);
    }

    @Override
    public void updateGame(Long id, Game game) {
        Game persistedGame = gameRepository.getGame(id);

        persistedGame.updateGame(game);
    }

    @Override
    public void deleteGame(Long id) {

        Game game = gameRepository.getGame(id);

        List<GameEvent> gameEventsToDelete = game.deleteEventsForPlayers();

        // If we don't do this the events get orphan but I don't understand why
        playerRepository.deleteGameEvents(gameEventsToDelete);

        gameRepository.deleteGame(game);
    }

    public Long addPlayerGameEvent(Long playerId, Long gameId, GameEvent event) {

        Player player = playerRepository.getPlayer(playerId);

        Game game = gameRepository.getGame(gameId);

        GameEvent gameEvent = GameEvent.createGameEvent(game, event);

        if (gameEvent.getGameEventLink() != null) {
            GameEvent gameEventLink = playerRepository.getPlayerGameEvent(gameEvent.getGameEventLink().getId());
            gameEvent.setGameEventLink(gameEventLink);
        }

        player.addGameEvent(gameEvent);

        return playerRepository.createGameEvent(gameEvent);
    }

    public GameSummary getGameSummary(Long gameId) {

        Game game = gameRepository.getGame(gameId);

        if (game == null) {
            //TODO: return Response.Status.NOT_FOUND, not IllegalException
            throw new IllegalStateException("Invalid gameId: " + gameId);
        }

        GameSummary gameSummary = new GameSummary(game);

        return gameSummary;
    }

    @Override
    public void endGame(Long gameId) {

        Game game = gameRepository.getGame(gameId);

        game.setGameEnd(new Date());
    }
}
