package com.twitchsnitch.importer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.sully.ChannelSearchDTO;
import com.twitchsnitch.importer.dto.sully.RaidFinderDTO;
import com.twitchsnitch.importer.dto.sully.SearchDTO;
import com.twitchsnitch.importer.dto.sully.channels.*;
import com.twitchsnitch.importer.dto.sully.games.GamesTable;
import com.twitchsnitch.importer.dto.twitch.*;
import com.twitchsnitch.importer.utils.SplittingUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
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
    @Value("${games.days.perspective}")
    private int gamesDaysPerspective;
    @Value("${teams.days.perspective}")
    private int teamsDaysPerspective;
    @Value("${channels.days.perspective}")
    private int channelDaysPerspective;
    @Value("${database}")
    private String database;
    @Value("${split.driver.workload}")

    private boolean splitDriverWorkload;
    @Value("${webdrivers.size.max}")
    private Integer webDriversSize;
    private final OAuthService oAuthService;
    private final PersistenceService persistenceService;
    private final DriverService driverService;
    private final AsyncPersistenceService asyncPersistenceService;

    private RestTemplate restTemplate = new RestTemplate();
    private Set<String> liveStreamers = new HashSet<>();

    public final ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    public TwitchDataService(OAuthService oAuthService, PersistenceService persistenceService, DriverService driverService, AsyncPersistenceService asyncPersistenceService) {
        this.oAuthService = oAuthService;
        this.persistenceService = persistenceService;
        this.driverService = driverService;
        this.asyncPersistenceService = asyncPersistenceService;
    }

    private final static Logger log = LoggerFactory.getLogger(TwitchDataService.class);

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


    public List<String> goToWebSitesJSON(Set<String> urls) {
        List<String> jsonList = new ArrayList<>();
        ChromeDriver driver = driverService.getRandomDriver();
        for (String url : urls) {
            try {
                driver.get("view-source:" + url);
                jsonList.add(driver.findElement(By.className("line-content")).getText());
                log.debug("Pages collected so far is: " + jsonList.size());
            } catch (Exception e) {
                try {
                    driver.get("view-source:" + url);
                    //Thread.sleep(2000);
                    jsonList.add(driver.findElement(By.className("line-content")).getText());

                } catch (Exception e2) {
                    log.error("FAILED COLLECTING: " + url);
                }

            }
        }
        return jsonList;
    }

    public String goToWebSiteHTML(String url) {
        ChromeDriver driver = driverService.getRandomDriver();
        try {
            driver.get(url);
            return driver.getPageSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> goToWebSitesHTML(List<String> urls) {
        ChromeDriver driver = driverService.getRandomDriver();
        List<String> htmlList = new ArrayList<>();
        for (String url : urls) {
            try {
                driver.get(url);
                htmlList.add(driver.getPageSource());
                log.debug("Pages collected so far is: " + htmlList.size());
            } catch (Exception e) {
                try {
                    driver.get(url);
                    htmlList.add(driver.getPageSource());

                } catch (Exception e2) {
                    log.error("FAILED COLLECTING: " + url);
                }

            }
        }
        return htmlList;
    }


    public String goToWebSiteREST(String urlString){
        String response = null;
        URL url = null;
        try {
            url = new URL(urlString);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");

            httpConn.setRequestProperty("authority", "sullygnome.com");
            httpConn.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            httpConn.setRequestProperty("accept-language", "en-US,en;q=0.9");
            httpConn.setRequestProperty("cookie", "_ga=GA1.2.911863633.1652187859; _gid=GA1.2.1880556020.1652356979; _gat=1");
            httpConn.setRequestProperty("referer", "https://sullygnome.com/games/watched");
            httpConn.setRequestProperty("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"101\", \"Google Chrome\";v=\"101\"");
            httpConn.setRequestProperty("sec-ch-ua-mobile", "?0");
            httpConn.setRequestProperty("sec-ch-ua-platform", "\"Linux\"");
            httpConn.setRequestProperty("sec-fetch-dest", "empty");
            httpConn.setRequestProperty("sec-fetch-mode", "cors");
            httpConn.setRequestProperty("sec-fetch-site", "same-origin");
            httpConn.setRequestProperty("timecode", "128052_5/12/2022 12:03:26 PM_7e376882-daf0-4b1e-8d50-e898e2111777_4a484544b669b1d1bdf40b5ecb31d9e6");
            httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.54 Safari/537.36");
            httpConn.setRequestProperty("x-requested-with", "XMLHttpRequest");

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            response = s.hasNext() ? s.next() : "";
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }


    private Set<String> buildUpSubSequentUrls(String prefix, String suffix, long resultSize) {
        HashSet<String> urls = new HashSet<>();
        long pages = resultSize / 100;
        log.debug("Original result size is: " + resultSize);
        for (int i = 0; i <= pages; i++) {
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

    public void getTwitchIdNotSetCountGame() {
        persistenceService.getTwitchIdNotSetCountGame();
    }

    public Long getTwitchIdNotSetCountUser() {
        return persistenceService.getTwitchIdNotSetCountUser();
    }

    //MAIN METHODS

    //its a gimic for marketing
    public void newPartnersImport() {
        String url = "https://sullygnome.com/api/tables/channeltables/newpartners/30/0/000/1/7/desc/0/100";
    }

    @Async
    public void sullySearchAndSetAllUsersWithoutSullyId() {
        List<String> allUsersWithoutSullyId = persistenceService.getAllUsersWithoutSullyId();
        for (String login : allUsersWithoutSullyId) {
            try {
                String url = "https://sullygnome.com/api/standardsearch/" + login + "/true/true/false/true";
                String json = goToWebSiteREST(url);
                List<SearchDTO> searchDTOList = objectMapper().readValue(json, new TypeReference<List<SearchDTO>>() {});
                for(SearchDTO searchDTO: searchDTOList){
                    if(searchDTO.getItemtype() == 1 && searchDTO.getSiteurl().equalsIgnoreCase(login)){
                        //match
                        //TODO some importing of an individual record
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Async
    public void sullySearchAndSetAllGamesWithoutSullyId() {
        List<String> allGamesWithoutSullyId = persistenceService.getAllGamesWithoutSullyId();
        for (String name : allGamesWithoutSullyId) {
            try {
                String url = "https://sullygnome.com/api/standardsearch/" + name + "/true/true/false/true";
                String json = goToWebSiteREST(url);
                List<SearchDTO> searchDTOList  = objectMapper().readValue(json, new TypeReference<List<SearchDTO>>() {});
                for(SearchDTO searchDTO: searchDTOList){
                    if(searchDTO.getItemtype() == 2 && searchDTO.getDisplaytext().equalsIgnoreCase(name)){
                        //match
                        //TODO some importing of an individual record
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public void sullySearchAndSetAllTeamsWithoutSullyId() {
        List<String> allTeamsWithoutSullyId = persistenceService.getAllTeamsWithoutSullyId();
        for (String login : allTeamsWithoutSullyId) {
            try {
                String url = "https://sullygnome.com/api/standardsearch/" + login + "/true/true/false/true";
                String json = goToWebSiteREST(url);
                List<SearchDTO> searchDTOList  = objectMapper().readValue(json, new TypeReference<List<SearchDTO>>() {});
                for(SearchDTO searchDTO: searchDTOList){
                    if(searchDTO.getItemtype() == 4 && searchDTO.getSiteurl().equalsIgnoreCase(login)){
                        //match
                        //TODO some importing of an individual record
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Async
    public void sullyDeepSearchPhase1() {
        List<Long> allSullyLanguageIds = persistenceService.getAllSullyLanguageIds();
        int lowerBound = 0;
        int upperBound = 250;
        int modus = 0;
        String suffix = "/" + numberOfRecords;
        for (int i = lowerBound; i <= upperBound; i++) {
            for (Long languageId : allSullyLanguageIds) {
                log.debug("Deep search for channels, index: " + i + " language key: " + languageId);
                String scaffoldUrl = "https://sullygnome.com/api/tables/channeltables/advancedsearch/30/0/" + languageId + "/" + i + "/" + i + "/-1/-1/%20/1/false/false/true/true/true/true/true/false/2022-04-16T22:00:00.000Z/-1/1/0/desc/0/100";
                String prefix = "https://sullygnome.com/api/tables/channeltables/advancedsearch/30/0/" + languageId + "/" + i + "/" + i + "/-1/-1/%20/1/false/false/true/true/true/true/true/false/2022-04-16T22:00:00.000Z/-1/1/0/desc/";
                try {
                    String json = goToWebSiteREST(scaffoldUrl);
                    if (json != null) {
                        ChannelSearchDTO channelSearchDTO = objectMapper().readValue(json, ChannelSearchDTO.class);
                        if(channelSearchDTO.getRecordsTotal() > 0){
                            Set<String> channelSearchUrls = buildUpSubSequentUrls(prefix, suffix, channelSearchDTO.getRecordsTotal());
                            if (channelSearchUrls.size() > 0) {
                                asyncPersistenceService.persistChannelsAsync(channelSearchUrls);
                            }
                        }
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void sullyDeepSearchPhase2() {
        int lowerBound = 250;
        int upperBound = 2500;
        int modus = 0;
        genericSullyDeepSearch(lowerBound, upperBound, modus);
    }

    @Async
    public void sullyDeepSearchPhase3() {
        int lowerBound = 2500;
        int upperBound = 10000;
        int modus = 10;
        genericSullyDeepSearch(lowerBound, upperBound, modus);
    }

    @Async
    public void sullyDeepSearchPhase4() {
        int lowerBound = 10000;
        int upperBound = 100000;
        int modus = 100;
        genericSullyDeepSearch(lowerBound, upperBound, modus);

    }

    @Async
    public void sullyDeepSearchPhase5() {
        int lowerBound = 100000;
        int upperBound = 10000000;
        int modus = 10000;
        genericSullyDeepSearch(lowerBound, upperBound, modus);
    }

    @Async
    public void genericSullyDeepSearch(Integer lowerBound, Integer upperBound, Integer modus) {
        String suffix = "/" + numberOfRecords;
        //fifth phase
        for (int i = lowerBound; i <= upperBound; i++) {
            log.debug("Deep search for channels, index: " + i);
            if (i % modus == 0) {
                String scaffoldUrl = "https://sullygnome.com/api/tables/channeltables/advancedsearch/30/0/-1/" + i + "/" + i + "/-1/-1/%20/1/false/false/true/true/true/true/true/false/2022-04-16T22:00:00.000Z/-1/1/0/desc/0/100";
                String prefix = "https://sullygnome.com/api/tables/channeltables/advancedsearch/30/0/-1/" + i + "/" + i + "/-1/-1/%20/1/false/false/true/true/true/true/true/false/2022-04-16T22:00:00.000Z/-1/1/0/desc/";
                try {
                    String json = goToWebSiteREST(scaffoldUrl);
                    if (json != null) {
                        ChannelSearchDTO channelSearchDTO = objectMapper().readValue(json, ChannelSearchDTO.class);
                        if(channelSearchDTO.getRecordsTotal() > 0){
                            Set<String> channelSearchUrls = buildUpSubSequentUrls(prefix, suffix, channelSearchDTO.getRecordsTotal());
                            if (channelSearchUrls.size() > 0) {
                                asyncPersistenceService.persistChannelsAsync( channelSearchUrls);
                            }
                        }
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void sullyDeepSearchAll() {
        sullyDeepSearchPhase1();
        sullyDeepSearchPhase2();
        sullyDeepSearchPhase3();
        sullyDeepSearchPhase4();
        sullyDeepSearchPhase5();
    }

    public void importAllUsersWithoutSullyData() {
        List<String> allUsersWithoutSullyId = persistenceService.getAllUsersWithoutSullyId();
        for (String login : allUsersWithoutSullyId) {
            try {
                String url = "https://sullygnome.com/channel/" + login;
                String html = goToWebSiteHTML(url);
                //TODO stuff here
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void importChattersOnDB() {
        persistenceService.persistChattersOnDB();
    }

    @Async
    public void importGames1() {
        String suffix = "/" + numberOfRecords;
        String gameScaffoldUrl = "https://sullygnome.com/api/tables/gametables/getgames/" + gamesDaysPerspective + "/%20/0/1/3/desc/0/" + numberOfRecords;
        String gamePrefix = "https://sullygnome.com/api/tables/gametables/getgames/" + gamesDaysPerspective + "/%20/0/1/3/desc/";
        long gamesTotalSize;
        try {
            GamesTable gamesTable = objectMapper().readValue(goToWebSiteREST(gameScaffoldUrl), GamesTable.class);
            gamesTotalSize = gamesTable.getRecordsTotal();
            log.debug("Actual Game size: " + gamesTable.getRecordsTotal());
            Set<String> gamesUrls = buildUpSubSequentUrls(gamePrefix, suffix, gamesTotalSize);
            List<Set<String>> sets = SplittingUtils.splitIntoMultipleSets(gamesUrls, 10);
            for (int i = 0; i < sets.size(); i++) {
                asyncPersistenceService.persistGamesAsync(i, sets.get(i));


            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @Async
    public void importGames2() {
        String suffix = "/" + numberOfRecords;
        String gameScaffoldUrl = "https://sullygnome.com/api/tables/gametables/getgames/" + gamesDaysPerspective + "/%20/0/2/3/asc/0/" + numberOfRecords;
        String gamePrefix = "https://sullygnome.com/api/tables/gametables/getgames/" + gamesDaysPerspective + "/%20/0/2/3/asc/";
        long gamesTotalSize;
        try {
            GamesTable gamesTable = objectMapper().readValue(goToWebSiteREST(gameScaffoldUrl), GamesTable.class);
            gamesTotalSize = gamesTable.getRecordsTotal();
            log.debug("Actual Game size: " + gamesTable.getRecordsTotal());
            Set<String> gamesUrls = buildUpSubSequentUrls(gamePrefix, suffix, gamesTotalSize);
            List<Set<String>> sets = SplittingUtils.splitIntoMultipleSets(gamesUrls, 10);
            for (int i = 0; i < sets.size(); i++) {
                asyncPersistenceService.persistGamesAsync(i, sets.get(i));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


    public void importTwitchTeams() {
        OAuthTokenDTO localToken = oAuthService.getRandomToken();
        Set<String> teamsWithoutTwitchId = persistenceService.getTeamsWithoutTwitchId();
        for (String teamName : teamsWithoutTwitchId) {
            if (teamName != null) {
                Map map = runGetTeam(teamName, localToken);
                persistenceService.updateTeamWithTwitchData(teamName, map);
            }
        }
    }


    public void importChannels() {
        String suffix = "/" + numberOfRecords;
        String channelMostViewedPrefix = "https://sullygnome.com/api/tables/channeltables/getchannels/" + channelDaysPerspective + "/0/11/3/desc/";
        String channelPeakViewersPrefix = "https://sullygnome.com/api/tables/channeltables/getchannels/" + channelDaysPerspective + "/0/11/5/desc/";
        String channelMostFollowsPrefix = "https://sullygnome.com/api/tables/channeltables/getchannels/" + channelDaysPerspective + "/0/11/7/desc/";
        String channelFollowerGrowthPrefix = "https://sullygnome.com/api/tables/channeltables/getchannels/" + channelDaysPerspective + "/0/11/8/desc/";

        long channelTotalSize;
        try {
            channelTotalSize = 5000;

            Set<String> channelMostViewedUrls = buildUpSubSequentUrls(channelMostViewedPrefix, suffix, channelTotalSize);
            Set<String> channelPeakViewersUrls = buildUpSubSequentUrls(channelPeakViewersPrefix, suffix, channelTotalSize);
            Set<String> channelMostFollowsUrls = buildUpSubSequentUrls(channelMostFollowsPrefix, suffix, channelTotalSize);
            Set<String> channelFollowerGrowthUrls = buildUpSubSequentUrls(channelFollowerGrowthPrefix, suffix, channelTotalSize);

            Set<String> allSets = new HashSet<>();
            allSets.addAll(channelMostViewedUrls);
            allSets.addAll(channelPeakViewersUrls);
            allSets.addAll(channelMostFollowsUrls);
            allSets.addAll(channelFollowerGrowthUrls);

            List<Set<String>> sets = SplittingUtils.splitIntoMultipleSets(allSets, 10);
            for (int i = 0; i < sets.size(); i++) {
                asyncPersistenceService.persistChannelsAsync(sets.get(i));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Async
    public void importTeams1() {
        String suffix = "/" + numberOfRecords;
        //teams result list
        String teamsScaffoldUrl = "https://sullygnome.com/api/tables/teamtables/getteams/" + teamsDaysPerspective + "/0/1/3/desc/0/" + numberOfRecords;
        String teamsprefix = "https://sullygnome.com/api/tables/teamtables/getteams/" + teamsDaysPerspective + "/0/1/3/desc/";
        long teamTotalSize;
        try {
            //TeamsTable teamsTable = objectMapper().readValue(goToWebSiteREST(teamsScaffoldUrl), TeamsTable.class);
            teamTotalSize = 5000;
            log.debug("Actual Teams size: " + teamTotalSize);
            Set<String> teamsUrls = buildUpSubSequentUrls(teamsprefix, suffix, teamTotalSize);
            List<Set<String>> sets = SplittingUtils.splitIntoMultipleSets(teamsUrls, 10);
            for (int i = 0; i < sets.size(); i++) {
                asyncPersistenceService.persistTeamsAsync(i, sets.get(i));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Async
    public void importTeams2() {
        String suffix = "/" + numberOfRecords;
        //teams result list
        String teamsScaffoldUrl = "https://sullygnome.com/api/tables/teamtables/getteams/" + teamsDaysPerspective + "/0/4/5/asc/0/" + numberOfRecords;
        String teamsprefix = "https://sullygnome.com/api/tables/teamtables/getteams/" + teamsDaysPerspective + "/0/4/5/asc/0/";
        long teamTotalSize;
        try {
            //TeamsTable teamsTable = objectMapper().readValue(goToWebSiteREST(teamsScaffoldUrl), TeamsTable.class);
            teamTotalSize = 5000;
            log.debug("Actual Teams size: " + teamTotalSize);
            Set<String> teamsUrls = buildUpSubSequentUrls(teamsprefix, suffix, teamTotalSize);
            List<Set<String>> sets = SplittingUtils.splitIntoMultipleSets(teamsUrls, 10);
            for (int i = 0; i < sets.size(); i++) {
                asyncPersistenceService.persistTeamsAsync(i, sets.get(i));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Async
    public void importFollowsTo() {
        Set<String> usersWithoutTwitchFollowsTo;
        usersWithoutTwitchFollowsTo = persistenceService.getUsersWithoutTwitchFollowsTo();
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
    }


    public void importFollowsFrom() {
        Set<String> usersWithoutTwitchFollowsFrom;

        usersWithoutTwitchFollowsFrom = persistenceService.getUsersWithoutTwitchFollowsFrom();


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
    }

    /**
     *
     */

    @Async
    public void importSullyLanguages() {
        try {
            URL resource = getClass().getClassLoader().getResource("language-english.json");
            File tokensFile = new File(resource.toURI());
            LanguageKeyDTO languageKeyDTO = objectMapper().readValue(tokensFile, LanguageKeyDTO.class);
            persistenceService.persistSullyLanguage("any", "Any Language", -1);
            persistenceService.persistSullyLanguage("en", languageKeyDTO.getEn(), 217);
            persistenceService.persistSullyLanguage("zh", languageKeyDTO.getZh(), 241);
            persistenceService.persistSullyLanguage("ja", languageKeyDTO.getJa(), 226);
            persistenceService.persistSullyLanguage("ko", languageKeyDTO.getKo(), 231);
            persistenceService.persistSullyLanguage("es", languageKeyDTO.getEs(), 228);
            persistenceService.persistSullyLanguage("fr", languageKeyDTO.getFr(), 214);
            persistenceService.persistSullyLanguage("de", languageKeyDTO.getDe(), 220);
            persistenceService.persistSullyLanguage("it", languageKeyDTO.getIt(), 230);
            persistenceService.persistSullyLanguage("pt", languageKeyDTO.getPt(), 232);
            persistenceService.persistSullyLanguage("sv", languageKeyDTO.getSv(), 238);
            persistenceService.persistSullyLanguage("no", languageKeyDTO.getNo(), 234);
            persistenceService.persistSullyLanguage("nl", languageKeyDTO.getNl(), 235);
            persistenceService.persistSullyLanguage("fi", languageKeyDTO.getFi(), 229);
            persistenceService.persistSullyLanguage("el", languageKeyDTO.getEl(), 245);
            persistenceService.persistSullyLanguage("ru", languageKeyDTO.getRu(), 227);
            persistenceService.persistSullyLanguage("tr", languageKeyDTO.getTr(), 236);
            persistenceService.persistSullyLanguage("cs", languageKeyDTO.getCs(), 216);
            persistenceService.persistSullyLanguage("hu", languageKeyDTO.getHu(), 218);
            persistenceService.persistSullyLanguage("ar", languageKeyDTO.getAr(), 213);
            persistenceService.persistSullyLanguage("bg", languageKeyDTO.getBg(), 215);
            persistenceService.persistSullyLanguage("th", languageKeyDTO.getTh(), 224);
            persistenceService.persistSullyLanguage("vi", languageKeyDTO.getVi(), 243);
            persistenceService.persistSullyLanguage("asl", languageKeyDTO.getAsl(), 244);
            persistenceService.persistSullyLanguage("other", languageKeyDTO.getOther(), 222);
            persistenceService.persistSullyLanguage("uk", languageKeyDTO.getUk(), 249);
            persistenceService.persistSullyLanguage("pl", languageKeyDTO.getPl(), 242);
            persistenceService.persistSullyLanguage("hi", languageKeyDTO.getHi(), 247);
            persistenceService.persistSullyLanguage("ca", languageKeyDTO.getCa(), 250);
            persistenceService.persistSullyLanguage("zh-HK", languageKeyDTO.getZhHK(), 225);
            persistenceService.persistSullyLanguage("zh-TW", languageKeyDTO.getZhTW(), 223);
            persistenceService.persistSullyLanguage("da", languageKeyDTO.getDa(), 240);
            persistenceService.persistSullyLanguage("id", languageKeyDTO.getId(), 248);
            persistenceService.persistSullyLanguage("ms", languageKeyDTO.getMs(), 252);
            persistenceService.persistSullyLanguage("pt-BR", languageKeyDTO.getPtBR(), 233);
            persistenceService.persistSullyLanguage("ro", languageKeyDTO.getRo(), 246);
            persistenceService.persistSullyLanguage("sk", languageKeyDTO.getSk(), 237);
            persistenceService.persistSullyLanguage("es-MX", languageKeyDTO.getEsMX(), 221);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //todo 4
    @Async
    public void importRaidPicker() {
        try {
            for (String login : persistenceService.getChannelsCurrentlyLiveStreaming()) {
                RaidFinderDTO raidDTO = persistenceService.getRaidFinder(login);
                boolean secondValue = false;
                String gameString = "";
                if (raidDTO.isDataIsSet()) {
                    for (String gameId : raidDTO.getGameIds()) {
                        if (secondValue) {
                            gameString = gameString + "," + gameId;
                        } else {
                            gameString = gameString + gameId;
                            secondValue = true;
                        }
                    }
                    String url = "https://sullygnome.com/api/tables/channeltables/raidfinder/30/2215977/%20" + gameString + "/0/0/9999999/" + raidDTO.getLowRange() + "/" + raidDTO.getHighRange() + "/011/11/false/1/4/desc/0/100";
                    String json = goToWebSiteREST(url);
                    ChannelRaidFinder channelRaidFinder = objectMapper().readValue(json, ChannelRaidFinder.class);
                    if (channelRaidFinder.getRecordsTotal() > 0) {
                        persistenceService.persistSullyChannelRaidFinder(login, objectMapper().readValue(json, Map.class));
                    }
                }

            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @Async
    //todo 3
    public void importGameFinder() {
        try {
            String suffix = "/" + numberOfRecords;
            String gamePickerScaffoldUrl = "https://sullygnome.com/api/tables/gametables/getgamepickergames/-1/-1/90/false/-1/-1/-1/-1/-1/-1/-1/-1/-1/-1/2/4/desc/0/100";
            String gamePickerPrefix = "https://sullygnome.com/api/tables/gametables/getgamepickergames/-1/-1/90/false/-1/-1/-1/-1/-1/-1/-1/-1/-1/-1/2/4/desc/";
            long gamePickerTotalSize;

            String jsonScaffold = goToWebSiteREST(gamePickerScaffoldUrl);
            if (jsonScaffold != null) {
                ChannelGamePicker channelGamePicker = objectMapper().readValue(jsonScaffold, ChannelGamePicker.class);
                gamePickerTotalSize = channelGamePicker.getRecordsTotal();
                Set<String> gamePickerUrls = buildUpSubSequentUrls(gamePickerPrefix, suffix, gamePickerTotalSize);
                for (String json : goToWebSitesJSON(gamePickerUrls)) {
                    persistenceService.persistSullyChannelGameFinder(objectMapper().readValue(json, Map.class));
                }
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }


    //todo 2
    @Async
    public void importChannelGames() {
        try {
            String urlPrefix = "https://sullygnome.com/api/tables/channeltables/games/" + gamesDaysPerspective + "/";
            String urlSuffix = "/%20/1/2/desc/0/100";
            Set<Long> channelsWithoutChannelGameData = persistenceService.getChannelsWithoutChannelGameData();
            for (Long sullyId : channelsWithoutChannelGameData) {
                String json = goToWebSiteREST(urlPrefix + sullyId + urlSuffix);
                persistenceService.persistSullyChannelGames(sullyId, objectMapper().readValue(json, Map.class));
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }


    @Async
    public void importTwitchGameData() {
        Set<String> allGamesWithoutTwitchIds = persistenceService.getAllGamesWithoutTwitchIds();
        OAuthTokenDTO localToken = oAuthService.getRandomToken();
        List<Set<String>> setsOf100 = SplittingUtils.choppedSet(allGamesWithoutTwitchIds, 100);
        for (Set<String> chunk : setsOf100) {
            Map map = runGetGame(chunk, localToken);
            persistenceService.updateGameWithTwitchData(map);
        }
        persistenceService.getTwitchIdNotSetCountGame();
    }

    @Async
    public void importTwitchUsers() {
        OAuthTokenDTO localToken = oAuthService.getRandomToken();
        Set<String> usersWithoutTwitchId = persistenceService.getUsersWithoutTwitchId();
//        for (String user : usersWithoutTwitchId) {
//            Map map = runGetUser(user, localToken);
//            persistenceService.updateUserWithTwitchData(map);
//        }
        List<Set<String>> setsOf100 = SplittingUtils.choppedSet(usersWithoutTwitchId, 100);
        for (Set<String> chunk : setsOf100) {
            Map map = runGetUsers(chunk, localToken);
            persistenceService.updateUserWithTwitchData(map);
        }
    }

    //todo 1
    public void importChannelStreams() {
        Set<Long> allSullyChannels = persistenceService.getAllSullyChannels();
        try {
            for (Long id : allSullyChannels) {
                String suffix = "/" + numberOfRecords;
                String channelStreamScaffoldUrl = "https://sullygnome.com/api/tables/channeltables/streams/" + gamesDaysPerspective + "/" + id + "/%20/1/1/desc/0/10";
                String channelStreamPrefix = "https://sullygnome.com/api/tables/channeltables/streams/" + gamesDaysPerspective + "/" + id + "/%20/1/1/desc/0/";

                long streamsTotalSize;
                String jsonScaffold = goToWebSiteREST(channelStreamScaffoldUrl);
                if (jsonScaffold != null) {
                    ChannelStreamList channelStreamList = objectMapper().readValue(jsonScaffold, ChannelStreamList.class);
                    streamsTotalSize = channelStreamList.getRecordsTotal();
                    Set<String> streamsUrls = buildUpSubSequentUrls(channelStreamPrefix, suffix, streamsTotalSize);
                    for (String json : goToWebSitesJSON(streamsUrls)) {
                        persistenceService.persistSullyChannelStreams(objectMapper().readValue(json, Map.class));
                        ChannelStreamList channelStreams = objectMapper().readValue(json, ChannelStreamList.class);
                        for (ChannelStreamListDatum data : channelStreams.getData()) {
                            String url = "https://sullygnome.com/channel/" + data.getChannelurl() + "/stream/" + data.getStreamId();
                            String html = goToWebSiteHTML(url);
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
                            persistenceService.persistSullyChannelIndividualStream(data.getStreamId(), individualStreamDTO);
                        }

                    }
                }


            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @Async
    public void importTopGames() {
        OAuthTokenDTO randomToken = oAuthService.getRandomToken();
        try {
            TopGameDTO resultList = runGetTopGame(randomToken, null);
            persistenceService.persistTwitchGames(resultList.getMap());
            String cursor = resultList.getPagination().getCursor();
            while (cursor != null) {
                TopGameDTO loopList = runGetTopGame(randomToken, cursor);
                persistenceService.persistTwitchGames(resultList.getMap());
                if (loopList != null && loopList.getData() != null) {
                    String newCursor = loopList.getPagination().getCursor();
                    if (newCursor == null) {
                        break;
                    } else {
                        cursor = newCursor;
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    @Async
    public void importLiveStreams() {
        liveStreamers = new HashSet<>();
        OAuthTokenDTO randomToken = oAuthService.getRandomToken();
        try {
            StreamListDTO resultList = runGetLiveStreams(randomToken, null);
            persistenceService.persistTwitchStreams(resultList.getMap());
            if (resultList.getStreams() != null) {
                for (StreamDTO stream : resultList.getStreams()) {
                    liveStreamers.add(stream.getUserLogin());
                }
            }
            String cursor = resultList.getPagination().getCursor();
            while (cursor != null) {
                StreamListDTO loopList = runGetLiveStreams(randomToken, cursor);
                persistenceService.persistTwitchStreams(resultList.getMap());
                for (StreamDTO stream : loopList.getStreams()) {
                    liveStreamers.add(stream.getUserLogin());
                }
                if (loopList != null && loopList.getStreams() != null) {
                    String newCursor = loopList.getPagination().getCursor();
                    if (newCursor == null) {
                        break;
                    } else {
                        cursor = newCursor;
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
        String url = "https://api.twitch.tv/helix/streams?first=100";
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
                url.append(name);
                firstValue = false;
            } else {
                url.append("&login=" + name);
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
        log.debug("");
        return null;
    }

    public Map runGetUser(String loginName, OAuthTokenDTO oAuthTokenDTO) {
        StringBuilder url = new StringBuilder("https://api.twitch.tv/helix/users?login=" + loginName);
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

    public TopGameDTO runGetTopGame(OAuthTokenDTO oAuthTokenDTO, String cursor) {
        String url = "https://api.twitch.tv/helix/games/top?first=100";
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
                TopGameDTO gamesDTO = objectMapper().readValue(response.getBody(), TopGameDTO.class);
                gamesDTO.setMap(objectMapper().readValue(response.getBody(), Map.class));
                return gamesDTO;
            }
        } catch (HttpClientErrorException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isFuzzy(String term, String value) {
        int distance;
        term = term.trim();
        if (term.length() < 3) {
            distance = 0;
        } else if (term.length() < 6) {
            distance = 1;
        } else {
            distance = 2;
        }
        return StringUtils.getLevenshteinDistance(value, term) <= distance;
    }

}





