package com.clowndata.rest;

/**
 * Created by 2014.
 */

import com.clowndata.model.valueobject.GameSummary;
import com.clowndata.service.GameService;
import com.clowndata.service.GameServiceImpl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 */
@Path("/games")
@RequestScoped
public class GameSummaryResource {

    @Inject
    private GameService gameService;

    @GET
    @Path("/{id:[0-9]*}/gamesummary")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame(@PathParam("id") String id) {

        GameSummary gameSummary = gameService.getGameSummary(Long.parseLong(id));

        return Response.ok().entity(gameSummary).build();
    }

}
