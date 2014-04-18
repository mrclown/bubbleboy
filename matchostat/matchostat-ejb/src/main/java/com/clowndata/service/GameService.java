package com.clowndata.service;

import com.clowndata.exception.ObjectNotFoundException;
import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import com.clowndata.model.Team;
import com.clowndata.model.valueobject.GameSummary;
import com.clowndata.repository.GameRepository;
import com.clowndata.repository.PlayerRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.http.HTTPException;
import java.util.List;

/**
 * Created by 2014.
 */

@Stateless
public class GameService {

    @Inject
    private PlayerRepository playerRepository;

    @Inject
    private GameRepository gameRepository;

    public Long addPlayerGameEvent(Long playerId, Long gameId, GameEvent gameEvent) {

        Player player = playerRepository.getPlayer(playerId);
        if (player == null) {
            throw new ObjectNotFoundException(playerId, Player.class);
        }

        Game game = gameRepository.getGame(gameId);
        if (game == null) {
//todo: fix exception
            throw new IllegalStateException("Invalid gameId: " + gameId);
        }

        if(gameEvent.getGameEventLink() != null){
            GameEvent gameEventLink = playerRepository.getPlayerGameEvent(gameEvent.getGameEventLink().getId());
            gameEvent.setGameEventLink(gameEventLink);
        }

        gameEvent.setGame(game);

        player.addGameEvent(gameEvent);

        return playerRepository.createGameEvent(gameEvent);
    }

    public boolean deletePlayer(Long playerId) {

        Player player = playerRepository.getPlayer(playerId);

        List<Team> teams = gameRepository.getAllTeams();

        for (Team team : teams) {
            if (team.getPlayers().contains(player)) {
                team.getPlayers().remove(player);
            }
        }
        return playerRepository.deletePlayer(playerId);
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
