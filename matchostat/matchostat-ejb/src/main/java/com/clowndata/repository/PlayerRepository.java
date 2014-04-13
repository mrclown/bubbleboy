package com.clowndata.repository;

import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 2014.
 */

@Stateless
public class PlayerRepository {

    final Logger log = LoggerFactory.getLogger(PlayerRepository.class);

    @PersistenceContext
    private EntityManager em;


    public Long createPlayer(Player player) {

        em.persist(player);

        log.info("Created player: " + player.getName() + " id: " + player.getId());

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
            log.info("Updating player: " + persistedPlayer.getName() + " id: " + id);
            persistedPlayer.setName(player.getName());
            persistedPlayer.setActive(player.getActive());
            log.info("Updated player: " + player.getName() + " id: " + id + " active: " + player.getActive());
        } else {
            log.error("Player could not be found id: " + id);
        }

        return persistedPlayer;
    }

    public boolean deletePlayer(Long id) {

        Boolean deleted = false;

        Player player = em.find(Player.class, id);

        if (player != null) {
            log.info("Deleting player: " + player.getName() + " id: " + id);
            deletePlayerEvents(player);
            em.remove(player);
            deleted = true;
        } else {
            log.error("Player could not be found id: " + id);
        }

        return deleted;
    }

    private void deletePlayerEvents(Player player) {

        log.info("Deleting events for player id: " + player.getId());

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
        } else {
            log.error("Player could not be found id: " + id);
        }

        return gameEvents;
    }


    public Long createGameEvent(GameEvent gameEvent) {

        em.persist(gameEvent);

        return gameEvent.getId();
    }

    public void deleteEventsForPlayerInGame(Long id, Long gameId) {

        Player player = em.find(Player.class, id);

        if (player != null) {

            List<GameEvent> gameEvents = player.getGameEvents();
            List<GameEvent> gameEventsToDelete = new ArrayList<>();

            for (GameEvent gameEvent : gameEvents) {
                if (gameEvent.getGame().getId() == gameId) {
                    gameEventsToDelete.add(gameEvent);
                }
            }

            deleteGameEvents(player, gameEventsToDelete);
        } else {
            log.error("Player could not be found id: " + id);
        }
    }

    private void deleteGameEvents(Player player, List<GameEvent> gameEventsToDelete) {

        log.info("Deleting events for player id: " + player.getId());

        for (GameEvent gameEvent : gameEventsToDelete) {
            player.getGameEvents().remove(gameEvent);
            em.remove(gameEvent);
        }
    }
}
