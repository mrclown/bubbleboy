package com.clowndata.rest;

/**
 * Created 2014.
 */

import com.clowndata.model.Game;
import com.clowndata.model.Team;
import com.clowndata.repository.GameRepository;

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
    private GameRepository gameRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGames() {

        List<Game> games = gameRepository.getAllGames();

        return Response.ok().entity(games).build();
    }

    @GET
    @Path("/{id:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@PathParam("id") String id) {

        Game game = gameRepository.getGame(Long.parseLong(id));

        if (game == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(game).build();
    }

    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGame(Game game) {

        Long id = gameRepository.createGame(game);

        URI uri = uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("{id}")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putGame(@PathParam("id") String id, Game game) {

        gameRepository.updateGame(Long.parseLong(id), game);

        //TODO: implement conflict (409)?
        //TODO: implement not found (404)
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    @Produces("text/plain")
    @Consumes("text/plain")
    public Response deleteGame(@PathParam("id") String id) {

        if (!gameRepository.deleteGame(Long.parseLong(id))) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();
    }


    @GET
    @Path("{id}/teams")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeams(@PathParam("id") String id) {
        List<Team> teams = gameRepository.getTeams(new Long(id));

        if (teams == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(teams).build();
    }
}