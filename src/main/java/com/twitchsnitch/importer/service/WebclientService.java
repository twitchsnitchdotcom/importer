package com.twitchsnitch.importer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.sully.SearchDTO;
import com.twitchsnitch.importer.dto.twitch.FollowsDTO;
import com.twitchsnitch.importer.dto.twitch.OAuthTokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static com.twitchsnitch.importer.service.TwitchDataService.errorHandler;

@Service
public class WebclientService {

    private final static Logger log = LoggerFactory.getLogger(WebclientService.class);

    private final OAuthService oAuthService;

    private final PersistenceService persistenceService;
    WebClient webClient = WebClient.builder().filter(errorHandler()).baseUrl("https://api.twitch.tv/helix").build();
    WebClient sullyWebClient = WebClient.builder().filter(errorHandler()).baseUrl("https://sullygnome.com/api").build();

    public WebclientService(OAuthService oAuthService, PersistenceService persistenceService) {
        this.oAuthService = oAuthService;
        this.persistenceService = persistenceService;
    }

    public final ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    private Mono<String> fetchSearchItems(String url) {
        return sullyWebClient.get().uri(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:77.0) Gecko/20100101 Firefox/77.0")
                .retrieve()
                .bodyToMono(String.class);
    }

    @Async
    public void importSullyIdChannels() {
        int cutoff = 30;
        for(int i=0; i<=cutoff; i++){
            log.debug("Running importSullyIdChannels batch: " + i);
            List<String> allUsersWithoutSullyId = persistenceService.getAllUsersWithoutSullyId();
            List<String> urls = new ArrayList<>();
            for (String login : allUsersWithoutSullyId) {
                String url = "/standardsearch/" + login + "/true/true/false/true";
                urls.add(url);
            }
            urls.stream()
                    .parallel()
                    .map(this::fetchSearchItems)
                    .map(d -> d.share().block())
                    .forEach(d -> {
                        try {
                            List<SearchDTO> searchDTOList = objectMapper().readValue(d, new TypeReference<List<SearchDTO>>() {
                            });
                            for(SearchDTO dto: searchDTOList){
                                if(dto.getItemtype() == 1 && allUsersWithoutSullyId.stream().anyMatch(dto.getSiteurl()::contains)){
                                    persistenceService.persistSullySearch(dto);
                                }
                            }
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                    });
        }


    }


    @Async
    public void importFollowsTo() {

        log.debug("Starting to run job importFollowsFrom ");
        List<String> urls = new ArrayList<>();
        Set<String> usersWithoutTwitchFollowsFrom = persistenceService.getUsersWithoutTwitchFollowsFrom();
        log.debug("Starting to run job importFollowsFrom, batch size: " + usersWithoutTwitchFollowsFrom.size());
        for (String id : usersWithoutTwitchFollowsFrom) {
            String url = "/users/follows?first=100&from_id=" + id;
            urls.add(url);
        }
        urls.stream()
                .parallel()
                .map(this::getItems)
                .map(d -> d.share().block())
                .forEach(d -> {
                    log.debug("Running request: " + d.hashCode());
                });

    }

    public Mono<List<byte[]>> getItems(String url) {
        return fetchFollowsItems(url).expand(response -> {
            String cursor = null;
            FollowsDTO followsDTO = null;
            try {
                followsDTO = objectMapper().readValue(response, FollowsDTO.class);
                followsDTO.setUrl(url);
                persistenceService.persistTwitchFollowersFrom(objectMapper().readValue(response, Map.class));

                if (followsDTO.getPagination().getCursor() == null) {
                    return Mono.empty();
                } else {
                    cursor = followsDTO.getPagination().getCursor();
                }

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return fetchFollowsItems(generateCorrectUrl(url, cursor));
        }).flatMap(response -> Flux.fromIterable(Arrays.asList(response.getBytes()))).collectList();
    }

    private String generateCorrectUrl(String url, String cursor) {

        if (cursor != null) {
            if (url.contains("after=")) {
                int length = url.split("after=")[1].length();
                String substring = url.substring(0, length);
                String finalUrl = substring + cursor;
                return finalUrl;
            } else {
                String finalUrl = url + "&after=" + cursor;
                return finalUrl;
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
