package com.clowndata.repository;

import com.clowndata.model.Game;
import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import sun.net.www.content.text.plain;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 2014.
 */

@Stateless
public class PlayerRepository {


    @PersistenceContext
    private EntityManager em;


    public Long createPlayer(Player player) {

        em.persist(player);

        return player.getId();
    }

    public Player getPlayer(Long id) {

        Player player = em.find(Player.class, id);

        return player;
    }

    public List getAllPlayers() {

        List<Player> players = em.createNamedQuery("Player.findAll", Player.class).getResultList();

        return players;
    }

    public Player updatePlayer(Long id, Player player) {

        Player persistedPlayer = em.find(Player.class, id);

        if (persistedPlayer != null) {
            persistedPlayer.setName(player.getName());
            persistedPlayer.setActive(player.getActive());
        }

        return persistedPlayer;
    }

    public boolean deletePlayer(Long id) {

        Boolean deleted = false;

        Player player = em.find(Player.class, id);


        if (player != null) {
            deletePlayerEvents(player);
            em.remove(player);
            deleted = true;
        }

        return deleted;
    }

    private void deletePlayerEvents(Player player) {

        for (GameEvent gameEvent : player.getGameEvents()) {
            em.remove(gameEvent);
        }
    }

    public List<GameEvent> getPlayerGameEvents(Long id, Long gameId) {

        List<GameEvent> gameEvents = null;
        Player player = em.find(Player.class, id);

        if (player != null) {
            gameEvents = new ArrayList();
            for (GameEvent gameEvent : player.getGameEvents()) {
                if (gameEvent.getGame().getId() == gameId) {
                    gameEvents.add(gameEvent);
                }
            }
        }

        return gameEvents;
    }


    public Long createGameEvent(GameEvent gameEvent) {

        em.persist(gameEvent);

        return gameEvent.getId();
    }

    public void deleteEventsForPlayerInGame(Long id, Long gameId) {

        Player player = em.find(Player.class, id);

        List<GameEvent> gameEvents = player.getGameEvents();
        List<GameEvent> gameEventsToDelete = new ArrayList<>();

        for (GameEvent gameEvent : gameEvents) {
            if (gameEvent.getGame().getId() == gameId) {
                gameEventsToDelete.add(gameEvent);
            }
        }

        deleteGameEvents(player, gameEventsToDelete);
    }

    private void deleteGameEvents(Player player, List<GameEvent> gameEventsToDelete) {

        for (GameEvent gameEvent : gameEventsToDelete) {
            player.getGameEvents().remove(gameEvent);
            em.remove(gameEvent);
        }
    }
}
