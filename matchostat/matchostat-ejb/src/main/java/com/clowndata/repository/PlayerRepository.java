package com.clowndata.repository;

import com.clowndata.exception.ObjectNotFoundException;
import com.clowndata.model.GameEvent;
import com.clowndata.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created 2014.
 */

public class PlayerRepository {

    final Logger log = LoggerFactory.getLogger(PlayerRepository.class);

    @PersistenceContext
    private EntityManager em;

    public PlayerRepository(EntityManager em) {
        this.em = em;
    }

    public PlayerRepository() {
    }

    public Long createPlayer(Player player) {

        validatePlayer(player);

        em.persist(player);

        log.info("Created player: " + player.getName() + " id: " + player.getId());

        return player.getId();
    }

    private void validatePlayer(Player player) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Player>> constraintViolations = validator.validate(player);

        if (constraintViolations.size() > 0) {
            Set<String> violationMessages = new HashSet<>();

            for (ConstraintViolation<Player> constraintViolation : constraintViolations) {
                violationMessages.add(constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage());
            }

            throw new IllegalStateException("Player is not valid:\n" + violationMessages.toString());
        }
    }

    public Player getPlayer(Long id) {

        Player player = em.find(Player.class, id);

        if (player == null) {
            throw new ObjectNotFoundException(Player.class, id);
        }

        return player;
    }

    public List getAllPlayers() {

        List<Player> players = em.createNamedQuery("Player.findAll", Player.class).getResultList();

        return players;
    }

    public void deletePlayer(Long id) {

        Player player = em.find(Player.class, id);

        if (player == null) {
            throw new ObjectNotFoundException(Player.class, id);
        }

        if (!player.isDeletable()) {
            String msg = "Player can not be deleted: " + player.getName() + " id: " + id;
            log.error(msg);
            throw new IllegalStateException(msg);
        }

        log.info("Deleting player: " + player.getName() + " id: " + id);
        em.remove(player);
    }

    public GameEvent getPlayerGameEvent(Long gameEventId) {

        GameEvent gameEvent = em.find(GameEvent.class, gameEventId);

        if (gameEvent == null) {
            log.error("GameEvent could not be found id: " + gameEventId);
            throw new ObjectNotFoundException(GameEvent.class, gameEventId);
        }

        return gameEvent;
    }

    public Long createGameEvent(GameEvent gameEvent) {

        em.persist(gameEvent);

        return gameEvent.getId();
    }
}
