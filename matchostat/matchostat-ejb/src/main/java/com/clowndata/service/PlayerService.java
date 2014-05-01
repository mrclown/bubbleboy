package com.clowndata.service;

import com.clowndata.model.Player;

import java.util.List;

/**
 * Created by 2014.
 */

public interface PlayerService {

    List getAllPlayers();

    Player getPlayer(Long id);

    Long createPlayer(Player player);

    void updatePlayer(Long id, Player player);

    public void deletePlayer(Long playerId);
}
