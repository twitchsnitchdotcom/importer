package com.twitchsnitch.importer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ExecutionService {

    private final static Logger log = LoggerFactory.getLogger(ExecutionService.class);
    private TwitchDataService twitchDataService;
    private OAuthService oAuthService;
    private boolean executionEnabled;
    private boolean refreshTokensEnabled;

    public ExecutionService(TwitchDataService twitchDataService){
        this.twitchDataService = twitchDataService;
    }

    //placeholder for execution stuff
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void execute(){
        if(executionEnabled){
            twitchDataService.importChattersOnDB();
        }
        executionEnabled = true;
    }

    //every hour, refresh the tokens
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void refreshTokens(){
        if(refreshTokensEnabled){
            oAuthService.newTokens(true);
        }
        refreshTokensEnabled = true;
    }

    
}
