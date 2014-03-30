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


    @Inject
    private PlayerRepository playerRepository;

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

    private void eagerFetch(Game game) {

        if (game != null) {
            game.getTeam1().getPlayers().size();
            game.getTeam2().getPlayers().size();
        }
    }

    public Game getGame(Long id) {
        Game game = em.find(Game.class, id);

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

    public List<Team> getAllTeams() {

        List<Team> teams = em.createNamedQuery("Team.findAll").getResultList();

        return teams;
    }

    private void updateTeam(Team persistedTeam, Team team) {
        persistedTeam.setPlayers(team.getPlayers());
        persistedTeam.setScore(team.getScore());
    }

    public Game updateGame(Long id, Game game) {

        Game persistedGame = em.find(Game.class, id);

        if (persistedGame != null) {
            Team persistedTeam = persistedGame.getTeam1();
            if (persistedTeam != null) {
                updateTeam(persistedTeam, game.getTeam1());

            } else {
                em.persist(game.getTeam1());
            }

            persistedTeam = persistedGame.getTeam2();
            if (persistedTeam != null) {
                updateTeam(persistedTeam, game.getTeam2());
            } else {
                em.persist(game.getTeam2());
            }
        }
        return persistedGame;
    }

    public boolean deleteGame(Long id) {

        Boolean deleted = false;

        Game game = em.find(Game.class, id);

        if (game != null) {
            deleteTeams(game);
            em.remove(game);
            deleted = true;
        }

        return deleted;
    }

    private void deleteTeams(Game game) {
        Team team = game.getTeam1();
        if (team != null) {
            deleteEventsForPlayersInTeam(game.getId(), team);
            em.remove(team);
        }
        team = game.getTeam2();
        if (team != null) {
            deleteEventsForPlayersInTeam(game.getId(), team);
            em.remove(team);
        }
    }

    private void deleteEventsForPlayersInTeam(Long id, Team team) {

        for (Player player : team.getPlayers()) {
            playerRepository.deleteEventsForPlayerInGame(player.getId(), id);
        }
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
