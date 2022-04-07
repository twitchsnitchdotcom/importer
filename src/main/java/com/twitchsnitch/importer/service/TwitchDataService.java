package com.twitchsnitch.importer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.twitch4j.helix.domain.*;
import com.github.twitch4j.helix.domain.Game;
import com.twitchsnitch.importer.dto.twitch.*;
import com.twitchsnitch.importer.dto.sully.channels.ChannelsTable;
import com.twitchsnitch.importer.dto.sully.games.GamesTable;
import com.twitchsnitch.importer.dto.sully.teams.TeamsTable;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class TwitchDataService {

    @Value("${data.filepath}")
    private String dataFilePath;
    @Value("${test.sampling}")
    private boolean testSampling;
    @Value("${number.records}")
    private int numberOfRecords;
    @Value("${testing}")
    private boolean testing;
    private final OAuthService oAuthService;
    private final TwitchClientService twitchClientService;
    private final PersistenceService persistenceService;
    private Neo4jClient client;
    private ChromeDriver driver;
    private RestTemplate restTemplate = new RestTemplate();
    private Set<String> liveStreamers;

    public final ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    public TwitchDataService(OAuthService oAuthService, TwitchClientService twitchClientService, PersistenceService persistenceService) {
        this.oAuthService = oAuthService;
        this.twitchClientService = twitchClientService;
        this.persistenceService = persistenceService;
    }

    private final static Logger log = LoggerFactory.getLogger(TwitchDataService.class);

    //@PostConstruct
    public void initWebDriver() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("chromedriver");
        File tokensFile = new File(resource.toURI());
        System.setProperty("webdriver.chrome.driver", tokensFile.getAbsolutePath());
        driver = new ChromeDriver();
    }

    public HttpEntity getGenericHttpRequest(OAuthTokenDTO randomToken) {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        if (randomToken != null) {
            headers.set("Client-ID", randomToken.getClientId());
            headers.set("Authorization", "Bearer " + randomToken.getToken());
        }

        HttpEntity request = new HttpEntity(headers);

        return request;
    }

    public String goToWebSite(String url) {
        driver.get(url);
        return driver.findElement(By.tagName("pre")).getText();
    }

    public List<String> goToWebSites(List<String> urls) {
        List<String> jsonList = new ArrayList<>();
        for (String url : urls) {
            driver.get(url);
            jsonList.add(driver.findElement(By.tagName("pre")).getText());
        }
        return jsonList;
    }

    private List<String> buildUpSubSequentUrls(String prefix, String suffix, long resultSize) {
        List<String> urls = new ArrayList<>();
        long pages = resultSize / 100;
        log.debug("Original result size is: " + resultSize);
        log.debug("Total number of pages is: " + pages);
        for (int i = 0; i < pages; i++) {
            Integer pagination = i * 100;
            urls.add(prefix + pagination.toString() + suffix);
        }
        return urls;
    }

    public Set<GamesTable> importSullyGameSummaries(){
        Set<GamesTable> allGames = new HashSet<>();
        Map<Integer, String> summaryMap = new HashMap<>();
        String days3 = "https://sullygnome.com/api/tables/gametables/getgames/3/%20/0/1/3/desc/0/10";
        String days7 = "https://sullygnome.com/api/tables/gametables/getgames/7/%20/0/1/3/desc/0/10";
        String days14 = "https://sullygnome.com/api/tables/gametables/getgames/14/%20/0/1/3/desc/0/10";
        String days30 = "https://sullygnome.com/api/tables/gametables/getgames/30/%20/0/1/3/desc/0/10";
        String days90 = "https://sullygnome.com/api/tables/gametables/getgames/90/%20/0/1/3/desc/0/10";
        String days180 = "https://sullygnome.com/api/tables/gametables/getgames/180/%20/0/1/3/desc/0/10";
        String days365 = "https://sullygnome.com/api/tables/gametables/getgames/365/%20/0/1/3/desc/0/10";
        summaryMap.put(3, days3);
        summaryMap.put(7, days7);
        summaryMap.put(14, days14);
        summaryMap.put(30, days30);
        summaryMap.put(90, days90);
        summaryMap.put(180, days180);
        summaryMap.put(365, days365);
        for(Map.Entry<Integer,String> entry : summaryMap.entrySet()){
            String json = goToWebSite(entry.getValue());
            try {
                GamesTable gamesTable = objectMapper().readValue(json, GamesTable.class);
                gamesTable.setDays(entry.getKey());
                allGames.add(gamesTable);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }

        return allGames;

    }

    public void importSullyTeamSummaries(){
        Set<TeamsTable> allTeams = new HashSet<>();
        Map<Integer, String> summaryMap = new HashMap<>();
        String days3 = "https://sullygnome.com/api/tables/teamtables/getteams/3/0/1/3/desc/0/10";
        String days7 = "https://sullygnome.com/api/tables/teamtables/getteams/7/0/1/3/desc/0/10";
        String days14 = "https://sullygnome.com/api/tables/teamtables/getteams/14/0/1/3/desc/0/10";
        String days30 = "https://sullygnome.com/api/tables/teamtables/getteams/30/0/1/3/desc/0/10";
        summaryMap.put(3, days3);
        summaryMap.put(7, days7);
        summaryMap.put(14, days14);
        summaryMap.put(30, days30);
        for(Map.Entry<Integer,String> entry : summaryMap.entrySet()){
            String json = goToWebSite(entry.getValue());
            try {
                TeamsTable teamsTable = objectMapper().readValue(json, TeamsTable.class);
                teamsTable.setDays(entry.getKey());
                allTeams.add(teamsTable);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        persistenceService.persistSullyTeamSummaries(allTeams);

    }

    public void importSullyChannelSummaries(){
        Set<ChannelsTable> allChannels = new HashSet<>();
        Map<Integer, String> summaryMap = new HashMap<>();
        String days3 = "https://sullygnome.com/api/tables/channeltables/getchannels/3/0/11/3/desc/0/10";
        String days7 = "https://sullygnome.com/api/tables/channeltables/getchannels/7/0/11/3/desc/0/10";
        String days14 = "https://sullygnome.com/api/tables/channeltables/getchannels/14/0/11/3/desc/0/10";
        String days30 = "https://sullygnome.com/api/tables/channeltables/getchannels/30/0/11/3/desc/0/10";
        String days90 = "https://sullygnome.com/api/tables/channeltables/getchannels/90/0/11/3/desc/0/10";
        String days180 = "https://sullygnome.com/api/tables/channeltables/getchannels/180/0/11/3/desc/0/10";
        String days365 = "https://sullygnome.com/api/tables/channeltables/getchannels/365/0/11/3/desc/0/10";
        summaryMap.put(3, days3);
        summaryMap.put(7, days7);
        summaryMap.put(14, days14);
        summaryMap.put(30, days30);
        summaryMap.put(90, days90);
        summaryMap.put(180, days180);
        summaryMap.put(365, days365);
        for(Map.Entry<Integer,String> entry : summaryMap.entrySet()){
            String json = goToWebSite(entry.getValue());
            try {
                ChannelsTable channelsTable = objectMapper().readValue(json, ChannelsTable.class);
                channelsTable.setDays(entry.getKey());
                allChannels.add(channelsTable);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        persistenceService.persistSullyChannelSummaries(allChannels);

    }

    public void importGames() {
        String suffix = "/" + numberOfRecords;
        String gameScaffoldUrl = "https://sullygnome.com/api/tables/gametables/getgames/30/%20/0/1/3/desc/0/" + numberOfRecords;
        String gamePrefix = "https://sullygnome.com/api/tables/gametables/getgames/30/%20/0/1/3/desc/";
        long gamesTotalSize;
        try {
            GamesTable gamesTable = objectMapper().readValue(goToWebSite(gameScaffoldUrl), GamesTable.class);
            gamesTotalSize = gamesTable.getRecordsTotal();
            log.debug("Actual Game size: " + gamesTotalSize);
            if (testing) {
                gamesTotalSize = 1001; //todo remove when read for prod
            }
            List<String> gamesUrls = buildUpSubSequentUrls(gamePrefix, suffix, gamesTotalSize);
            for (String json : goToWebSites(gamesUrls)) {
                persistenceService.persistSullyGames(objectMapper().readValue(json, Map.class));
                Set<String> allGamesWithoutTwitchIds = persistenceService.getAllGamesWithoutTwitchIds();
                updateTeamsWithTwitchId(getAllGamesByTwitchId(allGamesWithoutTwitchIds));
            }
        }
         catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

    }

    public void importChannels() {
        String suffix = "/" + numberOfRecords;
        String channelsScaffoldUrl = "https://sullygnome.com/api/tables/channeltables/getchannels/7/0/11/3/desc/0/" + numberOfRecords;
        String channelprefix = "https://sullygnome.com/api/tables/channeltables/getchannels/7/0/11/3/desc/";
        long channelTotalSize;
        try {
            ChannelsTable channelsTable = objectMapper().readValue(goToWebSite(channelsScaffoldUrl), ChannelsTable.class);
            channelTotalSize = channelsTable.getRecordsTotal();
            log.debug("Actual Channel size: " + channelTotalSize);
            if(testing){
                channelTotalSize = 101;  //todo comment out when its the real size
            }
            List<String> channelUrls = buildUpSubSequentUrls(channelprefix, suffix, channelTotalSize);
            for (String json : goToWebSites(channelUrls)) {
                persistenceService.persistSullyChannels(objectMapper().readValue(json, Map.class));
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

    }

    public Set<TeamsTable> importSullyTeams() {
        String suffix = "/" + numberOfRecords;
        Set<TeamsTable> allTeams = new HashSet<>();
        //teams result list
        String teamsScaffoldUrl = "https://sullygnome.com/api/tables/teamtables/getteams/7/0/1/3/desc/0/" + numberOfRecords;
        String teamsprefix = "https://sullygnome.com/api/tables/teamtables/getteams/7/0/1/3/desc/";
        long teamTotalSize;
        try {
            TeamsTable teamsTable = objectMapper().readValue(goToWebSite(teamsScaffoldUrl), TeamsTable.class);
            teamTotalSize = teamsTable.getRecordsTotal();
            log.debug("Actual Teams size: " + teamTotalSize);
            if(testing){
                teamTotalSize = 101;  //todo comment out when its the real size
            }

            List<String> teamsUrls = buildUpSubSequentUrls(teamsprefix, suffix, teamTotalSize);
            for (String json : goToWebSites(teamsUrls)) {
                allTeams.add(objectMapper().readValue(json, TeamsTable.class)); //todo make it more a merge, or call the neo4j client here so its lighter
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return allTeams;
    }

    public ChatterListDTO runGetChatters(String loginName) {
        String url = "http://tmi.twitch.tv/group/user/" + loginName + "/chatters";

        try {
            // make an HTTP GET request with headers
            ResponseEntity<ChatterListDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    getGenericHttpRequest(null),
                    ChatterListDTO.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (HttpClientErrorException e) {
            log.debug("Error getting username chatters: " + loginName);
        }
        return null;
    }

    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.error("BIG PROBLEM, CANT ENCODE THIS STRING: " + value);
        return null;
    }

    //todo enforce some limit so its max 100
    public Map<Map, GameListDTO> runGetGame(Set<String> gamesWithOutTwitchIds, OAuthTokenDTO oAuthTokenDTO) {
        Map<Map, GameListDTO> responseMap = new HashMap<>();
        StringBuilder url = new StringBuilder("https://api.twitch.tv/helix/games?name=");
        boolean firstValue = true;
        for(String name: gamesWithOutTwitchIds){
            if(firstValue){
                url.append(encodeValue(name));
                firstValue = false;
            }

            else{
                url.append("&name=" + encodeValue(name));
            }
        }

        try {

            ResponseEntity<String> response = restTemplate.exchange(
                    url.toString(),
                    HttpMethod.GET,
                    getGenericHttpRequest(oAuthTokenDTO),
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                responseMap.put(objectMapper().readValue(response.getBody(), Map.class),objectMapper().readValue(response.getBody(), GameListDTO.class));
                return responseMap;
            }
        } catch (HttpClientErrorException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public StreamListDTO runGetStreamList(OAuthTokenDTO randomToken, String cursor) {

        String url = "https://api.twitch.tv/helix/streams?first=100";
        if (cursor != null) {
            url += "&after=" + cursor;
        }

        // make an HTTP GET request with headers
        ResponseEntity<StreamListDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                getGenericHttpRequest(randomToken),
                StreamListDTO.class
        );

        if (response.getStatusCode().is4xxClientError()) {
            oAuthService.newTokens(true); //todo more sophisticated check for rate limit issues
        }

        return response.getBody();

    }

    public StreamTagListDTO runGetStreamTags(OAuthTokenDTO randomToken, String cursor){
        StringBuilder url = null;
        if(cursor == null){
            url = new StringBuilder("https://api.twitch.tv/helix/tags/streams?first=100");
        }
        else{
            url = new StringBuilder("https://api.twitch.tv/helix/tags/streams?first=100&after=" + cursor);
        }

        try {

            ResponseEntity<String> response = restTemplate.exchange(
                    url.toString(),
                    HttpMethod.GET,
                    getGenericHttpRequest(randomToken),
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                StreamTagListDTO streamTagListDTO = objectMapper().readValue(response.getBody(), StreamTagListDTO.class);
                streamTagListDTO.setMap(objectMapper().readValue(response.getBody(), LinkedHashMap.class));
                return streamTagListDTO;
            }
        } catch (HttpClientErrorException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void importGlobalStreams(int limit) {
        log.debug("Total number of streamers before reset = " + liveStreamers.size());
        liveStreamers = new HashSet<>();
        try {
            StreamListDTO resultList = runGetStreamList(oAuthService.getRandomToken(), null);
            if (resultList != null && resultList.getStreams() != null) {
                for (StreamDTO stream : resultList.getStreams()) {
                    liveStreamers.add(stream.getUserLogin());
                }
                String cursor = resultList.getPagination().getCursor();
                while (cursor != null && liveStreamers.size() < limit) {
                    StreamListDTO loopList = runGetStreamList(oAuthService.getRandomToken(), cursor);
                    if (loopList != null && loopList.getStreams() != null) {
                        String newCursor = loopList.getPagination().getCursor();
                        for (StreamDTO stream : loopList.getStreams()) {
                            liveStreamers.add(stream.getUserLogin());
                        }
                        if (newCursor == null) {
                            break;
                        } else {
                            cursor = newCursor;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        log.debug("Total size of all the Streams are: " + liveStreamers.size());
    }

    public void uploadChatters(int limit) {
        int index = 0;
        log.debug("Total number of streamers to get chatters for = " + liveStreamers.size());
        for (String username : liveStreamers) {
            if (index < limit) {
                try {
                    ChatterListDTO chatterListDTO = runGetChatters(username);
                    if (chatterListDTO != null) {
                        log.debug(chatterListDTO.getChatters().getBroadcaster().get(0) + " chatter count is: " + chatterListDTO.getChatterCount());
                    }
                } catch (Exception e) {
                    log.debug("Issue with username: " + username);
                }
                index++;
            }
        }
    }

    public void importGlobalStreamTags() {
        OAuthTokenDTO localToken = oAuthService.getRandomToken();
        try {
            StreamTagListDTO streamTags = runGetStreamTags(localToken, null);
            persistenceService.persistTwitchStreamTag(streamTags.getMap());
            if (streamTags != null) {
                String cursor = streamTags.getPagination().getCursor();
                while (cursor != null) {
                    StreamTagListDTO loopTags = runGetStreamTags(localToken, cursor);
                    persistenceService.persistTwitchStreamTag(loopTags.getMap());
                    if (loopTags != null) {
                        String newCursor = loopTags.getPagination().getCursor();
                        if (newCursor == null) {
                            break;
                        } else {
                            cursor = newCursor;
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public Set<Game> importGlobalTopGames() {
        Set<Game> totalSet = new HashSet<>();
        try {
            GameTopList resultList = twitchClientService.getRandomClient().getHelix().getTopGames(null, null, null, "100").execute();
            if (resultList != null && resultList.getGames() != null) {
                totalSet.addAll(resultList.getGames());
                String cursor = resultList.getPagination().getCursor();
                while (cursor != null) {
                    GameTopList loopList = twitchClientService.getRandomClient().getHelix().getTopGames(null, cursor, null, "100").execute();
                    if (loopList != null && loopList.getGames() != null) {
                        String newCursor = loopList.getPagination().getCursor();
                        totalSet.addAll(loopList.getGames());
                        if (newCursor == null) {
                            break;
                        } else {
                            cursor = newCursor;
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        log.trace("Total size of all the top Games is: " + totalSet.size());
        client.query("").run();
        return totalSet;
    }

    public void importGlobalStreams() {
        Set<Stream> totalSet = new HashSet<>();
        try {
            StreamList resultList = twitchClientService.getRandomClient().getHelix().getStreams(null, null, null, 100, null, null, null, null).execute();
            if (resultList != null && resultList.getStreams() != null) {
                totalSet.addAll(resultList.getStreams());
                String cursor = resultList.getPagination().getCursor();
                while (cursor != null) {
                    StreamList loopList = twitchClientService.getRandomClient().getHelix().getStreams(null, cursor, null, 100, null, null, null, null).execute();
                    if (loopList != null && loopList.getStreams() != null) {
                        String newCursor = loopList.getPagination().getCursor();
                        totalSet.addAll(loopList.getStreams());
                        if (newCursor == null) {
                            break;
                        } else {
                            cursor = newCursor;
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        log.trace("Total size of all the Streams are: " + totalSet.size());

        client.query("").run();
    }

    public void importChannelTeam(Set<String> broadcasterIdSet) {
        Set<TeamMember> totalSet = new HashSet<>();
        try {
            for (String broadcasterId : broadcasterIdSet) {
                TeamMembershipList resultList = twitchClientService.getRandomClient().getHelix().getChannelTeams(null, broadcasterId).execute();
                if (resultList != null && resultList.getTeams() != null) {
                    totalSet.addAll(resultList.getTeams());
                }
            }
        } catch (com.netflix.hystrix.exception.HystrixRuntimeException e) {
            log.error(e.getLocalizedMessage());
            log.error("POTENTIAL ISSUE WITH TOKEN: " + null);
        }
        log.trace("Total size of all the teams for channel is: " + totalSet.size());
        client.query("").run();
    }

    public void searchTeamByName(Set<String> teamNameSet) {
        Set<Team> totalSet = new HashSet<>();
        try {
            for (String teamName : teamNameSet) {
                TeamList resultList = twitchClientService.getRandomClient().getHelix().getTeams(null, teamName, null).execute();
                if (resultList != null && resultList.getTeams() != null) {
                    totalSet.addAll(resultList.getTeams());
                }
            }
        } catch (com.netflix.hystrix.exception.HystrixRuntimeException e) {
            log.error(e.getLocalizedMessage());
            log.error("POTENTIAL ISSUE WITH TOKEN: " + null);
        }
        log.trace("Total size of all the teams for channel is: " + totalSet.size());
        client.query("").run();
        client.query("").run();
    }

    public void importFollowersFrom(Set<String> userIdSet) {
        Set<FollowList> totalSet = new HashSet<>();
        try {
            for (String userId : userIdSet) {
                FollowList resultList = twitchClientService.getRandomClient().getHelix().getFollowers(null, userId, null, null, 100).execute();
                if (resultList != null) {
                    totalSet.add(resultList);
                    String cursor = resultList.getPagination().getCursor();
                    while (cursor != null) {
                        FollowList loopList = twitchClientService.getRandomClient().getHelix().getFollowers(null, userId, null, cursor, 100).execute();
                        if (loopList != null) {
                            String newCursor = loopList.getPagination().getCursor();
                            totalSet.add(loopList);
                            if (newCursor == null) {
                                break;
                            } else {
                                cursor = newCursor;
                            }
                        }
                    }
                }
            }
        } catch (com.netflix.hystrix.exception.HystrixRuntimeException e) {
            log.error(e.getLocalizedMessage());
            log.error("POTENTIAL ISSUE WITH TOKEN: " + null);
        }
        log.trace("Total size of all the followers from is: " + totalSet.size());
        client.query("").run();
    }

    public void importFollowersTo(Set<String> userIdSet) {
        Set<FollowList> totalSet = new HashSet<>();
        try {
            for (String userId : userIdSet) {
                FollowList resultList = twitchClientService.getRandomClient().getHelix().getFollowers(null, null, userId, null, 100).execute();
                if (resultList != null) {
                    totalSet.add(resultList);
                    String cursor = resultList.getPagination().getCursor();
                    while (cursor != null) {
                        FollowList loopList = twitchClientService.getRandomClient().getHelix().getFollowers(null, null, userId, cursor, 100).execute();
                        if (loopList != null) {
                            String newCursor = loopList.getPagination().getCursor();
                            totalSet.add(loopList);
                            if (newCursor == null) {
                                break;
                            } else {
                                cursor = newCursor;
                            }
                        }
                    }
                }
            }
        } catch (com.netflix.hystrix.exception.HystrixRuntimeException e) {
            log.error(e.getLocalizedMessage());
            log.error("POTENTIAL ISSUE WITH TOKEN: " + null);
        }
        log.trace("Total size of all the followers to is: " + totalSet.size());
        client.query("").run();
    }

    public void importUsers(Set<String> userIdsOnly) {

        Set<User> totalSet = new HashSet<>();

        try {
            List<String> arrayList = new ArrayList<>(userIdsOnly);
            try {
                UserList resultList = twitchClientService.getRandomClient().getHelix().getUsers(null, arrayList, null).execute();
                if (resultList != null && resultList.getUsers() != null) {
                    totalSet.addAll(resultList.getUsers());
                }

            } catch (com.netflix.hystrix.exception.HystrixRuntimeException e) {
                log.error(e.getLocalizedMessage());
            }
            log.trace("Total size of all the users for list is: " + totalSet.size());

        } catch (Exception e) {
            log.error(e.getMessage());

        }
        client.query("").run();
    }

    public void importChannelSchedule(String broadcasterId) {
        Set<StreamSchedule> totalSet = new HashSet<>();
        try {
            StreamScheduleResponse resultList = twitchClientService.getRandomClient().getHelix().getChannelStreamSchedule(null, broadcasterId, null, null, null, null, 100).execute();
            if (resultList != null && resultList.getSchedule() != null) {
                totalSet.add(resultList.getSchedule());
            }

        } catch (com.netflix.hystrix.exception.HystrixRuntimeException e) {
            log.error(e.getLocalizedMessage());
            log.error("POTENTIAL ISSUE WITH TOKEN: " + null);
        }
        log.trace("Total size of all the schedules for channel is: " + totalSet.size());
        client.query("").run();
    }

    public Map<Map, GameListDTO> getAllGamesByTwitchId(Set<String> sullyNames){
        OAuthTokenDTO localToken = oAuthService.getRandomToken();
        try {
            return runGetGame(sullyNames, localToken);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    public void updateTeamsWithTwitchId(Map<Map,GameListDTO> allGamesByTwitchId) {
        for (Map.Entry<Map,GameListDTO> entry : allGamesByTwitchId.entrySet())
            persistenceService.updateGameWithTwitchId(entry.getKey());
        }
    }

