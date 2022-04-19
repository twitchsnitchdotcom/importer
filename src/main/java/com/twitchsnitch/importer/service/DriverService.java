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
    private Set<ChromeDriver> availableDrivers = new HashSet<>();
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
            availableDrivers.add(driver);
        }

        areDriversAvailable = true;

    }

    public void returnWebDriver(ChromeDriver driver){
        availableDrivers.add(driver);
        log.debug("Driver being returned is: " + driver.hashCode());
    }

    public ChromeDriver getRandomDriver(){
        log.debug("Current size of available drivers is: " + availableDrivers.size());
        while(availableDrivers.size() == 0){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("Waiting for a web driver to become available before continuing");
        }

    return availableDrivers.stream().skip(new Random().nextInt(availableDrivers.size())).findFirst().orElse(null);

}

    public  <E> E getRandomSetElement(Set<E> set) {
        return set.stream().skip(new Random().nextInt(set.size())).findFirst().orElse(null);
    }


}
