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
        em.persist(team);

        team = game.getTeam2();
        em.persist(team);

        em.persist(game);

        return game.getId();
    }

    public Game getGame(Long id) {
        Game game = em.find(Game.class, id);

        if (game != null) {
            // To force fetching of players within the session
            game.getTeam1().getPlayers().size();
            game.getTeam2().getPlayers().size();
        }
        return game;
    }

    public List<Game> getAllGames() {

        List<Game> games = em.createNamedQuery("Game.findAll").getResultList();

        for(Game game : games){
            // To force fetching of players within the session
            game.getTeam1().getPlayers().size();
            game.getTeam2().getPlayers().size();
        }

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
            //TODO delete teams
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