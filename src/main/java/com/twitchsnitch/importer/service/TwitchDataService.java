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
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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

    public List<String> goToWebSites(List<String> urls) {
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

    public void importTwitchGames() {
        Set<String> allGamesWithoutTwitchIds = persistenceService.getAllGamesWithoutTwitchIds();
        //SplittingUtils here to limit to 100
        OAuthTokenDTO localToken = oAuthService.getRandomToken();
        Map map = runGetGame(allGamesWithoutTwitchIds, localToken);
        persistenceService.updateGameWithTwitchId(map);
    }

    public void importSullyGames() {
        String suffix = "/" + numberOfRecords;
        String gameScaffoldUrl = "https://sullygnome.com/api/tables/gametables/getgames/" + gamesDaysPerspective + "/%20/0/1/3/desc/0/" + numberOfRecords;
        String gamePrefix = "https://sullygnome.com/api/tables/gametables/getgames/" + gamesDaysPerspective + "/%20/0/1/3/desc/";
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
                persistenceService.persistSullyGames(gamesDaysPerspective, objectMapper().readValue(json, Map.class));
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

    }

    public void importSullyChannels() {
        String suffix = "/" + numberOfRecords;
        String channelsScaffoldUrl = "https://sullygnome.com/api/tables/channeltables/getchannels/" + channelDaysPerspective + "/0/11/3/desc/0/" + numberOfRecords;
        String channelPrefix = "https://sullygnome.com/api/tables/channeltables/getchannels/" + channelDaysPerspective + "/0/11/3/desc/";
        long channelTotalSize;
        try {
            ChannelsTable channelsTable = objectMapper().readValue(goToWebSite(channelsScaffoldUrl), ChannelsTable.class);
            channelTotalSize = channelsTable.getRecordsTotal();
            log.debug("Actual Channel size: " + channelTotalSize);
            if (testing) {
                channelTotalSize = 1001;
            }
            List<String> channelUrls = buildUpSubSequentUrls(channelPrefix, suffix, channelTotalSize);
            for (String json : goToWebSites(channelUrls)) {
                persistenceService.persistSullyChannels(channelDaysPerspective, objectMapper().readValue(json, Map.class));
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

    }

    public void importSullyTeams() {
        String suffix = "/" + numberOfRecords;
        Set<TeamsTable> allTeams = new HashSet<>();
        //teams result list
        String teamsScaffoldUrl = "https://sullygnome.com/api/tables/teamtables/getteams/" + teamsDaysPerspective + "/0/1/3/desc/0/" + numberOfRecords;
        String teamsprefix = "https://sullygnome.com/api/tables/teamtables/getteams/" + teamsDaysPerspective + "/0/1/3/desc/";
        long teamTotalSize;
        try {
            TeamsTable teamsTable = objectMapper().readValue(goToWebSite(teamsScaffoldUrl), TeamsTable.class);
            teamTotalSize = teamsTable.getRecordsTotal();
            log.debug("Actual Teams size: " + teamTotalSize);
            if (testing) {
                teamTotalSize = 1001;
            }
            List<String> teamsUrls = buildUpSubSequentUrls(teamsprefix, suffix, teamTotalSize);
            for (String json : goToWebSites(teamsUrls)) {
                persistenceService.persistSullyTeams(teamsDaysPerspective, objectMapper().readValue(json, Map.class));
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

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

    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.error("BIG PROBLEM, CANT ENCODE THIS STRING: " + value);
        return null;
    }

    public Map runGetGame(Set<String> gamesWithOutTwitchIds, OAuthTokenDTO oAuthTokenDTO) {
        StringBuilder url = new StringBuilder("https://api.twitch.tv/helix/games?name=");
        boolean firstValue = true;
        for (String name : gamesWithOutTwitchIds) {
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
}





