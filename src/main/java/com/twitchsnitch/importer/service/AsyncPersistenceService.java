package com.twitchsnitch.importer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class AsyncPersistenceService {

    private final PersistenceService persistenceService;
    private final DriverService driverService;

    public AsyncPersistenceService(PersistenceService persistenceService,DriverService driverService){
        this.driverService = driverService;
        this.persistenceService = persistenceService;
    }

    public final ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    private final static Logger log = LoggerFactory.getLogger(AsyncPersistenceService.class);


    @Async
    public void persistChannelsAsync(Set<String> urls){
        for (String url : urls) {
            try {
                String json = goToWebSiteREST(url);
                if(json!= null){
                    persistenceService.persistSullyChannels(objectMapper().readValue(json, Map.class));
                }

            } catch (JsonProcessingException e) {
                log.error("Can't get url: " + url);
            }
        }
    }


    @Async
    public void persistTeamsAsync(Integer index, Set<String> urls){
        for (String url : urls) {
            try {
                String json = goToWebSiteREST(url);
                if(json!= null) {
                    persistenceService.persistSullyTeams(objectMapper().readValue(json, Map.class));
                }
            } catch (JsonProcessingException e) {
                log.error("Can't get url: " + url);
            }
        }
    }


    @Async
    public void persistGamesAsync(Integer index, Set<String> urls){
        for (String url : urls) {
            try {
                String json = goToWebSiteREST(url);
                if(json!= null) {
                    persistenceService.persistSullyGames(objectMapper().readValue(json, Map.class));
                }
            } catch (JsonProcessingException e) {
                log.error("Can't get url: " + url);
            }
        }
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



    @Async
    public void channelStreamsAsync(){

    }

    @Async
    public void channelGamesAsync(){

    }

    @Async
    public void raidFinderAsync(){

    }

    @Async
    public void raidPickerAsync(){
        
    }

    public String goToWebSiteJSON(String url) {
        ChromeDriver driver = driverService.getRandomDriver();
        try {
            driver.get("view-source:" + url);
            String json = driver.findElement(By.className("line-content")).getText();
            driverService.returnWebDriver(driver);
            return json;
        } catch (Exception e) {
            log.error("FAILED COLLECTING: " + url);
        }
        driverService.returnWebDriver(driver);
        return null;
    }

    public Set<String> goToWebSitesJSON(Set<String> urls) {
        Set<String> jsonList = new HashSet<>();
        ChromeDriver driver = driverService.getRandomDriver();
        for (String url : urls) {
            try {
                driver.get("view-source:" + url);
                jsonList.add(driver.findElement(By.className("line-content")).getText());
                log.debug("Pages collected so far is: " + jsonList.size());
            } catch (Exception e) {
                log.error("FAILED COLLECTING: " + url);
            }
        }
        driverService.returnWebDriver(driver);
        return jsonList;
    }


}
