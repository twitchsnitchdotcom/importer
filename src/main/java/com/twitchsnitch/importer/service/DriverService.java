package com.twitchsnitch.importer.service;

import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class DriverService {

    private final static Logger log = LoggerFactory.getLogger(DriverService.class);

    @Value("${webdrivers.size.max}")
    private Integer webDriversSize;
    private Map<Integer, ChromeDriver> availableDrivers = new HashMap<>();
    ChromeOptions options = new ChromeOptions();
    private boolean areDriversAvailable = false;
    //GENERIC METHODS
    @PostConstruct
    public void initWebDriver() throws URISyntaxException {
        File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");

        for(int i =0; i < webDriversSize; i++){
            ChromeDriver driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().setPosition(new Point(-2000, 0));
            availableDrivers.put(i,driver);
        }

        areDriversAvailable = true;

    }


    public ChromeDriver getCorrectDriver(int index){
        return availableDrivers.get(index);
    }

    public ChromeDriver getCorrectDriver(){
        return availableDrivers.get(0);
    }

    public Integer getAvailableDriversSize(){
        return availableDrivers.size();
    }



}
