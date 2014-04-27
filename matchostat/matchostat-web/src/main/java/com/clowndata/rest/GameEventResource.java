package com.clowndata.rest;

import com.clowndata.model.GameEvent;
import com.clowndata.repository.PlayerRepository;
import com.clowndata.service.GameService;
import com.clowndata.service.GameServiceImpl;
import com.clowndata.service.PlayerService;

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
    private PlayerService playerService;

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerGameEvents(@PathParam("playerId") String playerId, @PathParam("gameId") String gameId) {

        List<GameEvent> gameEvents = playerService.getPlayerGameEvents(Long.parseLong(playerId), Long.parseLong(gameId));

        return Response.ok().entity(gameEvents).build();
    }

    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlayerGameEvent(@PathParam("playerId") String playerId, @PathParam("gameId") String gameId, GameEvent gameEvent) {

        Long eventId = gameService.addPlayerGameEvent(Long.parseLong(playerId), Long.parseLong(gameId), gameEvent);

        URI uri = uriInfo.getAbsolutePathBuilder().path(eventId.toString()).build();
        return Response.created(uri).build();
    }
}
