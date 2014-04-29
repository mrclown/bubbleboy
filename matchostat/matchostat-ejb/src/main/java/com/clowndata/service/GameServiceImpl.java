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
        gameRepository.updateGame(id, game);
    }

    @Override
    public void deleteGame(Long id) {
        gameRepository.deleteGame(id);
    }

    public Long addPlayerGameEvent(Long playerId, Long gameId, GameEvent gameEvent) {

        Player player = playerRepository.getPlayer(playerId);

        Game game = gameRepository.getGame(gameId);

        if (gameEvent.getGameEventLink() != null) {
            GameEvent gameEventLink = playerRepository.getPlayerGameEvent(gameEvent.getGameEventLink().getId());
            gameEvent.setGameEventLink(gameEventLink);
        }

        gameEvent.setGame(game);

        player.addGameEvent(gameEvent);

        return playerRepository.createGameEvent(gameEvent);
    }

    public void deletePlayer(Long playerId) {

        Player player = playerRepository.getPlayer(playerId);

        if (player.isDeletable()) {
            List<Team> teams = gameRepository.getAllTeams();

            for (Team team : teams) {
                if (team.getPlayers().contains(player)) {
                    team.getPlayers().remove(player);
                }
            }
            playerRepository.deletePlayer(playerId);
        } else {
            player.setActive(false);
        }
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
}
