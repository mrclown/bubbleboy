package com.clowndata.service;

import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import com.clowndata.repository.PlayerRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by 2014.
 */
@Stateless
public class PlayerServiceImpl implements PlayerService {

    @Inject
    PlayerRepository playerRepository;

    @Override
    public List getAllPlayers() {

        return playerRepository.getAllPlayers();
    }

    @Override
    public Player getPlayer(Long id) {
        return playerRepository.getPlayer(id);
    }

    @Override
    public Long createPlayer(Player player) {
        return playerRepository.createPlayer(player);
    }

    @Override
    public void updatePlayer(Long id, Player player) {
        playerRepository.updatePlayer(id, player);
    }

    @Override
    public List<GameEvent> getPlayerGameEvents(Long playerId, Long gameId) {
        return playerRepository.getPlayerGameEvents(playerId, gameId);
    }

}
