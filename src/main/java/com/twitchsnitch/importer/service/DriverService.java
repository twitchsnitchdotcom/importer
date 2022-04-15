package com.twitchsnitch.importer.service;

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
    private List<ChromeDriver> availableDrivers = new ArrayList<>();
    ChromeOptions options = new ChromeOptions();
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
            driver.manage().window().minimize();
            availableDrivers.add(driver);
        }

    }

    public ChromeDriver getAvailableDriver(){
        if(availableDrivers.size() == 0){
            ChromeDriver driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().minimize();
            return driver;
        }
        else{
            ChromeDriver chromeDriver = availableDrivers.get(0);
            availableDrivers.remove(chromeDriver);
            return chromeDriver;
        }
    }

    public void returnDriverAfterUse(ChromeDriver driver){
        availableDrivers.add(driver);
    }

    public Integer getAvailableDriversSize(){
        return availableDrivers.size();
    }



}
