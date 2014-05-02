package com.clowndata.service;

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
public class PlayerServiceImpl implements PlayerService {

    @Inject
    PlayerRepository playerRepository;

    @Inject
    private GameRepository gameRepository;

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

        Player persistedPlayer = playerRepository.getPlayer(id);
        persistedPlayer.updatePlayer(player);
    }

    public void deletePlayer(Long playerId) {

        Player player = playerRepository.getPlayer(playerId);

        if (player.isDeletable()) {

            List<Team> teams = gameRepository.getAllTeams();

            for (Team team : teams) {
                team.removePlayer(player);
            }

            playerRepository.deletePlayer(playerId);

        } else {
            player.setActive(false);
        }
    }


}
