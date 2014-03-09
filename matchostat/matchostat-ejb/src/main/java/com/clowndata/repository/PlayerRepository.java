package com.clowndata.repository;

import com.clowndata.model.Player;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
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

        List<Player> players = em.createNamedQuery("Player.findAll",Player.class).getResultList();

        return players;
    }

    public Player updatePlayer(Long id, Player player) {

        Player persistedPlayer = em.find(Player.class, id);

        if (persistedPlayer != null) {
            persistedPlayer.setName(player.getName());
        }

        return persistedPlayer;
    }

    public boolean deletePlayer(Long id) {

        Boolean deleted = false;

        Player player = em.find(Player.class, id);

        if (player != null) {
            em.remove(player);
            deleted = true;
        }

        return deleted;
    }
}
