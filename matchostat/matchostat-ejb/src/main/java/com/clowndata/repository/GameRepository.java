package com.clowndata.repository;

import com.clowndata.exception.ObjectNotFoundException;
import com.clowndata.model.Game;
import com.clowndata.model.Player;
import com.clowndata.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created 2014.
 */
public class GameRepository {

    @PersistenceContext
    private EntityManager em;

    @Transient
    final Logger log = LoggerFactory.getLogger(GameRepository.class);

    public GameRepository(EntityManager em) {
        this.em = em;
    }

    public GameRepository() {
    }

    public Long createGame(Game game) {

        em.persist(game);

        log.info("Created Game: " + game.getId());

        return game.getId();
    }

    private void eagerFetch(Game game) {

        if (game != null) {
            if (game.getTeam1() != null) {
                game.getTeam1().getPlayers().size();
            }
            if (game.getTeam2() != null) {
                game.getTeam2().getPlayers().size();
            }
        }
    }

    public Game getGame(Long id) {
        Game game = em.find(Game.class, id);

        if (game == null) {
            throw new ObjectNotFoundException(Game.class, id);
        }

        eagerFetch(game);

        return game;
    }

    public List<Game> getAllGames() {

        List<Game> games = em.createNamedQuery("Game.findAll").getResultList();

        for (Game game : games) {
            eagerFetch(game);
        }

        return games;
    }

    public void deleteGame(Game game) {

        em.remove(game);

        log.info("Deleted Game: " + game.getId());
    }

    public List<Team> getAllTeams() {
        return em.createNamedQuery("Team.findAll").getResultList();
    }

}
