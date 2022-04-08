package com.twitchsnitch.importer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.twitch4j.helix.domain.*;
import com.github.twitch4j.helix.domain.Game;
import com.twitchsnitch.importer.dto.sully.channels.ChannelDatum;
import com.twitchsnitch.importer.dto.sully.games.GamesDatum;
import com.twitchsnitch.importer.dto.sully.teams.TeamsDatum;
import com.twitchsnitch.importer.dto.twitch.*;
import com.twitchsnitch.importer.dto.sully.channels.ChannelsTable;
import com.twitchsnitch.importer.dto.sully.games.GamesTable;
import com.twitchsnitch.importer.dto.sully.teams.TeamsTable;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TwitchDataService {

    @Value("${data.filepath}")
    private String dataFilePath;
    @Value("${test.sampling}")
    private boolean testSampling;
    @Value("${number.records}")
    private int numberOfRecords;
    @Value("${games.days.perspective}")
    private int gamesDaysPerspective;
    @Value("${teams.days.perspective}")
    private int teamsDaysPerspective;
    @Value("${channel.days.perspective}")
    private int channelDaysPerspective;
    @Value("${testing}")
    private boolean testing;
    private final OAuthService oAuthService;
    private final TwitchClientService twitchClientService;
    private final PersistenceService persistenceService;
    private Neo4jClient client;
    private ChromeDriver primaryDriver;
    private ChromeDriver secondaryDriver;
    private ChromeDriver tertiaryDriver;
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

    //GENERIC METHODS
    @PostConstruct
    public void initWebDriver() throws URISyntaxException {
        File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");
        primaryDriver = new ChromeDriver(options);
        primaryDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        secondaryDriver = new ChromeDriver(options);
        secondaryDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        tertiaryDriver = new ChromeDriver(options);
        tertiaryDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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

    public String goToWebSite(String url, ChromeDriver driver) {
        try {
            driver.get("view-source:" + url);
            String json = driver.findElement(By.className("line-content")).getText();
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(driver.getPageSource());
        }
        return null;
    }

    public List<String> goToWebSites(List<String> urls, ChromeDriver driver) {
        List<String> jsonList = new ArrayList<>();
        for (String url : urls) {
            try {
                driver.get("view-source:" + url);
                jsonList.add(driver.findElement(By.className("line-content")).getText());
                log.debug("Pages collected so far is: " + jsonList.size());
            } catch (Exception e) {
                log.error("The URL that failed is: " + url);
                e.printStackTrace();
            }
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

    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.error("BIG PROBLEM, CANT ENCODE THIS STRING: " + value);
        return null;
    }

    //DB METHODS

    public void runDBConstraints(){
        persistenceService.runDBConstraints();
    }

    public void dropDbConstraints(){
        persistenceService.dropDBConstraints();
    }

    //todo import live streams and chatters
    public void importLiveStreamersAndChatters(){

    }


    //import followers to and from
    //todo need to import channel streams and channel games in the same loop to avoid a whole mess
    //todo need to do game picker and raid picker here

//    TeamsTable individualTeam = objectMapper().readValue(json, TeamsTable.class);
//                for(TeamsDatum team: individualTeam.getData()){
//        Long teamId = team.getId();
//        String encodedName = encodeValue(team.getName());
//        String urlFirstPage = "https://sullygnome.com/api/tables/teamtables/getteamchannels/7/0/" + teamId + "/" + encodedName + "/000/1/3/desc/0/";
//        String teamJson = goToWebSite(urlFirstPage, secondaryDriver);
//        //todo I am here doing channels table
//        TeamsTable currentTeams = objectMapper().readValue(goToWebSite(teamsScaffoldUrl, secondaryDriver), TeamsTable.class);
//        teamTotalSize = teamsTable.getRecordsTotal();
//        log.debug("Actual Teams size: " + teamTotalSize);
//        if (testing) {
//            teamTotalSize = 1001;
//        }
//        List<String> teamsUrls = buildUpSubSequentUrls(teamsprefix, suffix, teamTotalSize);
//
//        persistenceService.persistSullyMembersOfTeam(teamId, teamJson);


    //SULLY METHODS
    public void importGames() {
        String suffix = "/" + numberOfRecords;
        String gameScaffoldUrl = "https://sullygnome.com/api/tables/gametables/getgames/" + gamesDaysPerspective + "/%20/0/1/3/desc/0/" + numberOfRecords;
        String gamePrefix = "https://sullygnome.com/api/tables/gametables/getgames/" + gamesDaysPerspective + "/%20/0/1/3/desc/";
        long gamesTotalSize;
        try {
            GamesTable gamesTable = objectMapper().readValue(goToWebSite(gameScaffoldUrl, tertiaryDriver), GamesTable.class);
            gamesTotalSize = gamesTable.getRecordsTotal();
            log.debug("Actual Game size: " + gamesTotalSize);
            if (testing) {
                gamesTotalSize = 1001; //todo remove when read for prod
            }
            List<String> gamesUrls = buildUpSubSequentUrls(gamePrefix, suffix, gamesTotalSize);
            OAuthTokenDTO localToken = oAuthService.getRandomToken();
            for (String json : goToWebSites(gamesUrls, tertiaryDriver)) {
                persistenceService.persistSullyGames(gamesDaysPerspective, objectMapper().readValue(json, Map.class));
                GamesTable gamesToGetTwitchIds  = objectMapper().readValue(json, GamesTable.class);
                Set<String> gamesNameList = new HashSet<>();
                for(GamesDatum data : gamesToGetTwitchIds.getData()){
                    gamesNameList.add(encodeValue(data.getName()));
                }
                Map map = runGetGame(gamesNameList, localToken);
                persistenceService.updateGameWithTwitchData(map);
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

    }

    public void importChannels() {
        String suffix = "/" + numberOfRecords;
        String channelsScaffoldUrl = "https://sullygnome.com/api/tables/channeltables/getchannels/" + channelDaysPerspective + "/0/11/3/desc/0/" + numberOfRecords;
        String channelPrefix = "https://sullygnome.com/api/tables/channeltables/getchannels/" + channelDaysPerspective + "/0/11/3/desc/";
        long channelTotalSize;
        try {
            ChannelsTable channelsTable = objectMapper().readValue(goToWebSite(channelsScaffoldUrl, primaryDriver), ChannelsTable.class);
            channelTotalSize = channelsTable.getRecordsTotal();
            log.debug("Actual Channel size: " + channelTotalSize);
            if (testing) {
                channelTotalSize = 1001;
            }
            List<String> channelUrls = buildUpSubSequentUrls(channelPrefix, suffix, channelTotalSize);
            OAuthTokenDTO localToken = oAuthService.getRandomToken();
            for (String json : goToWebSites(channelUrls, primaryDriver)) {
                persistenceService.persistSullyChannels(channelDaysPerspective, objectMapper().readValue(json, Map.class));
                ChannelsTable currentChannels = objectMapper().readValue(json, ChannelsTable.class);
                Set<String> loginNames = new HashSet<>();
                for(ChannelDatum data :currentChannels.getData()){
                    loginNames.add(data.getUrl());
                }
                Map map = runGetUser(loginNames, localToken);
                persistenceService.updateUserWithTwitchData(map);
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

    }

    public void importTeams() {
        String suffix = "/" + numberOfRecords;
        Set<TeamsTable> allTeams = new HashSet<>();
        //teams result list
        String teamsScaffoldUrl = "https://sullygnome.com/api/tables/teamtables/getteams/" + teamsDaysPerspective + "/0/1/3/desc/0/" + numberOfRecords;
        String teamsprefix = "https://sullygnome.com/api/tables/teamtables/getteams/" + teamsDaysPerspective + "/0/1/3/desc/";
        long teamTotalSize;
        try {
            TeamsTable teamsTable = objectMapper().readValue(goToWebSite(teamsScaffoldUrl, secondaryDriver), TeamsTable.class);
            teamTotalSize = teamsTable.getRecordsTotal();
            log.debug("Actual Teams size: " + teamTotalSize);
            if (testing) {
                teamTotalSize = 1001;
            }
            List<String> teamsUrls = buildUpSubSequentUrls(teamsprefix, suffix, teamTotalSize);
            OAuthTokenDTO localToken = oAuthService.getRandomToken();
            for (String json : goToWebSites(teamsUrls, secondaryDriver)) {
                persistenceService.persistSullyTeams(teamsDaysPerspective, objectMapper().readValue(json, Map.class));
                TeamsTable currentTeam = objectMapper().readValue(json, TeamsTable.class);
                for(TeamsDatum data : currentTeam.getData()){
                    Map map = runGetTeam(data.getName(), localToken);
                    persistenceService.updateTeamWithTwitchData(map);

                    //do individual requests but also set the team members in this request because we have access to it
                }
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    //REST METHODS
    public Map runGetChatters(String loginName) {
        String url = "http://tmi.twitch.tv/group/user/" + loginName + "/chatters";

        try {
            // make an HTTP GET request with headers
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    getGenericHttpRequest(null),
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return objectMapper().readValue(response.getBody(), Map.class);
            }
        } catch (HttpClientErrorException e) {
            log.debug("Error getting username chatters: " + loginName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map runGetGame(Set<String> encodedGameDisplayNames, OAuthTokenDTO oAuthTokenDTO) {
        StringBuilder url = new StringBuilder("https://api.twitch.tv/helix/games?name=");
        boolean firstValue = true;
        for (String name : encodedGameDisplayNames) {
            if (firstValue) {
                url.append(encodeValue(name));
                firstValue = false;
            } else {
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
                return objectMapper().readValue(response.getBody(), Map.class);
            }
        } catch (HttpClientErrorException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map runGetUser(Set<String> loginNames, OAuthTokenDTO oAuthTokenDTO) {
        StringBuilder url = new StringBuilder("https://api.twitch.tv/helix/user?login=");
        boolean firstValue = true;
        for (String name : loginNames) {
            if (firstValue) {
                url.append(encodeValue(name));
                firstValue = false;
            } else {
                url.append("&login=" + encodeValue(name));
            }
        }

        try {

            ResponseEntity<String> response = restTemplate.exchange(
                    url.toString(),
                    HttpMethod.GET,
                    getGenericHttpRequest(oAuthTokenDTO),
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return objectMapper().readValue(response.getBody(), Map.class);
            }
        } catch (HttpClientErrorException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map runGetTeam(String teamName, OAuthTokenDTO oAuthTokenDTO) {
        String url = "https://api.twitch.tv/helix/teams?name=" + teamName;
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    getGenericHttpRequest(oAuthTokenDTO),
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return objectMapper().readValue(response.getBody(), Map.class);
            }
        } catch (HttpClientErrorException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}





