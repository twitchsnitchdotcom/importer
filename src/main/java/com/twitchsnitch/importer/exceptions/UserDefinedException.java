package com.twitchsnitch.importer.exceptions;

import com.twitchsnitch.importer.service.TwitchDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDefinedException extends RuntimeException {

    private final static Logger log = LoggerFactory.getLogger(TwitchDataService.class);

    public UserDefinedException(String errorBody) {
        log.error(errorBody);
    }
//your class definition which includes error attributes... etc
}