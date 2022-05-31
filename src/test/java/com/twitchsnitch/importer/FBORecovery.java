package com.twitchsnitch.importer;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.List;

public class FBORecovery {

    @Test
    public void fboRecovery() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        //File webDriverFile = new File("/Users/horizondeep/Desktop/importer/src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");
        options.addArguments("--disable-dev-shm-usage");
        ChromeDriver driver = new ChromeDriver(options);

        driver.get("https://sam.gov/data-services/Contract%20Opportunities/FBORecovery/historical?privacy=Public");
        driver.get("https://sam.gov/data-services/Contract%20Opportunities/FBORecovery/historical?privacy=Public");

        List<WebElement> elementsByClassName = driver.findElementsByClassName("data-service-file-link");
        for(WebElement element: elementsByClassName){
            element.click();
            Thread.sleep(2000);
        }
    }
}
