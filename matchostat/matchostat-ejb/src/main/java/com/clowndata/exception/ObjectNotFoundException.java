package com.clowndata.exception;

/**
 * Created by 2014.
 */
public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(Class className, Long id) {
        super(className.getSimpleName() + " id: " + id.toString() + " not found");
    }
}
