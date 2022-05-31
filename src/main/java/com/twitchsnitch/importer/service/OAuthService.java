package com.twitchsnitch.importer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.twitch.OAuthTokenDTO;
import com.twitchsnitch.importer.dto.twitch.RefreshTokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OAuthService {

    @Value("${oauth.token.list.size}")
    private int oAuthTokenListSize;
    @Value("${token.remaining.limit}")
    private int tokenRemainingLimit;

    private static final Logger log = LoggerFactory.getLogger(OAuthService.class);

    private List<OAuthTokenDTO> tokens = null;

    private RestTemplate restTemplate = new RestTemplate();

    public final ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    @PostConstruct
    public void init(){
        readTokensFromFile();
        newTokens(false);
    }

    public OAuthTokenDTO validateTokenLimit(int remainingLimit, OAuthTokenDTO currentToken){
        if(remainingLimit < tokenRemainingLimit){
            log.debug("Token: " + currentToken.getName() + " is getting near it request limit, swopped to new token");
            return getRandomToken();
        }
        return currentToken;
    }

    public void readTokensFromFile() {
        try {
            URL resource = getClass().getClassLoader().getResource("tokens.json");
            File tokensFile = new File (resource.toURI());
            tokens =  objectMapper().readValue(tokensFile, new TypeReference<List<OAuthTokenDTO>>() {});
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void newTokens(boolean refresh){
        String url = null;
        List<OAuthTokenDTO> newTokenList = new ArrayList<>();
        for(OAuthTokenDTO oAuthTokenDTO: tokens){
            log.debug("trying to refresh token: " + oAuthTokenDTO.getName());
            if(refresh){
                url =  "https://id.twitch.tv/oauth2/token?grant_type=refresh_token&client_id=" + oAuthTokenDTO.getClientId() + "&client_secret=" + oAuthTokenDTO.getClientSecret()  + "&grant_type=client_credentials&refresh_token=" + oAuthTokenDTO.getRefreshToken();
            }
            else{
                url =  "https://id.twitch.tv/oauth2/token?client_id=" + oAuthTokenDTO.getClientId() + "&client_secret=" + oAuthTokenDTO.getClientSecret()  + "&grant_type=client_credentials";
            }
            // make an HTTP GET request with headers
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    getGenericHttpRequest(oAuthTokenDTO),
                    String.class
            );

            log.debug(response.getBody());
            try{
                RefreshTokenDTO refreshTokenDTO = objectMapper().readValue(response.getBody(),RefreshTokenDTO.class);

                log.debug(response.getBody());
                if(response.getStatusCode().is2xxSuccessful()){
                    //log.debug("Successfully got new token from twitch: " + refreshTokenDTO.getAccessToken());
                    oAuthTokenDTO.setToken(refreshTokenDTO.getAccessToken());
                    if(refreshTokenDTO.getRefreshToken() != null) {
                        oAuthTokenDTO.setRefreshToken(refreshTokenDTO.getRefreshToken());
                    }
                    oAuthTokenDTO.setExpiresIn(refreshTokenDTO.getExpiresIn());
                    newTokenList.add(oAuthTokenDTO);
                }
                else{
                    log.error("Unable to refresh token: " + oAuthTokenDTO.getName());
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        this.tokens = newTokenList;
    }

    public HttpEntity getGenericHttpRequest(OAuthTokenDTO randomToken){
        // create headers
        HttpHeaders headers = new HttpHeaders();

// set `Content-Type` and `Accept` headers
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        if(randomToken != null){
            // example of custom header
            headers.set("Client-ID", randomToken.getClientId());
            headers.set("Authorization", "Bearer " + randomToken.getToken());
            headers.set("User-Agent", "PostmanRuntime/7.29.0");  //todo change to browser agent
        }

        HttpEntity request = new HttpEntity(headers);

        return request;
    }

    public OAuthTokenDTO getRandomToken(){
        int randomTokenIndex = (int) (Math.random()*oAuthTokenListSize);
        OAuthTokenDTO oAuthTokenDTO = tokens.get(randomTokenIndex);
        return oAuthTokenDTO;
    }

    public List<OAuthTokenDTO> getTokens() {
        if(tokens == null){
            readTokensFromFile();
            newTokens(false);
        }
        return tokens;
    }
}
