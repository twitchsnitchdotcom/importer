package com.twitchsnitch.importer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.twitch.FollowsDTO;
import com.twitchsnitch.importer.dto.twitch.OAuthTokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static com.twitchsnitch.importer.service.TwitchDataService.errorHandler;

public class WebclientService {

    private final static Logger log = LoggerFactory.getLogger(WebclientService.class);

    private final OAuthService oAuthService;

    private final PersistenceService persistenceService;

    public WebclientService(OAuthService oAuthService, PersistenceService persistenceService){
        this.oAuthService = oAuthService;
        this.persistenceService = persistenceService;
    }

    WebClient webClient = WebClient.builder().filter(errorHandler()).baseUrl("https://api.twitch.tv/helix").build();

    public final ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    @Async
    public void importFollowsTo(){

        log.debug("Starting to run job importFollowsTo ");
        List<String> urls = new ArrayList<>();
        Set<String> usersWithoutTwitchFollowsTo = persistenceService.getUsersWithoutTwitchFollowsTo();
        log.debug("Starting to run job importFollowsTo, batch size: " + usersWithoutTwitchFollowsTo.size());
        for(String id: usersWithoutTwitchFollowsTo){
            String url = "/users/follows?first=100&to_id=" + id;
            urls.add(url);
        }
                urls.stream()
                .parallel()
                .map(this::getItems)
                .map(d -> d.share().block())
                .forEach(d -> {
                    log.debug("Running request: " +  d.hashCode() );
                });

    }

    public Mono<List<byte[]>> getItems(String url) {
        return fetchFollowsItems(url).expand(response -> {
            String cursor = null;
            FollowsDTO followsDTO = null;
             try {
                 followsDTO = objectMapper().readValue(response, FollowsDTO.class);
                 followsDTO.setUrl(url);
                 persistenceService.persistTwitchFollowersTo(objectMapper().readValue(response, Map.class));

                 if (followsDTO.getPagination().getCursor() == null) {
                     return Mono.empty();
                 }
                 else{
                     cursor = followsDTO.getPagination().getCursor();
                 }

             } catch (JsonProcessingException e) {
                 throw new RuntimeException(e);
             }
            return fetchFollowsItems(generateCorrectUrl(url, cursor));
        }).flatMap(response -> Flux.fromIterable(Arrays.asList(response.getBytes()))).collectList();
    }

    private String generateCorrectUrl(String url, String cursor){

        if(cursor != null){
            if(url.contains("after=")){
                int length = url.split("after=")[1].length();
                String substring = url.substring(0, length);
                String finalUrl = substring + cursor;
                return finalUrl;
            }
            else{
                String finalUrl = url + "&after=" + cursor;
            }
        }
        return url;

    }

    private Mono<String> fetchFollowsItems(String url) {
        OAuthTokenDTO localToken = oAuthService.getRandomToken();
        return webClient.get().uri(url)
                .header("Content-Type", "application/json")
                .header("Client-ID", localToken.getClientId())
                .header("Authorization", "Bearer " + localToken.getToken())
                .retrieve()
                .bodyToMono(String.class);
    }


//    public void getFollowersFrom(String url) {
//        log.debug("Processing followers url: " + url);
//         fetchFollowsItems(url).expand(response -> {
//             try {
//                 FollowsDTO followsDTO = null;
//                 followsDTO = objectMapper().readValue(response, FollowsDTO.class);
//                 persistenceService.persistTwitchFollowersFrom(objectMapper().readValue(response, Map.class));
//                 if (followsDTO.getPagination().getCursor() != null) {
//                    url = url + "&after=" + cursor;
//
//                     fetchFollowsItems(followsDTO.getPagination().getCursor());
//                 }
//             } catch (JsonProcessingException e) {
//                 throw new RuntimeException(e);
//             }
//        }).flatMap().collectList();
//    }
}
