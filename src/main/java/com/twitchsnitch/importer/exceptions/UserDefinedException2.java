package com.twitchsnitch.importer.exceptions;

import com.twitchsnitch.importer.service.TwitchDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDefinedException2 extends RuntimeException {

    private final static Logger log = LoggerFactory.getLogger(UserDefinedException2.class);

    public UserDefinedException2(String errorBody) {
        log.error(errorBody);
    }
//your class definition which includes error attributes... etc
}