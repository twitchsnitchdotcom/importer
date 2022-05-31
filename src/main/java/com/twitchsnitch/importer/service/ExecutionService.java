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
    private boolean executionEnabled = false;
    private boolean refreshTokensEnabled = false;

    public ExecutionService(TwitchDataService twitchDataService){
        this.twitchDataService = twitchDataService;
    }

    //placeholder for execution stuff
   // @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.MINUTES)
    public void execute(){
        if(this.executionEnabled){
            twitchDataService.importChattersOnDB();
            twitchDataService.importLiveStreams();
        }
        this.executionEnabled = true;
    }

    //@Scheduled(fixedDelay = 15, timeUnit = TimeUnit.MINUTES)
    public void executeChatters(){
            twitchDataService.importChattersOnDB();
    }

    //every hour, refresh the tokens
    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.MINUTES)
    public void refreshTokens(){
        if(this.refreshTokensEnabled){
            oAuthService.newTokens(false);
        }
        this.refreshTokensEnabled = true;
    }


}
