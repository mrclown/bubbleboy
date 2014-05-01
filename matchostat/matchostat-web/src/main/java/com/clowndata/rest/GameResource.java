package com.clowndata.rest;

/**
 * Created 2014.
 */

import com.clowndata.model.Game;
import com.clowndata.model.Team;
import com.clowndata.repository.GameRepository;
import com.clowndata.service.GameService;
import com.clowndata.service.GameServiceImpl;

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
 * REST Web Service
 */
@Path("/games")
@RequestScoped
public class GameResource {

    @Context
    UriInfo uriInfo;

    @Inject
    GameService gameService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGames() {

        List<Game> games = gameService.getAllGames();

        return Response.ok().entity(games).build();
    }

    @GET
    @Path("/{id:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@PathParam("id") String id) {

        Game game = gameService.getGame(Long.parseLong(id));

        return Response.ok().entity(game).build();
    }

    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGame(Game game) {

        Long id = gameService.createGame(game);

        URI uri = uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("{id}")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putGame(@PathParam("id") String id, Game game) {

        gameService.updateGame(Long.parseLong(id), game);

        return Response.noContent().build();
    }

    @PUT
    @Path("{id}/end")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response endGame(@PathParam("id") String id) {

       gameService.endGame(Long.parseLong(id));

        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    @Produces("text/plain")
    @Consumes("text/plain")
    public Response deleteGame(@PathParam("id") String id) {

        gameService.deleteGame(Long.parseLong(id));

        return Response.noContent().build();
    }
}