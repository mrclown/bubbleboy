package com.clowndata.rest;

/**
 * Created 2014.
 */

import com.clowndata.repository.PlayerRepository;
import com.clowndata.model.Player;

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
@Path("/players")
@RequestScoped
public class PlayerResource {

    @Inject
    private PlayerRepository playerRepository;

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlayers() {

        List players = playerRepository.getAllPlayers();

        return Response.ok().entity(players).build();
    }

    @GET
    @Path("/{id:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayer(@PathParam("id") String id) {

        Player player = playerRepository.getPlayer(Long.parseLong(id));

        if (player == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(player).build();
    }

    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlayer(Player player) {
        Long id = playerRepository.createPlayer(player);

        URI uri = uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("{id}")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putPlayer(@PathParam("id") String id, Player player) {

        Player persistedPlayer = playerRepository.updatePlayer(Long.parseLong(id), player);

        if (persistedPlayer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();
    }

    @DELETE
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("{id}")
    public Response deletePlayer(@PathParam("id") String id) {

        if (!playerRepository.deletePlayer(Long.parseLong(id))) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();
    }
}