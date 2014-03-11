package com.clowndata.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class Configuration {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean isTest() {
        return test;
    }

    private boolean test;

    @PostConstruct
    public void init() {
        this.test = true; //TODO: Read from file?
        logger.info("Initialized: {}", this);
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "test=" + test +
                '}';
    }
}
