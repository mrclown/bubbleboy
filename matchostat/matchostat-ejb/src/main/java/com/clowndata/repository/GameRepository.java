package com.clowndata.repository;

import com.clowndata.model.Game;
import com.clowndata.model.Player;
import com.clowndata.model.Team;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created 2014.
 */

@Stateless
public class GameRepository {


    @PersistenceContext
    private EntityManager em;


    public Long createGame(Game game) {

        Team team = game.getTeam1();
        System.out.println("Persisting team 1");
        em.persist(team);

        team = game.getTeam2();
        System.out.println("Persisting team 2");
        em.persist(team);

        System.out.println("Persisting game");
        em.persist(game);
        System.out.println("Persisting done!");

        return game.getId();
    }

    public Game getGame(Long id) {
        Game game = em.find(Game.class, id);

        // To force fetching of players within the session
        game.getTeam1().getPlayers().size();
        game.getTeam2().getPlayers().size();

        return game;
    }

    public List getAllGames() {

        List games = em.createNamedQuery("Game.findAll").getResultList();

        return games;
    }

    public Game updateGame(Long id, Game game) {

        Game persistedGame = em.find(Game.class, id);

        if (persistedGame != null) {
            //   persistedGame.setName(player.getName());
        }

        return persistedGame;
    }

    public boolean deleteGame(Long id) {

        Boolean deleted = false;

        Game game = em.find(Game.class, id);

        if (game != null) {
            em.remove(game);
            deleted = true;
        }

        return deleted;
    }

    public List getTeams(Long id) {

        List<Team> teams = null;

        Game game = em.find(Game.class, id);

        if (game != null) {

            teams = new ArrayList<Team>(2);

            teams.add(game.getTeam1());
            teams.add(game.getTeam2());
        }
        return teams;
    }

}
