package com.clowndata.service;

import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import com.clowndata.model.Team;
import com.clowndata.model.valueobject.GameSummary;
import com.clowndata.repository.GameRepository;
import com.clowndata.repository.PlayerRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
            throw new IllegalStateException("Invalid playerId: " + playerId);
        }


        Game game = gameRepository.getGame(gameId);
        if (game == null) {
            throw new IllegalStateException("Invalid gameId: " + gameId);
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
