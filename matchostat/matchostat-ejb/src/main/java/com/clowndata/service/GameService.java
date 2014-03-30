package com.clowndata.service;

import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import com.clowndata.model.Team;
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
        Game game = gameRepository.getGame(gameId);
        gameEvent.setGame(game);

        player.addGameEvent(game, gameEvent);

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
}
