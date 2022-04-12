package com.twitchsnitch.importer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.sully.channels.*;
import com.twitchsnitch.importer.dto.twitch.*;
import com.twitchsnitch.importer.dto.sully.games.GamesTable;
import com.twitchsnitch.importer.dto.sully.teams.TeamsTable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
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
    @Value("${follows.limit}")
    private int followsLimit;
    @Value("${games.days.perspective}")
    private int gamesDaysPerspective;
    @Value("${teams.days.perspective}")
    private int teamsDaysPerspective;
    @Value("${channels.days.perspective}")
    private int channelDaysPerspective;
    @Value("${testing}")
    private boolean testing;
    @Value("${database}")
    private String database;
    private final OAuthService oAuthService;
    private final TwitchClientService twitchClientService;
    private final PersistenceService persistenceService;
    private Neo4jClient client;
    private ChromeDriver primaryDriver;
    private ChromeDriver secondaryDriver;
    private ChromeDriver tertiaryDriver;
    private RestTemplate restTemplate = new RestTemplate();
    private Set<String> liveStreamers = new HashSet<>();

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

    public String goToWebSiteJSON(String url, ChromeDriver driver) {
        try {
            driver.get("view-source:" + url);
            String json = driver.findElement(By.className("line-content")).getText();
            return json;
        } catch (Exception e) {
            log.error("The URL that failed is: " + url + " I will wait another 2 seconds for the response");
            try {
                driver.get("view-source:" + url);
                Thread.sleep(2000);
                String json = driver.findElement(By.className("line-content")).getText();
                return json;
            } catch (Exception e2) {
                log.error("FAILED COLLECTING: " + url);
            }
        }
        return null;
    }

    public List<String> goToWebSitesJSON(List<String> urls, ChromeDriver driver) {
        List<String> jsonList = new ArrayList<>();
        for (String url : urls) {
            try {
                driver.get("view-source:" + url);
                jsonList.add(driver.findElement(By.className("line-content")).getText());
                log.debug("Pages collected so far is: " + jsonList.size());
            } catch (Exception e) {
                log.error("The URL that failed is: " + url + " I will wait another 2 seconds for the response");
                try {
                    driver.get("view-source:" + url);
                    Thread.sleep(2000);
                    jsonList.add(driver.findElement(By.className("line-content")).getText());

                } catch (Exception e2) {
                    log.error("FAILED COLLECTING: " + url);
                }

            }
        }
        return jsonList;
    }

    public List<String> goToWebSitesHTML(List<String> urls, ChromeDriver driver) {
        List<String> htmlList = new ArrayList<>();
        for (String url : urls) {
            try {
                driver.get(url);
                htmlList.add(driver.getPageSource());
                log.debug("Pages collected so far is: " + htmlList.size());
            } catch (Exception e) {
                log.error("The URL that failed is: " + url + " I will wait another 2 seconds for the response");
                try {
                    driver.get(url);
                    Thread.sleep(2000);
                    htmlList.add(driver.getPageSource());

                } catch (Exception e2) {
                    log.error("FAILED COLLECTING: " + url);
                }

            }
        }
        return htmlList;
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
    public void addDB() {
        persistenceService.addDatabase("system");
    }

    public void dropDB() {
        persistenceService.dropDatabase("system");
    }

    public void dropDBConstraints() {
        persistenceService.deleteDBData();
        persistenceService.dropDBConstraints();
    }

    public void addDBConstraints() {
        persistenceService.runDBConstraints();
    }

    //todo sixth
    public void raidPicker() {
        for (String login : liveStreamers) {
            String url = "https://sullygnome.com/api/tables/channeltables/raidfinder/30/571/%203/0/0/9999999/10000/50000/011/11/false/1/4/desc/0/100";
        }
    }

    //todo seventh
    public void gamePicker() {
        String url = "https://sullygnome.com/api/tables/gametables/getgamepickergames/88082/3196211/180/true/1/-1/-1/-1/10/5000/-1/10/-1/-1/1/0/desc/0/100";
    }

    public void importChannelGames() {
        try{
            String urlPrefix = "https://sullygnome.com/api/tables/channeltables/games/" + gamesDaysPerspective + "/";
            String urlSuffix = "/%20/1/2/desc/0/100";
            Set<Long> channelsWithoutChannelGameData = persistenceService.getChannelsWithoutChannelGameData();
            for (Long sullyId : channelsWithoutChannelGameData) {
                String json = goToWebSiteJSON(urlPrefix + sullyId + urlSuffix, tertiaryDriver);
                persistenceService.persistSullyChannelGames(sullyId, objectMapper().readValue(json, Map.class));
            }
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    public void importChannelStreamsDetail() {
        Set<Long> streamIdSet = persistenceService.getChannelStreamsWithoutIndividualData();
        try {
            for (Long id : streamIdSet) {
                String suffix = "/" + numberOfRecords;
                String channelStreamDetailScaffoldUrl = "https://sullygnome.com/api/tables/channeltables/streams/" + gamesDaysPerspective + "/" + id + "/%20/1/1/desc/10";
                String channelStreamDetailPrefix = "https://sullygnome.com/api/tables/channeltables/streams/" + gamesDaysPerspective + "/" + id + "/%20/1/1/desc";

                long streamsDetailTotalSize;
                ChannelStreamList channelStreamList = objectMapper().readValue(goToWebSiteJSON(channelStreamDetailScaffoldUrl, tertiaryDriver), ChannelStreamList.class);
                streamsDetailTotalSize = channelStreamList.getRecordsTotal();
                log.debug("Actual channel stream detail size: " + streamsDetailTotalSize);
                if (testing) {
                    streamsDetailTotalSize = 10; //todo remove when read for prod
                }
                List<String> streamsUrls = buildUpSubSequentUrls(channelStreamDetailPrefix, suffix, streamsDetailTotalSize);
                for (String html : goToWebSitesHTML(streamsUrls, tertiaryDriver)) {
                    IndividualStreamDTO individualStreamDTO = new IndividualStreamDTO();
                    Document doc = Jsoup.parse(html);
                    individualStreamDTO.setAverageViewers(Long.parseLong(doc.select("body > div.RightContent > div.MainContent > div.PageContentContainer > div.StandardPageContainer > div > div.MiniPanelContainer > div:nth-child(1) > div > div.MiniPanelTop > div.MiniPanelTopRight").get(0).text().replace(",", "")));
                    individualStreamDTO.setViewsPerHour(Double.parseDouble(doc.select("body > div.RightContent > div.MainContent > div.PageContentContainer > div.StandardPageContainer > div > div.MiniPanelContainer > div:nth-child(2) > div > div.MiniPanelTop > div.MiniPanelTopRight").get(0).text().replace(",", "")));
                    Elements streamSummaryPanels = doc.getElementsByClass("StreamGameSummaryPanel");
                    for (Element panel : streamSummaryPanels) {
                        IndividualStreamDataDTO individualStreamDataDTO = new IndividualStreamDataDTO();
                        Elements pannelItems = panel.getElementsByClass("StreamGameSummaryPanelItemValue");
                        individualStreamDataDTO.setGameName(pannelItems.get(0).text());
                        individualStreamDataDTO.setWatchTime(Long.parseLong(pannelItems.get(1).text().replace(",", "").replace(" hours", "")));
                        individualStreamDataDTO.setStreamLength(pannelItems.get(2).text());
                        Elements miniPanelOuter = panel.getElementsByClass("MiniPanelOuter");

                        individualStreamDataDTO.setAverageViewers(Long.parseLong(miniPanelOuter.get(0).getElementsByClass("MiniPanelTop").get(0).getElementsByClass("MiniPanelTopRight").get(0).text().replace(",", "")));
                        individualStreamDataDTO.setMaxViewers(Long.parseLong(miniPanelOuter.get(0).getElementsByClass("MiniPanelMiddle").get(0).getElementsByClass("MiniPanelMiddleRight").get(0).text().replace(",", "")));
                        individualStreamDataDTO.setMaxViewersPerformance(miniPanelOuter.get(0).getElementsByClass("MiniPanelBottom").get(0).getElementsByClass("MiniPanelBottomRight").get(0).text());
                        individualStreamDataDTO.setViewsPerHour(Double.parseDouble(miniPanelOuter.get(1).getElementsByClass("MiniPanelTop").get(0).getElementsByClass("MiniPanelTopRight").get(0).text().replace(",", "")));
                        individualStreamDataDTO.setViews(Long.parseLong(miniPanelOuter.get(1).getElementsByClass("MiniPanelMiddle").get(0).getElementsByClass("MiniPanelMiddleRight").get(0).text().replace(",", "")));
                        individualStreamDataDTO.setViewsPerformance(miniPanelOuter.get(1).getElementsByClass("MiniPanelBottom").get(0).getElementsByClass("MiniPanelBottomRight").get(0).text());
                        individualStreamDTO.getGamesPlayed().add(individualStreamDataDTO);
                    }
                    persistenceService.persistSullyChannelIndividualStream(individualStreamDTO);
                }

            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    public void importTwitchGameData(){
        Set<String> gamesNameList = new HashSet<>();
        Set<String> allGamesWithoutTwitchIds = persistenceService.getAllGamesWithoutTwitchIds(100);
        OAuthTokenDTO localToken = oAuthService.getRandomToken();
        while(allGamesWithoutTwitchIds.size() > 0){
            Map map = runGetGame(allGamesWithoutTwitchIds, localToken);
            persistenceService.updateGameWithTwitchData(map);
            allGamesWithoutTwitchIds = persistenceService.getAllGamesWithoutTwitchIds(100);
            gamesNameList = new HashSet<>();
        }
    }

    public void importTwitchUsers() {
        OAuthTokenDTO randomToken = oAuthService.getRandomToken();
        Set<String> usersWithoutTwitchId = persistenceService.getUsersWithoutTwitchId(100);
        while (usersWithoutTwitchId.size() > 0) {
            Map map = runGetUsers(persistenceService.getUsersWithoutTwitchId(100), randomToken);
            persistenceService.updateUserWithTwitchData(map);
            usersWithoutTwitchId = persistenceService.getUsersWithoutTwitchId(100);
        }
    }

    public void importChannelStreams() {
        Set<String> allSullyChannels = persistenceService.getAllSullyChannels();
        try {
            for (String id : allSullyChannels) {
                String suffix = "/" + numberOfRecords;
                String channelStreamScaffoldUrl = "https://sullygnome.com/api/tables/channeltables/streams/" + gamesDaysPerspective + "/" + id + "/%20/1/1/desc/10";
                String channelStreamPrefix = "https://sullygnome.com/api/tables/channeltables/streams/" + gamesDaysPerspective + "/" + id + "/%20/1/1/desc";

                long streamsTotalSize;
                ChannelStreamList channelStreamList = objectMapper().readValue(goToWebSiteJSON(channelStreamScaffoldUrl, tertiaryDriver), ChannelStreamList.class);
                streamsTotalSize = channelStreamList.getRecordsTotal();
                log.debug("Actual channel stream size: " + streamsTotalSize);
                if (testing) {
                    streamsTotalSize = 10; //todo remove when read for prod
                }
                List<String> streamsUrls = buildUpSubSequentUrls(channelStreamPrefix, suffix, streamsTotalSize);
                for (String json : goToWebSitesJSON(streamsUrls, tertiaryDriver)) {
                    persistenceService.persistSullyChannelStreams(objectMapper().readValue(json, Map.class));
                }

            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    //MAIN METHODS

    /**
     * Covers both the importing of sully Games and updating those games with twitch
     */
    public void importGames() {
        String suffix = "/" + numberOfRecords;
        String gameScaffoldUrl = "https://sullygnome.com/api/tables/gametables/getgames/" + gamesDaysPerspective + "/%20/0/1/3/desc/0/" + numberOfRecords;
        String gamePrefix = "https://sullygnome.com/api/tables/gametables/getgames/" + gamesDaysPerspective + "/%20/0/1/3/desc/";
        long gamesTotalSize;
        try {
            GamesTable gamesTable = objectMapper().readValue(goToWebSiteJSON(gameScaffoldUrl, tertiaryDriver), GamesTable.class);
            gamesTotalSize = gamesTable.getRecordsTotal();
            log.debug("Actual Game size: " + gamesTotalSize);
            if (testing) {
                gamesTotalSize = 1001; //todo remove when read for prod
            }
            List<String> gamesUrls = buildUpSubSequentUrls(gamePrefix, suffix, gamesTotalSize);
            for (String json : goToWebSitesJSON(gamesUrls, tertiaryDriver)) {
                persistenceService.persistSullyGames(gamesDaysPerspective, objectMapper().readValue(json, Map.class));
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

    }

    public void importTwitchTeams() {
        OAuthTokenDTO localToken = oAuthService.getRandomToken();
        Set<String> teamsWithoutTwitchId = persistenceService.getTeamsWithoutTwitchId();
        for (String teamName : teamsWithoutTwitchId) {
            Map map = runGetTeam(teamName, localToken);
            persistenceService.updateTeamWithTwitchData(teamName, map);
        }
    }

    public void importChannels() {
        String suffix = "/" + numberOfRecords;
        String channelsScaffoldUrl = "https://sullygnome.com/api/tables/channeltables/getchannels/" + channelDaysPerspective + "/0/11/3/desc/0/" + numberOfRecords;
        String channelPrefix = "https://sullygnome.com/api/tables/channeltables/getchannels/" + channelDaysPerspective + "/0/11/3/desc/";
        long channelTotalSize;
        try {
            ChannelsTable channelsTable = objectMapper().readValue(goToWebSiteJSON(channelsScaffoldUrl, primaryDriver), ChannelsTable.class);
            channelTotalSize = channelsTable.getRecordsTotal();
            log.debug("Actual Channel size: " + channelTotalSize);
            if (testing) {
                channelTotalSize = 1001;
            }
            List<String> channelUrls = buildUpSubSequentUrls(channelPrefix, suffix, channelTotalSize);
            OAuthTokenDTO localToken = oAuthService.getRandomToken();
            for (String json : goToWebSitesJSON(channelUrls, primaryDriver)) {
                persistenceService.persistSullyChannels(channelDaysPerspective, objectMapper().readValue(json, Map.class));
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
            TeamsTable teamsTable = objectMapper().readValue(goToWebSiteJSON(teamsScaffoldUrl, secondaryDriver), TeamsTable.class);
            teamTotalSize = teamsTable.getRecordsTotal();
            log.debug("Actual Teams size: " + teamTotalSize);
            if (testing) {
                teamTotalSize = 101;
            }
            List<String> teamsUrls = buildUpSubSequentUrls(teamsprefix, suffix, teamTotalSize);
            for (String json : goToWebSitesJSON(teamsUrls, secondaryDriver)) {
                persistenceService.persistSullyTeams(teamsDaysPerspective, objectMapper().readValue(json, Map.class));
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    public void importFollowsTo() {
        Set<String> usersWithoutTwitchFollowsTo;
        if (testing) {
            usersWithoutTwitchFollowsTo = persistenceService.getUsersWithoutTwitchFollowsTo(followsLimit);
        } else {
            usersWithoutTwitchFollowsTo = persistenceService.getUsersWithoutTwitchFollowsTo(null);
        }

        OAuthTokenDTO randomToken = oAuthService.getRandomToken();
        for (String twitchId : usersWithoutTwitchFollowsTo) {
            try {
                FollowsDTO resultList = runGetFollowersTo(twitchId, randomToken, null);
                persistenceService.persistTwitchFollowersTo(resultList.getMap());
                if (resultList != null) {
                    String cursor = resultList.getPagination().getCursor();
                    while (cursor != null) {
                        FollowsDTO loopList = runGetFollowersTo(twitchId, randomToken, cursor);
                        if (loopList != null && loopList.getData() != null) {
                            String newCursor = loopList.getPagination().getCursor();
                            persistenceService.persistTwitchFollowersTo(resultList.getMap());
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

        log.debug("Total size of all the Import followers from are: " + liveStreamers.size());
    }

    public void importFollowsFrom() {
        Set<String> usersWithoutTwitchFollowsFrom;
        if (testing) {
            usersWithoutTwitchFollowsFrom = persistenceService.getUsersWithoutTwitchFollowsFrom(followsLimit);
        } else {
            usersWithoutTwitchFollowsFrom = persistenceService.getUsersWithoutTwitchFollowsFrom(null);
        }

        OAuthTokenDTO randomToken = oAuthService.getRandomToken();
        for (String twitchId : usersWithoutTwitchFollowsFrom) {
            try {
                FollowsDTO resultList = runGetFollowersFrom(twitchId, randomToken, null);
                persistenceService.persistTwitchFollowersFrom(resultList.getMap());
                if (resultList != null) {
                    String cursor = resultList.getPagination().getCursor();
                    while (cursor != null) {
                        FollowsDTO loopList = runGetFollowersFrom(twitchId, randomToken, cursor);
                        if (loopList != null && loopList.getData() != null) {
                            String newCursor = loopList.getPagination().getCursor();
                            persistenceService.persistTwitchFollowersFrom(resultList.getMap());
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

        log.debug("Total size of all the Import followers from are: " + liveStreamers.size());
    }

    public void importLanguages() {
        try {
            URL resource = getClass().getClassLoader().getResource("language-english.json");
            File tokensFile = new File(resource.toURI());
            LanguageKeyDTO languageKeyDTO = objectMapper().readValue(tokensFile, LanguageKeyDTO.class);
            persistenceService.persistTwitchLanguage("en", languageKeyDTO.getEn());
            persistenceService.persistTwitchLanguage("zh", languageKeyDTO.getZh());
            persistenceService.persistTwitchLanguage("ja", languageKeyDTO.getJa());
            persistenceService.persistTwitchLanguage("ko", languageKeyDTO.getKo());
            persistenceService.persistTwitchLanguage("es", languageKeyDTO.getEs());
            persistenceService.persistTwitchLanguage("fr", languageKeyDTO.getFr());
            persistenceService.persistTwitchLanguage("de", languageKeyDTO.getDe());
            persistenceService.persistTwitchLanguage("it", languageKeyDTO.getIt());
            persistenceService.persistTwitchLanguage("pt", languageKeyDTO.getPt());
            persistenceService.persistTwitchLanguage("sv", languageKeyDTO.getSv());
            persistenceService.persistTwitchLanguage("no", languageKeyDTO.getNo());
            persistenceService.persistTwitchLanguage("nl", languageKeyDTO.getNl());
            persistenceService.persistTwitchLanguage("fi", languageKeyDTO.getFi());
            persistenceService.persistTwitchLanguage("el", languageKeyDTO.getEl());
            persistenceService.persistTwitchLanguage("ru", languageKeyDTO.getRu());
            persistenceService.persistTwitchLanguage("tr", languageKeyDTO.getTr());
            persistenceService.persistTwitchLanguage("cs", languageKeyDTO.getCs());
            persistenceService.persistTwitchLanguage("hu", languageKeyDTO.getHu());
            persistenceService.persistTwitchLanguage("ar", languageKeyDTO.getAr());
            persistenceService.persistTwitchLanguage("bg", languageKeyDTO.getBg());
            persistenceService.persistTwitchLanguage("th", languageKeyDTO.getTh());
            persistenceService.persistTwitchLanguage("vi", languageKeyDTO.getVi());
            persistenceService.persistTwitchLanguage("asl", languageKeyDTO.getAsl());
            persistenceService.persistTwitchLanguage("other", languageKeyDTO.getOther());
            persistenceService.persistTwitchLanguage("uk", languageKeyDTO.getUk());
            persistenceService.persistTwitchLanguage("pl", languageKeyDTO.getPl());
            persistenceService.persistTwitchLanguage("hi", languageKeyDTO.getHi());
            persistenceService.persistTwitchLanguage("ca", languageKeyDTO.getCa());
            persistenceService.persistTwitchLanguage("zh-HK", languageKeyDTO.getZhHK());
            persistenceService.persistTwitchLanguage("zh-TW", languageKeyDTO.getZhTW());
            persistenceService.persistTwitchLanguage("da", languageKeyDTO.getDa());
            persistenceService.persistTwitchLanguage("id", languageKeyDTO.getId());
            persistenceService.persistTwitchLanguage("ms", languageKeyDTO.getMs());
            persistenceService.persistTwitchLanguage("pt-BR", languageKeyDTO.getPtBR());
            persistenceService.persistTwitchLanguage("ro", languageKeyDTO.getRo());
            persistenceService.persistTwitchLanguage("sk", languageKeyDTO.getSk());
            persistenceService.persistTwitchLanguage("es-MX", languageKeyDTO.getEsMX());
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void importChatters() {
        for (String streamLogin : liveStreamers) {
            Map map = runGetChatters(streamLogin);
            if (map != null) {
                persistenceService.persistTwitchChatters(map);
            }
        }
    }

    public void importLiveStreams(Integer limit) {
        liveStreamers = new HashSet<>();
        OAuthTokenDTO randomToken = oAuthService.getRandomToken();
        try {
            StreamListDTO resultList = runGetLiveStreams(randomToken, null);
            persistenceService.persistTwitchStreams(resultList.getMap());
            if (resultList.getStreams() != null) {
                for (StreamDTO stream : resultList.getStreams()) {
                    Map map = runGetChatters(stream.getUserLogin());
                    if (map != null) {
                        persistenceService.persistTwitchChatters(map);
                        liveStreamers.add(stream.getUserLogin());
                    }
                }
                String cursor = resultList.getPagination().getCursor();
                while (cursor != null && liveStreamers.size() < limit) {
                    StreamListDTO loopList = runGetLiveStreams(randomToken, cursor);
                    if (loopList != null && loopList.getStreams() != null) {
                        String newCursor = loopList.getPagination().getCursor();
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

    //REST METHODS

    public StreamListDTO runGetLiveStreams(OAuthTokenDTO oAuthTokenDTO, String cursor) {
        String url = "https://api.twitch.tv/helix/streams?limit=100";
        if (cursor != null) {
            url = url + "&after=" + cursor;
        }
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    getGenericHttpRequest(oAuthTokenDTO),
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                StreamListDTO streamListDTO = objectMapper().readValue(response.getBody(), StreamListDTO.class);
                streamListDTO.setMap(objectMapper().readValue(response.getBody(), Map.class));
                return streamListDTO;
            }
        } catch (HttpClientErrorException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
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

    public Map runGetGame(Set<String> nonEncodedGameDisplayNames, OAuthTokenDTO oAuthTokenDTO) {
        StringBuilder url = new StringBuilder("https://api.twitch.tv/helix/games?name=");
        boolean firstValue = true;
        for (String name : nonEncodedGameDisplayNames) {
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

    public Map runGetUsers(Set<String> loginNames, OAuthTokenDTO oAuthTokenDTO) {
        StringBuilder url = new StringBuilder("https://api.twitch.tv/helix/users?login=");
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
                log.debug("SUCCESSFUL REQUEST FOR TWITCH USERS : " + loginNames.toString());
                return objectMapper().readValue(response.getBody(), Map.class);
            }
        } catch (HttpClientErrorException | JsonProcessingException e) {
            e.printStackTrace();
        }
        log.debug("");
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

    public FollowsDTO runGetFollowersTo(String twitchId, OAuthTokenDTO oAuthTokenDTO, String cursor) {
        String url = "https://api.twitch.tv/helix/users/follows?first=100&to_id=" + twitchId;
        if (cursor != null) {
            url = url + "&after=" + cursor;
        }
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    getGenericHttpRequest(oAuthTokenDTO),
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                FollowsDTO followsDTO = objectMapper().readValue(response.getBody(), FollowsDTO.class);
                followsDTO.setMap(objectMapper().readValue(response.getBody(), Map.class));
                return followsDTO;
            }
        } catch (HttpClientErrorException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FollowsDTO runGetFollowersFrom(String twitchId, OAuthTokenDTO oAuthTokenDTO, String cursor) {
        String url = "https://api.twitch.tv/helix/users/follows?first=100&from_id=" + twitchId;
        if (cursor != null) {
            url = url + "&after=" + cursor;
        }
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    getGenericHttpRequest(oAuthTokenDTO),
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                FollowsDTO followsDTO = objectMapper().readValue(response.getBody(), FollowsDTO.class);
                followsDTO.setMap(objectMapper().readValue(response.getBody(), Map.class));
                return followsDTO;
            }
        } catch (HttpClientErrorException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}





