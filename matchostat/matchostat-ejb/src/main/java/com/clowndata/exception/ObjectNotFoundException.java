package com.clowndata.exception;

/**
 * Created by 2014.
 */
public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(Long id, Class className) {
        super(className.getSimpleName()+ " id: " + id.toString() + " not found");
    }
}
