package com.clowndata.rest;

import com.clowndata.model.GameEvent;
import com.clowndata.repository.PlayerRepository;
import com.clowndata.service.GameService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by 2014.
 */

@Path("/players/{playerId:[0-9]*}/games/{gameId:[0-9]*}/events")
@RequestScoped
public class GameEventResource {

    @Inject
    private GameService gameService;

    @Inject
    private PlayerRepository playerRepository;

    @Context
    UriInfo uriInfo;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerGameEvents(@PathParam("playerId") String playerId, @PathParam("gameId") String gameId) {

        //todo: move code to service
        List<GameEvent> gameEvents = playerRepository.getPlayerGameEvents(Long.parseLong(playerId), Long.parseLong(gameId));

        if (gameEvents == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(gameEvents).build();
    }

    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlayerGameEvent(@PathParam("playerId") String playerId, @PathParam("gameId") String gameId, GameEvent gameEvent) {

        Long eventId = gameService.addPlayerGameEvent(Long.parseLong(playerId), Long.parseLong(gameId), gameEvent);

        //TODO: Error handling: no player, no game, illegal event
        URI uri = uriInfo.getAbsolutePathBuilder().path(eventId.toString()).build();
        return Response.created(uri).build();
    }
}
