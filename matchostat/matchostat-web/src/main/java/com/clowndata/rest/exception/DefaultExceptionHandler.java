package com.clowndata.rest.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by 2014.
 */
    //@Provider
    public class DefaultExceptionHandler implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception e) {
            //e.getMessage()
            return Response.serverError().entity("rajraj").type(MediaType.TEXT_PLAIN_TYPE).build();
        }
    }

