package com.clowndata.service;

import com.clowndata.exception.ObjectNotFoundException;
import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import com.clowndata.model.Team;
import com.clowndata.model.valueobject.GameSummary;

import java.util.List;

/**
 * Created by 2014.
 */
public interface GameService {

    public List<Game> getAllGames();

    public Game getGame(Long id);

    public Long createGame(Game game);

    public void updateGame(Long id, Game game);

    public void deleteGame(Long id);

    public Long addPlayerGameEvent(Long playerId, Long gameId, GameEvent gameEvent);

    public boolean deletePlayer(Long playerId);

    public GameSummary getGameSummary(Long gameId);
}
