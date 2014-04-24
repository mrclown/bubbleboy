package com.clowndata.rest.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by 2014.
 */
@Provider
public class IllegalStateExceptionHandler implements ExceptionMapper<IllegalStateException> {

    @Override
    public Response toResponse(IllegalStateException e) {
        return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN_TYPE).build();
    }
}
