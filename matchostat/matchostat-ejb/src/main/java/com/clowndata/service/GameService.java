package com.clowndata.service;

import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;
import com.clowndata.model.valueobject.GameSummary;

import java.util.List;

/**
 * Created by 2014.
 */
public interface GameService {

    public Long createGame(Game game);

    public Game getGame(Long id);

    public List<Game> getAllGames();

    public void updateGame(Long id, Game game);

    public void deleteGame(Long id);

    public Long addPlayerGameEvent(Long playerId, Long gameId, GameEvent gameEvent);

    public GameSummary getGameSummary(Long gameId);

    void endGame(Long gameId);
}
