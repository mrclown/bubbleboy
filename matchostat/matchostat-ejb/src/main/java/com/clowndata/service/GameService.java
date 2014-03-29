package com.clowndata.service;

import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import com.clowndata.repository.GameRepository;
import com.clowndata.repository.PlayerRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
        gameEvent.setGameId(gameId);

        player.addGameEvent(game, gameEvent);

        return playerRepository.createGameEvent(gameEvent);
    }
}
