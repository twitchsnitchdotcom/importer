package com.twitchsnitch.importer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.*;
import com.twitchsnitch.importer.dto.twitch.OAuthTokenDTO;
import com.twitchsnitch.importer.opentender.tendersearch.TenderSearchDTO;
import com.twitchsnitch.importer.service.DriverService;
import com.twitchsnitch.importer.utils.SplittingUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OpenGovUSDownloader {

    private final static Logger log = LoggerFactory.getLogger(OpenGovUSDownloader.class);


    Set<PhysicianDTO> physicianDTOS = new HashSet<>();
    Set<String> physicianSearchResults = new HashSet<>();
    Set<String> physicianDeltaResults = new HashSet<>();
    Set<String> physicianSearchDeltaResults = new HashSet<>();
    Set<String> physicianCompletedResults = new HashSet<>();


    Set<LawyerDTO> lawyerDTOS = new HashSet<>();
    Set<String> lawyerSearchResults = new HashSet<>();
    Set<String> lawyerDeltaResults = new HashSet<>();
    Set<String> lawyerSearchDeltaResults = new HashSet<>();
    Set<String> lawyerCompletedResults = new HashSet<>();


    Set<EstateAgentDTO> estateAgentDTOS = new HashSet<>();
    Set<String> estateAgentSearchResults = new HashSet<>();
    Set<String> estateAgentDeltaResults = new HashSet<>();
    Set<String> estateAgentSearchDeltaResults = new HashSet<>();
    Set<String> estateAgentCompletedResults = new HashSet<>();

    public final ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    @Test
    public void IowaInsuranceProviders() throws IOException, URISyntaxException {

        int insurancePageSize = 1000;
        String iowaInsuranceURL = "https://opengovus.com/iowa-insurance-producer";

        Set<InsuranceProviderDTO> insuranceProviderDTOS = new HashSet<>();
        Set<String> insuranceSearchResults = new HashSet<>();
        Set<String> insuranceDeltaResults = new HashSet<>();
        Set<String> insuranceSearchDeltaResults = new HashSet<>();
        Set<String> insuranceCompletedResults = new HashSet<>();

        genericSearch(iowaInsuranceURL, insurancePageSize, insuranceSearchResults);
        //all the general pages
        for (String url : insuranceSearchResults) {
            if (!insuranceCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractInsuranceProvider(url, rows, insuranceProviderDTOS);
                    insuranceCompletedResults.add(url);
                    insuranceDeltaResults.addAll(extractExtraUrls(doc, insuranceSearchResults, insuranceCompletedResults, iowaInsuranceURL));
                    insuranceSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    log.debug("insuranceProviderDTOS size: " + insuranceProviderDTOS.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        log.debug("insuranceSearchResults size: " + insuranceSearchResults.size());
        log.debug("insuranceProviderDTOS size: " + insuranceProviderDTOS.size());
        log.debug("insuranceSearchDeltaResults size: " + insuranceSearchDeltaResults.size());
        log.debug("insuranceCompletedResults size: " + insuranceCompletedResults.size());

        // all the additional search result pages to build up the biggest delta results
        for (String url : insuranceSearchDeltaResults) {
            Document doc = null;
            try {
                doc = Jsoup.connect(url).timeout(5000).get();
                insuranceDeltaResults.addAll(extractExtraUrls(doc, insuranceSearchResults, insuranceCompletedResults, iowaInsuranceURL));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        log.debug("insuranceSearchResults size: " + insuranceSearchResults.size());
        log.debug("insuranceProviderDTOS size: " + insuranceProviderDTOS.size());
        log.debug("insuranceSearchDeltaResults size: " + insuranceSearchDeltaResults.size());
        log.debug("insuranceCompletedResults size: " + insuranceCompletedResults.size());


        //add all the final results
        for (String url : insuranceDeltaResults) {
            if (!insuranceCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractInsuranceProvider(url, rows, insuranceProviderDTOS);
                    insuranceCompletedResults.add(url);
                    log.debug("insuranceProviderDTOS size: " + insuranceProviderDTOS.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        File insurersFile = new File("/root/IdeaProjects/importer/src/test/resources/database/insurers.json");
        objectMapper().writeValue(insurersFile, insuranceProviderDTOS);

        log.debug("insuranceSearchResults size: " + insuranceSearchResults.size());
        log.debug("insuranceProviderDTOS size: " + insuranceProviderDTOS.size());
        log.debug("insuranceSearchDeltaResults size: " + insuranceSearchDeltaResults.size());
        log.debug("insuranceCompletedResults size: " + insuranceCompletedResults.size());
    }


    public void genericSearch(String url, int pageSize, Set<String> searchResults) {
        Document doc = null;
        for (int i = 1; i <= pageSize; i++) {
            try {
                if (i == 1) {
                    doc = Jsoup.connect(url).timeout(5000).get();
                } else {
                    doc = Jsoup.connect(url + "?page=" + i).timeout(5000).get();
                }
                List<Element> trows = doc.select("tr");
                boolean firstrow = true;
                for (Element row : trows) {
                    if (firstrow) {
                        firstrow = false;
                    } else {
                        List<Element> tds = row.getElementsByTag("td");
                        searchResults.add(tds.get(0).getElementsByTag("a").attr("href"));
                    }
                }
            } catch (Exception e) {
                log.error("Couldnt connect to url: " + url);
            }
        }
    }

    //INDIVIDUAL IOWA SEARCH

    private Set<String> extractExtraSearchUrls(Document doc, String urlKey) {
        Set<String> extraLinksToBeAdded = new HashSet<>();
        Elements extraLinks = doc.getElementsByTag("a");
        for (Element a : extraLinks) {
            if (a.hasAttr("href") && a.hasAttr("target") && a.attr("target").equalsIgnoreCase("_blank")) {
                String link = a.attr("href");
                if (link.length() > urlKey.length() && !link.contains("contact?")) {
                    extraLinksToBeAdded.add(link);
                }
            }
        }
        if (extraLinksToBeAdded.size() > 0) {
            log.trace("Extra links to be added to the search list: " + extraLinksToBeAdded.size() + " details: " + extraLinksToBeAdded.toString());
        }
        return extraLinksToBeAdded;
    }

    private Set<String> extractExtraUrls(Document doc, Set<String> searchResults, Set<String> completedResults, String urlKey) {
        urlKey = urlKey + "/";
        Set<String> extraLinksToBeAdded = new HashSet<>();
        Elements extraLinks = doc.getElementsByTag("a");
        for (Element a : extraLinks) {
            if (a.hasAttr("href")) {
                String link = a.attr("href");
                if (link.contains(urlKey) && link.length() > urlKey.length()) {
                    if (!searchResults.contains(urlKey) && !completedResults.contains(urlKey) && !link.contains("?")) {
                        extraLinksToBeAdded.add(link);
                    }
                }
            }
        }
        if (extraLinksToBeAdded.size() > 0) {
            log.trace("Extra links to be added to the search list: " + extraLinksToBeAdded.size() + " details: " + extraLinksToBeAdded.toString());
        }
        return extraLinksToBeAdded;
    }

    private void extractInsuranceProvider(String url, List<Element> trows, Set<InsuranceProviderDTO> insuranceProviderDTOS) {

        InsuranceProviderDTO insuranceProviderDTO = new InsuranceProviderDTO();
        insuranceProviderDTO.setUrl(url);
        for (Element row : trows) {
            List<Element> variables = row.getElementsByTag("td");
            if (variables.get(0).text().equalsIgnoreCase("National Producer Number")) {
                insuranceProviderDTO.setNationalProducerNumber(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Last Name")) {
                insuranceProviderDTO.setLastName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("First Name")) {
                insuranceProviderDTO.setFirstName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Mailing Address")) {
                insuranceProviderDTO.setMailingAddress(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Active Date")) {
                insuranceProviderDTO.setActiveDate(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Expire Date")) {
                insuranceProviderDTO.setExpireDate(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Business Phone")) {
                insuranceProviderDTO.setBusinessPhone(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Business Email")) {
                insuranceProviderDTO.setBusinessEmail(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Iowa Resident")) {
                insuranceProviderDTO.setIowaResident(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Insurance Products Provided")) {
                insuranceProviderDTO.setInsuranceProductsProvided(variables.get(1).text());
            }

        }
        insuranceProviderDTOS.add(insuranceProviderDTO);
        log.trace("Added Provider: " + insuranceProviderDTO.getUrl());
    }


    //MOTOR CARRIERS https://opengovus.com/motor-carrier

    @Test
    public void motorCarrierSearch() throws InterruptedException, IOException {

        String motorCarrierUrl = "https://opengovus.com/motor-carrier";

        Set<MotorCarrierDTO> motorCarrierDTOS = new HashSet<>();
        Set<String> motorCarriersSearchResults = new HashSet<>();
        Set<String> motorCarriersDeltaResults = new HashSet<>();
        Set<String> motorCarriersSearchDeltaResults = new HashSet<>();
        Set<String> motorCarriersCompletedResults = new HashSet<>();

        genericSearch(motorCarrierUrl, 1, motorCarriersSearchResults);

        //all the general pages x 10
        for (String url : motorCarriersSearchResults) {
            if (!motorCarriersCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).timeout(5000).get();
                    Elements select = doc.select("#entity-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractMotorCarrier(url, rows, motorCarrierDTOS);
                    motorCarriersCompletedResults.add(url);
                    motorCarriersDeltaResults.addAll(extractExtraUrls(doc, motorCarriersSearchResults, motorCarriersCompletedResults, motorCarrierUrl));
                    motorCarriersSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url: " + url);
                }
            }
        }

        log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
        log.debug("motorCarriersSearchResults size: " + motorCarriersSearchResults.size());
        log.debug("motorCarriersDeltaResults size: " + motorCarriersDeltaResults.size());
        log.debug("motorCarriersSearchDeltaResults size: " + motorCarriersSearchDeltaResults.size());
        log.debug("motorCarriersCompletedResults size: " + motorCarriersCompletedResults.size());

        for (String url : motorCarriersSearchDeltaResults) {
            for (int i = 1; i <= 2; i++) {
                Document doc = null;
                try {
                    if (i == 1) {
                        doc = Jsoup.connect(url).timeout(5000).get();
                    } else {
                        doc = Jsoup.connect(url + "?page=" + i).timeout(5000).get();
                    }
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractMotorCarrier(url, rows, motorCarrierDTOS);
                    motorCarriersCompletedResults.add(url);
                    motorCarriersDeltaResults.addAll(extractExtraUrls(doc, motorCarriersSearchResults, motorCarriersCompletedResults, motorCarrierUrl));
                    log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url: " + url);
                }

            }
        }

        log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
        log.debug("motorCarriersSearchResults size: " + motorCarriersSearchResults.size());
        log.debug("motorCarriersDeltaResults size: " + motorCarriersDeltaResults.size());
        log.debug("motorCarriersSearchDeltaResults size: " + motorCarriersSearchDeltaResults.size());
        log.debug("motorCarriersCompletedResults size: " + motorCarriersCompletedResults.size());

        for (String url : motorCarriersDeltaResults) {
            if (!motorCarriersCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractMotorCarrier(url, rows, motorCarrierDTOS);
                    motorCarriersCompletedResults.add(url);
                    log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url: " + url);
                }
            }
        }

        log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
        log.debug("motorCarriersSearchResults size: " + motorCarriersSearchResults.size());
        log.debug("motorCarriersDeltaResults size: " + motorCarriersDeltaResults.size());
        log.debug("motorCarriersSearchDeltaResults size: " + motorCarriersSearchDeltaResults.size());
        log.debug("motorCarriersCompletedResults size: " + motorCarriersCompletedResults.size());

        File motorCarriersFile = new File("/root/IdeaProjects/importer/src/test/resources/database/motorcarriers.json");
        objectMapper().writeValue(motorCarriersFile, motorCarrierDTOS);

    }


    private void extractMotorCarrier(String url, List<Element> trows, Set<MotorCarrierDTO> motorCarrierDTOS) {

        MotorCarrierDTO motorCarrier = new MotorCarrierDTO();
        motorCarrier.setUrl(url);
        for (Element row : trows) {
            List<Element> variables = row.getElementsByTag("td");
            if (variables.get(0).text().equalsIgnoreCase("DOT Number")) {
                motorCarrier.setdOTNumber(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Legal Name")) {
                motorCarrier.setLegalName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Physical Address")) {
                motorCarrier.setPhysicalAddress(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Mailing Address")) {
                motorCarrier.setMailingAddress(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Telephone")) {
                motorCarrier.setTelephone(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Email")) {
                motorCarrier.setEmail(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Carrier Operation")) {
                motorCarrier.setCarrierOperation(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Placardable Hazardous Materials Threshold")) {
                motorCarrier.setPlacardableHazardousMaterialsThreshold(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Passengercarrier Threshold")) {
                motorCarrier.setPassengercarrierThreshold(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("MCS150 Filed Date")) {
                motorCarrier.setmCS150FiledDate(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Oversight State")) {
                motorCarrier.setOversightState(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Number of Power Units")) {
                motorCarrier.setNumberOfPowerUnits(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Number of Drivers")) {
                motorCarrier.setNumberOfDrivers(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Add Date")) {
                motorCarrier.setAddDate(variables.get(1).text());
            }

        }
        motorCarrierDTOS.add(motorCarrier);
    }

//
//
//    //MEDICAL DOCTORS https://opengovus.com/physician
//
//    @Test
//    public void medicalDoctorsSearch() throws InterruptedException {
//
//
//        driver.get("https://opengovus.com/physician");
//
//        try{
//            for(int i= 1; i < pageSize; i++){
//                Thread.sleep(3000);
//                System.out.println("Running Page: " + i);
//                System.out.println("Search Results Size is: " + physicianSearchResults.size());
//                List<WebElement> trows = driver.findElementsByTagName("tr");
//                extractDoctorSearch(trows);
//                WebElement nextPage = driver.findElementByCssSelector("body > div.container-fluid > div > div.col-sm-12.col-md-9 > div:nth-child(3) > div.panel-footer > ul > li > a");
//                JavascriptExecutor executor = (JavascriptExecutor)driver;
//                executor.executeScript("arguments[0].click();", nextPage);
//            }
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            driver.close();
//        }
//
//
//
//    }
//
//    private void extractDoctorSearch(List<WebElement> trows){
//
//        boolean firstrow = true;
//        for(WebElement row: trows){
//            if(firstrow){
//                firstrow = false;
//            }
//            else{
//                List<WebElement> tds = row.findElements(By.tagName("td"));
//                EntitySearchResult entitySearchResult = new EntitySearchResult();
//                entitySearchResult.setUrl(tds.get(0).findElement(By.tagName("a")).getAttribute("href"));
//                entitySearchResult.setFullName(tds.get(0).getText());
//                physicianSearchResults.add(entitySearchResult);
//                System.out.println(entitySearchResult.toString());
//            }
//
//        }
//    }
//
//    @Test
//    public void medicalDoctor() throws InterruptedException {
//
//
//        String examplePhysician1 = "https://opengovus.com/physician/4082949383";
//        String examplePhysician2 = "https://opengovus.com/physician/7911398870";
//        String examplePhysician3 = "https://opengovus.com/physician/0840681706";
//
//        driver.get(examplePhysician1);
//        WebElement detailsElement = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
//        List<WebElement> examplePhysician1rows = detailsElement.findElements(By.tagName("tr"));
//        extractMedicalDoctors(examplePhysician1rows);
//
//        driver.get(examplePhysician2);
//        WebElement detailsElement2 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
//        List<WebElement> examplePhysician2rows = detailsElement2.findElements(By.tagName("tr"));
//        extractMedicalDoctors(examplePhysician2rows);
//
//        driver.get(examplePhysician3);
//        WebElement detailsElement3 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
//        List<WebElement> examplePhysician3rows = detailsElement3.findElements(By.tagName("tr"));
//        extractMedicalDoctors(examplePhysician3rows);
//
//        System.out.println("Details of the individualResults are: " + physicians.size());
//    }
//
//    //TODO there is more medical doc information to add
//    private void extractMedicalDoctors(List<WebElement> trows){
//
//        boolean firstrow = true;
//        Physician physician = new Physician();
//        for(WebElement row: trows){
//            if(firstrow){
//                firstrow = false;
//            }
//            else{
//                List<WebElement> variables = row.findElements(By.tagName("td"));
//                if(variables.get(0).getText().equalsIgnoreCase("NPI")){
//                    physician.setnPI(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("PAC ID")){
//                    physician.setpACID(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Professional Enrollment ID")){
//                    physician.setProfessionalEnrollmentID(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Full Name")){
//                    physician.setFullName(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Gender")){
//                    physician.setGender(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Medical School")){
//                    physician.setMedicalSchool(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Graduation Year")){
//                    physician.setGraduationYear(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Primary Specialty")){
//                    physician.setPrimarySpecialty(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Organization Legal Name")){
//                    physician.setOrganizationLegalName(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Group Practice PAC ID")){
//                    physician.setGroupPracticePACID(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Number of Group Practice Members")){
//                    physician.setNumberOfGroupPracticeMembers(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Address")){
//                    physician.setAddress(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Telephone")){
//                    physician.setTelephone(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Professional Accepts Medicare Assignment")){
//                    physician.setProfessionalAcceptsMedicareAssignment(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Group Accepts Medicare Assignment")){
//                    physician.setGroupAcceptsMedicareAssignment(variables.get(1).getText());
//                }
//
//            }
//
//        }
//        physicians.add(physician);
//    }
//
//    //WASHINGTON LAWYERS https://opengovus.com/washington-lawyer
//
//    @Test
//    public void lawyerSearch() throws InterruptedException {
//
//
//        driver.get("https://opengovus.com/washington-lawyer");
//
//        try{
//            for(int i= 1; i < pageSize; i++){
//                Thread.sleep(3000);
//                System.out.println("Running Page: " + i);
//                System.out.println("Search Results Size is: " + lawyerSearchResults.size());
//                List<WebElement> trows = driver.findElementsByTagName("tr");
//                extractLawyerSearch(trows);
//                WebElement nextPage = driver.findElementByCssSelector("body > div.container-fluid > div > div.col-sm-12.col-md-9 > div:nth-child(3) > div.panel-footer > ul > li > a");
//                JavascriptExecutor executor = (JavascriptExecutor)driver;
//                executor.executeScript("arguments[0].click();", nextPage);
//            }
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            driver.close();
//        }
//
//
//
//    }
//
//    private void extractLawyerSearch(List<WebElement> trows){
//
//        boolean firstrow = true;
//        for(WebElement row: trows){
//            if(firstrow){
//                firstrow = false;
//            }
//            else{
//                List<WebElement> tds = row.findElements(By.tagName("td"));
//                EntitySearchResult entitySearchResult = new EntitySearchResult();
//                entitySearchResult.setUrl(tds.get(0).findElement(By.tagName("a")).getAttribute("href"));
//                entitySearchResult.setFullName(tds.get(0).getText());
//                lawyerSearchResults.add(entitySearchResult);
//                System.out.println(entitySearchResult.toString());
//            }
//
//        }
//    }
//
//    @Test
//    public void lawyer() throws InterruptedException {
//
//
//        String exampleLawyer1 = "https://opengovus.com/washington-lawyer/59130";
//        String exampleLawyer2 = "https://opengovus.com/washington-lawyer/59131";
//        String exampleLawyer3 = "https://opengovus.com/washington-lawyer/59134";
//
//        driver.get(exampleLawyer1);
//        WebElement detailsElement = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
//        List<WebElement> rows = detailsElement.findElements(By.tagName("tr"));
//        extractLawyer(rows);
//
//        driver.get(exampleLawyer2);
//        WebElement detailsElement2 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
//        List<WebElement> rows2 = detailsElement2.findElements(By.tagName("tr"));
//        extractLawyer(rows2);
//
//        driver.get(exampleLawyer3);
//        WebElement detailsElement3 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
//        List<WebElement> rows3 = detailsElement3.findElements(By.tagName("tr"));
//        extractLawyer(rows3);
//
//        System.out.println("Details of the individualResults are: " + lawyers.size());
//    }
//
//    private void extractLawyer(List<WebElement> trows){
//
//        boolean firstrow = true;
//        Lawyer lawyer = new Lawyer();
//        for(WebElement row: trows){
//            if(firstrow){
//                firstrow = false;
//            }
//            else{
//                List<WebElement> variables = row.findElements(By.tagName("td"));
//                if(variables.get(0).getText().equalsIgnoreCase("WSBA Number")){
//                    lawyer.setwSBANumber(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Full Name")){
//                    lawyer.setFullName(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Last Name")){
//                    lawyer.setLastName(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("First Name")){
//                    lawyer.setFirstName(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Address")){
//                    lawyer.setAddress(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Phone")){
//                    lawyer.setPhone(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Email")){
//                    lawyer.setEmail(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("License Type")){
//                    lawyer.setLicenseType(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Admit Date")){
//                    lawyer.setAdmitDate(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("License Status")){
//                    lawyer.setLicenseStatus(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Eligible To Practice")){
//                    lawyer.setEligibleToPractice(variables.get(1).getText());
//                }
//
//                //TODO PRACTICE IMPLEMENTATION AND COMPANY IMPLEMENTATION
//
//            }
//
//        }
//        lawyers.add(lawyer);
//    }
//
//    //TEXAS REAL ESTATE LICENSES https://opengovus.com/texas-real-estate-license
//
//    @Test
//    public void realEstateAgentSearch() throws InterruptedException {
//
//
//        driver.get("https://opengovus.com/motor-carrier");
//
//        try{
//            for(int i= 1; i < pageSize; i++){
//                Thread.sleep(3000);
//                System.out.println("Running Page: " + i);
//                System.out.println("Search Results Size is: " + estateAgentSearchResults.size());
//                List<WebElement> trows = driver.findElementsByTagName("tr");
//                extractMotorCarrierSearch(trows);
//                WebElement nextPage = driver.findElementByCssSelector("body > div.container-fluid > div > div.col-sm-12.col-md-9 > div:nth-child(3) > div.panel-footer > ul > li > a");
//                JavascriptExecutor executor = (JavascriptExecutor)driver;
//                executor.executeScript("arguments[0].click();", nextPage);
//            }
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            driver.close();
//        }
//
//
//
//    }
//
//    private void extractRealEstateSearch(List<WebElement> trows){
//
//        boolean firstrow = true;
//        for(WebElement row: trows){
//            if(firstrow){
//                firstrow = false;
//            }
//            else{
//                List<WebElement> tds = row.findElements(By.tagName("td"));
//                EntitySearchResult entitySearchResult = new EntitySearchResult();
//                entitySearchResult.setUrl(tds.get(0).findElement(By.tagName("a")).getAttribute("href"));
//                entitySearchResult.setFullName(tds.get(0).getText());
//                estateAgentSearchResults.add(entitySearchResult);
//                System.out.println(entitySearchResult.toString());
//            }
//
//        }
//    }
//
//    @Test
//    public void realEstateAgent() throws InterruptedException {
//
//
//        String exampleEstateAgent1 = "https://opengovus.com/texas-real-estate-license/765045";
//        String exampleEstateAgent2 = "https://opengovus.com/texas-real-estate-license/765053";
//        String exampleEstateAgent3 = "https://opengovus.com/texas-real-estate-license/765052";
//
//        driver.get(exampleEstateAgent1);
//        WebElement detailsElement = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
//        List<WebElement> rows = detailsElement.findElements(By.tagName("tr"));
//        extractRealEstate(rows);
//
//        driver.get(exampleEstateAgent2);
//        WebElement detailsElement2 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
//        List<WebElement> rows2 = detailsElement2.findElements(By.tagName("tr"));
//        extractRealEstate(rows2);
//
//        driver.get(exampleEstateAgent3);
//        WebElement detailsElement3 = driver.findElementByCssSelector("#overview > div.panel-body > div > table > tbody");
//        List<WebElement> rows3 = detailsElement3.findElements(By.tagName("tr"));
//        extractRealEstate(rows3);
//
//        System.out.println("Details of the individualResults are: " + estateAgents.size());
//    }
//
//    private void extractRealEstate(List<WebElement> trows){
//
//        boolean firstrow = true;
//        EstateAgent estateAgent = new EstateAgent();
//        for(WebElement row: trows){
//            if(firstrow){
//                firstrow = false;
//            }
//            else{
//                List<WebElement> variables = row.findElements(By.tagName("td"));
//                if(variables.get(0).getText().equalsIgnoreCase("License Number")){
//                    estateAgent.setLicenseNumber(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Licensee Name")){
//                    estateAgent.setLicenseeName(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("License Type")){
//                    estateAgent.setLicenseType(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Mailing Address")){
//                    estateAgent.setMailingAddress(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Telephone")){
//                    estateAgent.setTelephone(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Email")){
//                    estateAgent.setEmail(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("License Status")){
//                    estateAgent.setLicenseStatus(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Original License Date")){
//                    estateAgent.setOriginalLicenseDate(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Expiration Date")){
//                    estateAgent.setExpirationDate(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Education Status")){
//                    estateAgent.setEducationStatus(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("MCE Status")){
//                    estateAgent.setmCEStatus(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Designated Supervisor")){
//                    estateAgent.setDesignatedSupervisor(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Related License Number")){
//                    estateAgent.setRelatedLicenseNumber(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Related License Type")){
//                    estateAgent.setRelatedLicenseType(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Related License Start Date")){
//                    estateAgent.setRelatedLicenseStartDate(variables.get(1).getText());
//                }
//                if(variables.get(0).getText().equalsIgnoreCase("Agency Identifier")){
//                    estateAgent.setAgencyIdentifier(variables.get(1).getText());
//                }
//            }
//
//        }
//        estateAgents.add(estateAgent);
//    }


}


