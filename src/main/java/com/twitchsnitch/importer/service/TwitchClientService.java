package com.twitchsnitch.importer.service;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.twitchsnitch.importer.dto.twitch.OAuthTokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitchClientService {

    private OAuthService oAuthService;

    @Value("${oauth.token.list.size}")
    private int oAuthTokenListSize;

    public TwitchClientService(OAuthService oAuthService){
        this.oAuthService = oAuthService;
    }
    private final static Logger log = LoggerFactory.getLogger(TwitchClientService.class);

    private List<TwitchClient> twitchClients;

    public List<TwitchClient> rePopulateTwitchClient(){
        List<OAuthTokenDTO> tokens = oAuthService.getTokens();
        if(tokens == null){
            log.error("This shouldn't happen, tokens should be there now");
        }
        twitchClients = null;
        List<TwitchClient> twitchClients = new ArrayList<>();
        for(OAuthTokenDTO token : tokens){
            log.debug("Adding twitch client: " + token.getName() + " " + token.getToken());
            TwitchClient twitchClient = TwitchClientBuilder.builder()
                    .withDefaultAuthToken(new OAuth2Credential("twitch", token.getToken()))
                    .withEnableHelix(true)
                    .build();
            twitchClients.add(twitchClient);
        }
        return twitchClients;
    }

    public TwitchClient getRandomClient(){
        if(twitchClients == null){
            twitchClients = rePopulateTwitchClient();
        }
        int index = (int)(Math.random()*oAuthTokenListSize);
        TwitchClient twitchClient = twitchClients.get(index);
        return twitchClient;
    }

}
