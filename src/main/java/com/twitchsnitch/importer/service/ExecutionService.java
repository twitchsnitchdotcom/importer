package com.twitchsnitch.importer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService {

    private final static Logger log = LoggerFactory.getLogger(ExecutionService.class);
    private TwitchDataService twitchDataService;
    private OAuthService oAuthService;

    public ExecutionService(TwitchDataService twitchDataService){
        this.twitchDataService = twitchDataService;
    }

    //placeholder for execution stuff
    public void execute(){
        twitchDataService.importChattersOnDB();
    }

    //every hour, refresh the tokens
    public void refreshTokens(){
        oAuthService.newTokens(true);
    }
}
