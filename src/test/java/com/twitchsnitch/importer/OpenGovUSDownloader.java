package com.twitchsnitch.importer;

import com.twitchsnitch.importer.dto.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OpenGovUSDownloader {

    List<InsuranceProvider> insuranceProviders = new ArrayList<>();
    List<EntitySearchResult> insuranceSearchResults = new ArrayList<>();

    List<MotorCarrier> motorCarriers = new ArrayList<>();
    List<EntitySearchResult> motorCarriersSearchResults = new ArrayList<>();

    List<Physician> physicians = new ArrayList<>();
    List<EntitySearchResult> physicianSearchResults = new ArrayList<>();

    List<Lawyer> lawyers = new ArrayList<>();
    List<EntitySearchResult> lawyerSearchResults = new ArrayList<>();

    List<EstateAgent> estateAgents = new ArrayList<>();
    List<EntitySearchResult> estateAgentSearchResults = new ArrayList<>();

    int pageSize = 1000;

    //Global IOWA SEARCH

    @Test
    public void insuranceProviderSearch() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        //File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        File webDriverFile = new File("/Users/horizondeep/Desktop/importer/src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeDriver driver = new ChromeDriver(options);

        driver.get("https://opengovus.com/iowa-insurance-producer");

        try{
            for(int i= 1; i < pageSize; i++){
                Thread.sleep(3000);
                System.out.println("Running Page: " + i);
                System.out.println("Search Results Size is: " + insuranceSearchResults.size());
                List<WebElement> trows = driver.findElementsByTagName("tr");
                extractInsuranceProviderSearch(trows);
                WebElement nextPage = driver.findElementByCssSelector("body > div.container-fluid > div > div.col-sm-12.col-md-9 > div:nth-child(3) > div.panel-footer > ul > li > a");
                JavascriptExecutor executor = (JavascriptExecutor)driver;
                executor.executeScript("arguments[0].click();", nextPage);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            driver.close();
        }



    }

    private void extractInsuranceProviderSearch(List<WebElement> trows){

        boolean firstrow = true;
        for(WebElement row: trows){
            if(firstrow){
                firstrow = false;
            }
            else{
                List<WebElement> tds = row.findElements(By.tagName("td"));
                EntitySearchResult entitySearchResult = new EntitySearchResult();
                entitySearchResult.setUrl(tds.get(0).findElement(By.tagName("a")).getAttribute("href"));
                entitySearchResult.setFullName(tds.get(0).getText());
                insuranceSearchResults.add(entitySearchResult);
                System.out.println(entitySearchResult.toString());
            }

        }
    }

    //INDIVIDUAL IOWA SEARCH

    @Test
    public void insuranceProvider() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        //File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        File webDriverFile = new File("/Users/horizondeep/Desktop/importer/src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeDriver driver = new ChromeDriver(options);

        String exampleIowaInsurance1 = "https://opengovus.com/iowa-insurance-producer/259413";
        String examplesIowaInsurance2 = "https://opengovus.com/iowa-insurance-producer/8848033";
        String exampleIowaInsurance3 = "https://opengovus.com/iowa-insurance-producer/18724548";

        driver.get(exampleIowaInsurance1);
        WebElement detailsElement = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> rows = detailsElement.findElements(By.tagName("tr"));
        extractInsuranceProvider(rows);

        driver.get(examplesIowaInsurance2);
        WebElement detailsElement2 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> rows2 = detailsElement2.findElements(By.tagName("tr"));
        extractInsuranceProvider(rows2);

        driver.get(exampleIowaInsurance3);
        WebElement detailsElement3 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> rows3 = detailsElement3.findElements(By.tagName("tr"));
        extractInsuranceProvider(rows3);

        System.out.println("Details of the individualResults are: " + insuranceProviders.size());
    }

    private void extractInsuranceProvider(List<WebElement> trows){

        boolean firstrow = true;
        InsuranceProvider insuranceProvider = new InsuranceProvider();
        for(WebElement row: trows){
            if(firstrow){
                firstrow = false;
            }
            else{
                    List<WebElement> variables = row.findElements(By.tagName("td"));
                    if(variables.get(0).getText().equalsIgnoreCase("National Producer Number")){
                        insuranceProvider.setNationalProducerNumber(variables.get(1).getText());
                    }
                if(variables.get(0).getText().equalsIgnoreCase("Last Name")){
                    insuranceProvider.setLastName(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("First Name")){
                    insuranceProvider.setFirstName(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Mailing Address")){
                    insuranceProvider.setMailingAddress(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Active Date")){
                    insuranceProvider.setActiveDate(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Expire Date")){
                    insuranceProvider.setExpireDate(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Business Phone")){
                    insuranceProvider.setBusinessPhone(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Business Email")){
                    insuranceProvider.setBusinessEmail(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Iowa Resident")){
                    insuranceProvider.setIowaResident(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Insurance Products Provided")){
                    insuranceProvider.setInsuranceProductsProvided(variables.get(1).getText());
                }

            }

        }
        insuranceProviders.add(insuranceProvider);
    }


    //MOTOR CARRIERS https://opengovus.com/motor-carrier

    @Test
    public void motorCarrierSearch() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        //File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        File webDriverFile = new File("/Users/horizondeep/Desktop/importer/src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeDriver driver = new ChromeDriver(options);

        driver.get("https://opengovus.com/motor-carrier");

        try{
            for(int i= 1; i < pageSize; i++){
                Thread.sleep(3000);
                System.out.println("Running Page: " + i);
                System.out.println("Search Results Size is: " + motorCarriersSearchResults.size());
                List<WebElement> trows = driver.findElementsByTagName("tr");
                extractMotorCarrierSearch(trows);
                WebElement nextPage = driver.findElementByCssSelector("body > div.container-fluid > div > div.col-sm-12.col-md-9 > div:nth-child(3) > div.panel-footer > ul > li > a");
                JavascriptExecutor executor = (JavascriptExecutor)driver;
                executor.executeScript("arguments[0].click();", nextPage);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            driver.close();
        }



    }

    private void extractMotorCarrierSearch(List<WebElement> trows){

        boolean firstrow = true;
        for(WebElement row: trows){
            if(firstrow){
                firstrow = false;
            }
            else{
                List<WebElement> tds = row.findElements(By.tagName("td"));
                EntitySearchResult entitySearchResult = new EntitySearchResult();
                entitySearchResult.setUrl(tds.get(0).findElement(By.tagName("a")).getAttribute("href"));
                entitySearchResult.setFullName(tds.get(0).getText());
                motorCarriersSearchResults.add(entitySearchResult);
                System.out.println(entitySearchResult.toString());
            }

        }
    }

    @Test
    public void motorCarrier() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        //File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        File webDriverFile = new File("/Users/horizondeep/Desktop/importer/src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeDriver driver = new ChromeDriver(options);

        String exampleIowaInsurance1 = "https://opengovus.com/iowa-insurance-producer/259413";
        String examplesIowaInsurance2 = "https://opengovus.com/iowa-insurance-producer/8848033";
        String exampleIowaInsurance3 = "https://opengovus.com/iowa-insurance-producer/18724548";

        driver.get(exampleIowaInsurance1);
        WebElement detailsElement = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> rows = detailsElement.findElements(By.tagName("tr"));
        extractInsuranceProvider(rows);

        driver.get(examplesIowaInsurance2);
        WebElement detailsElement2 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> rows2 = detailsElement2.findElements(By.tagName("tr"));
        extractInsuranceProvider(rows2);

        driver.get(exampleIowaInsurance3);
        WebElement detailsElement3 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> rows3 = detailsElement3.findElements(By.tagName("tr"));
        extractInsuranceProvider(rows3);

        System.out.println("Details of the individualResults are: " + insuranceProviders.size());
    }

    private void extractMotorCarrier(List<WebElement> trows){

        boolean firstrow = true;
        MotorCarrier motorCarrier = new MotorCarrier();
        for(WebElement row: trows){
            if(firstrow){
                firstrow = false;
            }
            else{
                List<WebElement> variables = row.findElements(By.tagName("td"));
                if(variables.get(0).getText().equalsIgnoreCase("DOT Number")){
                    motorCarrier.setdOTNumber(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Legal Name")){
                    motorCarrier.setLegalName(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Physical Address")){
                    motorCarrier.setPhysicalAddress(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Mailing Address")){
                    motorCarrier.setMailingAddress(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Telephone")){
                    motorCarrier.setTelephone(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Email")){
                    motorCarrier.setEmail(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Carrier Operation")){
                    motorCarrier.setCarrierOperation(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Placardable Hazardous Materials Threshold")){
                    motorCarrier.setPlacardableHazardousMaterialsThreshold(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Passengercarrier Threshold")){
                    motorCarrier.setPassengercarrierThreshold(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("MCS150 Filed Date")){
                    motorCarrier.setmCS150FiledDate(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Oversight State")){
                    motorCarrier.setOversightState(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Number of Power Units")){
                    motorCarrier.setNumberOfPowerUnits(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Number of Drivers")){
                    motorCarrier.setNumberOfDrivers(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Add Date")){
                    motorCarrier.setAddDate(variables.get(1).getText());
                }

            }

        }
        motorCarriers.add(motorCarrier);
    }



    //MEDICAL DOCTORS https://opengovus.com/physician

    @Test
    public void medicalDoctorsSearch() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        //File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        File webDriverFile = new File("/Users/horizondeep/Desktop/importer/src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeDriver driver = new ChromeDriver(options);

        driver.get("https://opengovus.com/physician");

        try{
            for(int i= 1; i < pageSize; i++){
                Thread.sleep(3000);
                System.out.println("Running Page: " + i);
                System.out.println("Search Results Size is: " + physicianSearchResults.size());
                List<WebElement> trows = driver.findElementsByTagName("tr");
                extractDoctorSearch(trows);
                WebElement nextPage = driver.findElementByCssSelector("body > div.container-fluid > div > div.col-sm-12.col-md-9 > div:nth-child(3) > div.panel-footer > ul > li > a");
                JavascriptExecutor executor = (JavascriptExecutor)driver;
                executor.executeScript("arguments[0].click();", nextPage);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            driver.close();
        }



    }

    private void extractDoctorSearch(List<WebElement> trows){

        boolean firstrow = true;
        for(WebElement row: trows){
            if(firstrow){
                firstrow = false;
            }
            else{
                List<WebElement> tds = row.findElements(By.tagName("td"));
                EntitySearchResult entitySearchResult = new EntitySearchResult();
                entitySearchResult.setUrl(tds.get(0).findElement(By.tagName("a")).getAttribute("href"));
                entitySearchResult.setFullName(tds.get(0).getText());
                physicianSearchResults.add(entitySearchResult);
                System.out.println(entitySearchResult.toString());
            }

        }
    }

    @Test
    public void medicalDoctor() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        //File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        File webDriverFile = new File("/Users/horizondeep/Desktop/importer/src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeDriver driver = new ChromeDriver(options);

        String examplePhysician1 = "https://opengovus.com/physician/4082949383";
        String examplePhysician2 = "https://opengovus.com/physician/7911398870";
        String examplePhysician3 = "https://opengovus.com/physician/0840681706";

        driver.get(examplePhysician1);
        WebElement detailsElement = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> examplePhysician1rows = detailsElement.findElements(By.tagName("tr"));
        extractMedicalDoctors(examplePhysician1rows);

        driver.get(examplePhysician2);
        WebElement detailsElement2 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> examplePhysician2rows = detailsElement2.findElements(By.tagName("tr"));
        extractMedicalDoctors(examplePhysician2rows);

        driver.get(examplePhysician3);
        WebElement detailsElement3 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> examplePhysician3rows = detailsElement3.findElements(By.tagName("tr"));
        extractMedicalDoctors(examplePhysician3rows);

        System.out.println("Details of the individualResults are: " + physicians.size());
    }

    //TODO there is more medical doc information to add
    private void extractMedicalDoctors(List<WebElement> trows){

        boolean firstrow = true;
        Physician physician = new Physician();
        for(WebElement row: trows){
            if(firstrow){
                firstrow = false;
            }
            else{
                List<WebElement> variables = row.findElements(By.tagName("td"));
                if(variables.get(0).getText().equalsIgnoreCase("NPI")){
                    physician.setnPI(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("PAC ID")){
                    physician.setpACID(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Professional Enrollment ID")){
                    physician.setProfessionalEnrollmentID(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Full Name")){
                    physician.setFullName(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Gender")){
                    physician.setGender(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Medical School")){
                    physician.setMedicalSchool(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Graduation Year")){
                    physician.setGraduationYear(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Primary Specialty")){
                    physician.setPrimarySpecialty(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Organization Legal Name")){
                    physician.setOrganizationLegalName(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Group Practice PAC ID")){
                    physician.setGroupPracticePACID(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Number of Group Practice Members")){
                    physician.setNumberOfGroupPracticeMembers(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Address")){
                    physician.setAddress(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Telephone")){
                    physician.setTelephone(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Professional Accepts Medicare Assignment")){
                    physician.setProfessionalAcceptsMedicareAssignment(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Group Accepts Medicare Assignment")){
                    physician.setGroupAcceptsMedicareAssignment(variables.get(1).getText());
                }

            }

        }
        physicians.add(physician);
    }

    //WASHINGTON LAWYERS https://opengovus.com/washington-lawyer

    @Test
    public void lawyerSearch() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        //File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        File webDriverFile = new File("/Users/horizondeep/Desktop/importer/src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeDriver driver = new ChromeDriver(options);

        driver.get("https://opengovus.com/washington-lawyer");

        try{
            for(int i= 1; i < pageSize; i++){
                Thread.sleep(3000);
                System.out.println("Running Page: " + i);
                System.out.println("Search Results Size is: " + lawyerSearchResults.size());
                List<WebElement> trows = driver.findElementsByTagName("tr");
                extractLawyerSearch(trows);
                WebElement nextPage = driver.findElementByCssSelector("body > div.container-fluid > div > div.col-sm-12.col-md-9 > div:nth-child(3) > div.panel-footer > ul > li > a");
                JavascriptExecutor executor = (JavascriptExecutor)driver;
                executor.executeScript("arguments[0].click();", nextPage);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            driver.close();
        }



    }

    private void extractLawyerSearch(List<WebElement> trows){

        boolean firstrow = true;
        for(WebElement row: trows){
            if(firstrow){
                firstrow = false;
            }
            else{
                List<WebElement> tds = row.findElements(By.tagName("td"));
                EntitySearchResult entitySearchResult = new EntitySearchResult();
                entitySearchResult.setUrl(tds.get(0).findElement(By.tagName("a")).getAttribute("href"));
                entitySearchResult.setFullName(tds.get(0).getText());
                lawyerSearchResults.add(entitySearchResult);
                System.out.println(entitySearchResult.toString());
            }

        }
    }

    @Test
    public void lawyer() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        //File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        File webDriverFile = new File("/Users/horizondeep/Desktop/importer/src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeDriver driver = new ChromeDriver(options);

        String exampleLawyer1 = "https://opengovus.com/washington-lawyer/59130";
        String exampleLawyer2 = "https://opengovus.com/washington-lawyer/59131";
        String exampleLawyer3 = "https://opengovus.com/washington-lawyer/59134";

        driver.get(exampleLawyer1);
        WebElement detailsElement = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> rows = detailsElement.findElements(By.tagName("tr"));
        extractLawyer(rows);

        driver.get(exampleLawyer2);
        WebElement detailsElement2 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> rows2 = detailsElement2.findElements(By.tagName("tr"));
        extractLawyer(rows2);

        driver.get(exampleLawyer3);
        WebElement detailsElement3 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> rows3 = detailsElement3.findElements(By.tagName("tr"));
        extractLawyer(rows3);

        System.out.println("Details of the individualResults are: " + lawyers.size());
    }

    private void extractLawyer(List<WebElement> trows){

        boolean firstrow = true;
        Lawyer lawyer = new Lawyer();
        for(WebElement row: trows){
            if(firstrow){
                firstrow = false;
            }
            else{
                List<WebElement> variables = row.findElements(By.tagName("td"));
                if(variables.get(0).getText().equalsIgnoreCase("WSBA Number")){
                    lawyer.setwSBANumber(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Full Name")){
                    lawyer.setFullName(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Last Name")){
                    lawyer.setLastName(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("First Name")){
                    lawyer.setFirstName(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Address")){
                    lawyer.setAddress(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Phone")){
                    lawyer.setPhone(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Email")){
                    lawyer.setEmail(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("License Type")){
                    lawyer.setLicenseType(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Admit Date")){
                    lawyer.setAdmitDate(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("License Status")){
                    lawyer.setLicenseStatus(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Eligible To Practice")){
                    lawyer.setEligibleToPractice(variables.get(1).getText());
                }

                //TODO PRACTICE IMPLEMENTATION AND COMPANY IMPLEMENTATION

            }

        }
        lawyers.add(lawyer);
    }

    //TEXAS REAL ESTATE LICENSES https://opengovus.com/texas-real-estate-license

    @Test
    public void realEstateAgentSearch() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        //File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        File webDriverFile = new File("/Users/horizondeep/Desktop/importer/src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeDriver driver = new ChromeDriver(options);

        driver.get("https://opengovus.com/motor-carrier");

        try{
            for(int i= 1; i < pageSize; i++){
                Thread.sleep(3000);
                System.out.println("Running Page: " + i);
                System.out.println("Search Results Size is: " + realEstateAgentSearch().size());
                List<WebElement> trows = driver.findElementsByTagName("tr");
                extractMotorCarrierSearch(trows);
                WebElement nextPage = driver.findElementByCssSelector("body > div.container-fluid > div > div.col-sm-12.col-md-9 > div:nth-child(3) > div.panel-footer > ul > li > a");
                JavascriptExecutor executor = (JavascriptExecutor)driver;
                executor.executeScript("arguments[0].click();", nextPage);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            driver.close();
        }



    }

    private void extractRealEstateSearch(List<WebElement> trows){

        boolean firstrow = true;
        for(WebElement row: trows){
            if(firstrow){
                firstrow = false;
            }
            else{
                List<WebElement> tds = row.findElements(By.tagName("td"));
                EntitySearchResult entitySearchResult = new EntitySearchResult();
                entitySearchResult.setUrl(tds.get(0).findElement(By.tagName("a")).getAttribute("href"));
                entitySearchResult.setFullName(tds.get(0).getText());
                estateAgentSearchResults.add(entitySearchResult);
                System.out.println(entitySearchResult.toString());
            }

        }
    }

    @Test
    public void realEstateAgent() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        //File webDriverFile = new File("/usr/lib/chromium-browser/chromedriver");
        File webDriverFile = new File("/Users/horizondeep/Desktop/importer/src/main/resources/chromedriver");
        System.setProperty("webdriver.chrome.driver", webDriverFile.getAbsolutePath());
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeDriver driver = new ChromeDriver(options);

        String exampleEstateAgent1 = "https://opengovus.com/texas-real-estate-license/765045";
        String exampleEstateAgent2 = "https://opengovus.com/texas-real-estate-license/765053";
        String exampleEstateAgent3 = "https://opengovus.com/texas-real-estate-license/765052";

        driver.get(exampleEstateAgent1);
        WebElement detailsElement = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> rows = detailsElement.findElements(By.tagName("tr"));
        extractRealEstate(rows);

        driver.get(exampleEstateAgent2);
        WebElement detailsElement2 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> rows2 = detailsElement2.findElements(By.tagName("tr"));
        extractRealEstate(rows2);

        driver.get(exampleEstateAgent3);
        WebElement detailsElement3 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
        List<WebElement> rows3 = detailsElement3.findElements(By.tagName("tr"));
        extractRealEstate(rows3);

        System.out.println("Details of the individualResults are: " + estateAgents.size());
    }

    private void extractRealEstate(List<WebElement> trows){

        boolean firstrow = true;
        EstateAgent estateAgent = new EstateAgent();
        for(WebElement row: trows){
            if(firstrow){
                firstrow = false;
            }
            else{
                List<WebElement> variables = row.findElements(By.tagName("td"));
                if(variables.get(0).getText().equalsIgnoreCase("License Number")){
                    estateAgent.setLicenseNumber(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Licensee Name")){
                    estateAgent.setLicenseeName(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("License Type")){
                    estateAgent.setLicenseType(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Mailing Address")){
                    estateAgent.setMailingAddress(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Telephone")){
                    estateAgent.setTelephone(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Email")){
                    estateAgent.setEmail(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("License Status")){
                    estateAgent.setLicenseStatus(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Original License Date")){
                    estateAgent.setOriginalLicenseDate(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Expiration Date")){
                    estateAgent.setExpirationDate(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Education Status")){
                    estateAgent.setEducationStatus(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("MCE Status")){
                    estateAgent.setmCEStatus(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Designated Supervisor")){
                    estateAgent.setDesignatedSupervisor(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Related License Number")){
                    estateAgent.setRelatedLicenseNumber(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Related License Type")){
                    estateAgent.setRelatedLicenseType(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Related License Start Date")){
                    estateAgent.setRelatedLicenseStartDate(variables.get(1).getText());
                }
                if(variables.get(0).getText().equalsIgnoreCase("Agency Identifier")){
                    estateAgent.setAgencyIdentifier(variables.get(1).getText());
                }
            }

        }
        estateAgents.add(estateAgent);
    }




}


