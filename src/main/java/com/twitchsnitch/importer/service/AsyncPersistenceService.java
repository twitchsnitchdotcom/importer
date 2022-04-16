package com.twitchsnitch.importer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    public void persistChannelsAsync(Integer index, Set<String> urls){
        for (String url : urls) {
            try {
                persistenceService.persistSullyChannels(objectMapper().readValue(goToWebSiteJSON(index, url), Map.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Async
    public void persistTeamsAsync(Integer index, Set<String> urls){
        for (String url : urls) {
            try {
                persistenceService.persistSullyTeams(objectMapper().readValue(goToWebSiteJSON(index, url), Map.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Async
    public void persistGamesAsync(Integer index, Set<String> urls){
        for (String url : urls) {
            try {
                persistenceService.persistSullyGames(objectMapper().readValue(goToWebSiteJSON(index, url), Map.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
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

    public String goToWebSiteJSON(Integer index, String url) {
        ChromeDriver driver = driverService.getCorrectDriver(index);
        try {
            driver.get("view-source:" + url);
            String json = driver.findElement(By.className("line-content")).getText();
            return json;
        } catch (Exception e) {
            try {
                driver.get("view-source:" + url);
                //Thread.sleep(2000);
                String json = driver.findElement(By.className("line-content")).getText();
                return json;
            } catch (Exception e2) {
                log.error("FAILED COLLECTING: " + url);
            }
        }
        return null;
    }

    public Set<String> goToWebSitesJSON(Integer index, Set<String> urls) {
        Set<String> jsonList = new HashSet<>();
        ChromeDriver driver = driverService.getCorrectDriver(index);
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
}
