package com.twitchsnitch.importer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.*;
import com.twitchsnitch.importer.dto.twitch.OAuthTokenDTO;
import com.twitchsnitch.importer.opentender.tendersearch.TenderSearchDTO;
import com.twitchsnitch.importer.proxy.ProxyDTO;
import com.twitchsnitch.importer.service.DriverService;
import com.twitchsnitch.importer.utils.SplittingUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class OpenGovUSDownloader {

    private final static Logger log = LoggerFactory.getLogger(OpenGovUSDownloader.class);
    private RestTemplate restTemplate = new RestTemplate();

    Set<WorkingProxyDTO> workingProxies = new HashSet<>();

    public final ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    <E> E getRandomSetElement(Set<E> set) {
        return set.stream().skip(new Random().nextInt(set.size())).findFirst().orElse(null);
    }

    public WorkingProxyDTO getRandomProxy() {
        return getRandomSetElement(workingProxies);
    }


    public void addWorkingProxies(){
        workingProxies.add(new WorkingProxyDTO("64.225.27.133", 3128));
        workingProxies.add(new WorkingProxyDTO("138.197.71.194", 3128));
        workingProxies.add(new WorkingProxyDTO("159.65.183.49", 3128));
        workingProxies.add(new WorkingProxyDTO("138.197.74.40", 3128));
        workingProxies.add(new WorkingProxyDTO("143.198.18.47", 3128));
        workingProxies.add(new WorkingProxyDTO("143.198.16.169", 3128));
        workingProxies.add(new WorkingProxyDTO("143.198.3.16", 3128));
        workingProxies.add(new WorkingProxyDTO("159.65.45.59", 3128));
        workingProxies.add(new WorkingProxyDTO("167.172.238.66", 3128));
        workingProxies.add(new WorkingProxyDTO("165.232.187.113", 3128));

    }

    @Test
    public void IowaInsuranceProviders() throws IOException, URISyntaxException {
        addWorkingProxies();
        int insurancePageSize = 1000;
        String iowaInsuranceURL = "https://opengovus.com/iowa-insurance-producer";

        Set<InsuranceProviderDTO> insuranceProviderDTOS = new HashSet<>();
        Set<String> insuranceSearchResults = new HashSet<>();
        Set<String> insuranceErrorResults = new HashSet<>();
        Set<String> insuranceDeltaResults = new HashSet<>();
        Set<String> insuranceSearchDeltaResults = new HashSet<>();
        Set<String> insuranceCompletedResults = new HashSet<>();

        File insurersSearchFile = new File("/root/IdeaProjects/importer/src/test/resources/database/insurers_search_results.json");
        insuranceSearchResults =  objectMapper().readValue(insurersSearchFile, new TypeReference<Set<String>>() {});
        if(insuranceSearchResults.size() == 0){
            genericSearch(iowaInsuranceURL, insurancePageSize, insuranceSearchResults, "insurers_search_results.json");
        }
        //all the general pages

        for (String url : insuranceSearchResults) {
            if (!insuranceCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractInsuranceProvider(url, rows, insuranceProviderDTOS);
                    insuranceCompletedResults.add(url);
                    insuranceDeltaResults.addAll(extractExtraUrls(doc, insuranceSearchResults, insuranceCompletedResults, iowaInsuranceURL));
                    insuranceSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    log.debug("insuranceProviderDTOS size: " + insuranceProviderDTOS.size());
                } catch (Exception e) {
                    log.error("Issue adding all the general pages");
                    insuranceErrorResults.add(url);
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
                WorkingProxyDTO randomProxy = getRandomProxy();
                doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                insuranceDeltaResults.addAll(extractExtraUrls(doc, insuranceSearchResults, insuranceCompletedResults, iowaInsuranceURL));
            } catch (Exception e) {
                log.error("Issue with addin the delta search results: " + url);
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
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractInsuranceProvider(url, rows, insuranceProviderDTOS);
                    insuranceCompletedResults.add(url);
                    log.debug("insuranceProviderDTOS size: " + insuranceProviderDTOS.size());
                } catch (Exception e) {
                    log.error("Issue with final results: " + url);
                    insuranceErrorResults.add(url);
                }
            }
        }

        //add all the final results
        for (String url : insuranceErrorResults) {
            if (!insuranceCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractInsuranceProvider(url, rows, insuranceProviderDTOS);
                    insuranceCompletedResults.add(url);
                    log.debug("insuranceProviderDTOS size: " + insuranceProviderDTOS.size());
                } catch (Exception e) {
                    log.error("Issue with final results: " + url);
                }
            }
        }

        File insurersFile = new File("/root/IdeaProjects/importer/src/test/resources/database/insurers.json");
        objectMapper().writeValue(insurersFile, insuranceProviderDTOS);

        File insurersErrorsFile = new File("/root/IdeaProjects/importer/src/test/resources/database/insurers_errors.json");
        objectMapper().writeValue(insurersErrorsFile, insuranceErrorResults);

        log.debug("insuranceSearchResults size: " + insuranceSearchResults.size());
        log.debug("insuranceProviderDTOS size: " + insuranceProviderDTOS.size());
        log.debug("insuranceSearchDeltaResults size: " + insuranceSearchDeltaResults.size());
        log.debug("insuranceCompletedResults size: " + insuranceCompletedResults.size());
    }

    public void genericSearch(String url, int pageSize, Set<String> searchResults, String fileName) throws IOException {
        Document doc = null;
        for (int i = 1; i <= pageSize; i++) {
            log.debug("Running page: " + i + " searchResultsSize is: " + searchResults.size());
            try {
                if (i == 1) {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(10000).get();
                } else {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url + "?page=" + i).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(10000).get();
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
                log.error("Error in generic search, connect to url: " + url);
            }
        }
        //File insurersFile = new File("/Users/horizondeep/Desktop/importer/src/test/resources/database/" + fileName);
        File insurersFile = new File("/root/IdeaProjects/importer/src/test/resources/database/" + fileName);
        objectMapper().writeValue(insurersFile, searchResults);

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
        addWorkingProxies();
        String motorCarrierUrl = "https://opengovus.com/motor-carrier";

        Set<MotorCarrierDTO> motorCarrierDTOS = new HashSet<>();
        Set<String> motorCarriersSearchResults = new HashSet<>();
        Set<String> motorCarriersDeltaResults = new HashSet<>();
        Set<String> motorCarrierErrorResults = new HashSet<>();
        Set<String> motorCarriersSearchDeltaResults = new HashSet<>();
        Set<String> motorCarriersCompletedResults = new HashSet<>();

        genericSearch(motorCarrierUrl, 10, motorCarriersSearchResults, "motorcarriers_search_results.json");

        //all the general pages x 10
        for (String url : motorCarriersSearchResults) {
            if (!motorCarriersCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#entity-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractMotorCarrier(url, rows, motorCarrierDTOS);
                    motorCarriersCompletedResults.add(url);
                    motorCarriersDeltaResults.addAll(extractExtraUrls(doc, motorCarriersSearchResults, motorCarriersCompletedResults, motorCarrierUrl));
                    motorCarriersSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
                } catch (Exception e) {
                    log.error("Issue in connect to url: " + url + " with the generic pages");
                    motorCarrierErrorResults.add(url);
                }
            }
        }

        log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
        log.debug("motorCarriersSearchResults size: " + motorCarriersSearchResults.size());
        log.debug("motorCarriersDeltaResults size: " + motorCarriersDeltaResults.size());
        log.debug("motorCarriersSearchDeltaResults size: " + motorCarriersSearchDeltaResults.size());
        log.debug("motorCarriersCompletedResults size: " + motorCarriersCompletedResults.size());
        log.debug("motorCarrierErrorResults size: " + motorCarrierErrorResults.size());

        for (String url : motorCarriersSearchDeltaResults) {
            for (int i = 1; i <= 10; i++) {
                Document doc = null;
                try {
                    if (i == 1) {
                        WorkingProxyDTO randomProxy = getRandomProxy();
                        doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    } else {
                        WorkingProxyDTO randomProxy = getRandomProxy();
                        doc = Jsoup.connect(url + "?page=" + i).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    }
                    motorCarriersDeltaResults.addAll(extractExtraUrls(doc, motorCarriersSearchResults, motorCarriersCompletedResults, motorCarrierUrl));
                    log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url: " + url + "when looking in the delta search results");
                }

            }
        }

        log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
        log.debug("motorCarriersSearchResults size: " + motorCarriersSearchResults.size());
        log.debug("motorCarriersDeltaResults size: " + motorCarriersDeltaResults.size());
        log.debug("motorCarriersSearchDeltaResults size: " + motorCarriersSearchDeltaResults.size());
        log.debug("motorCarriersCompletedResults size: " + motorCarriersCompletedResults.size());
        log.debug("motorCarrierErrorResults size: " + motorCarrierErrorResults.size());

        for (String url : motorCarriersDeltaResults) {
            if (!motorCarriersCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#entity-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractMotorCarrier(url, rows, motorCarrierDTOS);
                    motorCarriersCompletedResults.add(url);
                    log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                    motorCarrierErrorResults.add(url);
                }
            }
        }

        for (String url : motorCarrierErrorResults) {
            if (!motorCarriersCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#entity-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractMotorCarrier(url, rows, motorCarrierDTOS);
                    motorCarriersCompletedResults.add(url);
                    log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                }
            }
        }

        log.debug("motorCarrierDTOS size: " + motorCarrierDTOS.size());
        log.debug("motorCarriersSearchResults size: " + motorCarriersSearchResults.size());
        log.debug("motorCarriersDeltaResults size: " + motorCarriersDeltaResults.size());
        log.debug("motorCarriersSearchDeltaResults size: " + motorCarriersSearchDeltaResults.size());
        log.debug("motorCarriersCompletedResults size: " + motorCarriersCompletedResults.size());
        log.debug("motorCarrierErrorResults size: " + motorCarrierErrorResults.size());

        File motorCarriersFile = new File("/root/IdeaProjects/importer/src/test/resources/database/motorcarriers.json");
        objectMapper().writeValue(motorCarriersFile, motorCarrierDTOS);

        File motorCarriersErrorsFile = new File("/root/IdeaProjects/importer/src/test/resources/database/motorcarriers_errors.json");
        objectMapper().writeValue(motorCarriersErrorsFile, motorCarrierErrorResults);

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
    @Test
    public void medicalDoctorsSearch() throws InterruptedException, IOException {
        addWorkingProxies();
        String doctorUrl = "https://opengovus.com/physician";

        Set<PhysicianDTO> physicianDTOS = new HashSet<>();
        Set<String> physicianSearchResults = new HashSet<>();
        Set<String> physicianErrorResults = new HashSet<>();
        Set<String> physicianDeltaResults = new HashSet<>();
        Set<String> physicianSearchDeltaResults = new HashSet<>();
        Set<String> physicianCompletedResults = new HashSet<>();

        genericSearch(doctorUrl, 10, physicianSearchResults, "doctors_search_results.json");

        //all the general pages x 10
        for (String url : physicianSearchResults) {
            if (!physicianCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#physician-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractMedicalDoctors(url, rows, physicianDTOS);
                    physicianCompletedResults.add(url);
                    physicianDeltaResults.addAll(extractExtraUrls(doc, physicianSearchResults, physicianCompletedResults, doctorUrl));
                    physicianSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    log.debug("physicianDTOS size: " + physicianDTOS.size());
                } catch (Exception e) {
                    log.error("Issue in connect to url: " + url + " with the generic pages");
                    physicianErrorResults.add(url);
                }
            }
        }

        log.debug("physicianDTOS size: " + physicianDTOS.size());
        log.debug("physicianSearchResults size: " + physicianSearchResults.size());
        log.debug("physicianDeltaResults size: " + physicianDeltaResults.size());
        log.debug("physicianSearchDeltaResults size: " + physicianSearchDeltaResults.size());
        log.debug("physicianCompletedResults size: " + physicianCompletedResults.size());
        log.debug("physicianErrorResults size: " + physicianErrorResults.size());

        for (String url : physicianSearchDeltaResults) {
            for (int i = 1; i <= 10; i++) {
                Document doc = null;
                try {
                    if (i == 1) {
                        WorkingProxyDTO randomProxy = getRandomProxy();
                        doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    } else {
                        WorkingProxyDTO randomProxy = getRandomProxy();
                        doc = Jsoup.connect(url + "?page=" + i).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    }
                    physicianSearchDeltaResults.addAll(extractExtraUrls(doc, physicianSearchResults, physicianCompletedResults, doctorUrl));
                    log.debug("physicianDTOS size: " + physicianDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url: " + url + "when looking in the delta search results");
                }

            }
        }

        log.debug("physicianDTOS size: " + physicianDTOS.size());
        log.debug("physicianSearchResults size: " + physicianSearchResults.size());
        log.debug("physicianDeltaResults size: " + physicianDeltaResults.size());
        log.debug("physicianSearchDeltaResults size: " + physicianSearchDeltaResults.size());
        log.debug("physicianCompletedResults size: " + physicianCompletedResults.size());
        log.debug("physicianErrorResults size: " + physicianErrorResults.size());

        for (String url : physicianDeltaResults) {
            if (!physicianCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#physician-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractMedicalDoctors(url, rows, physicianDTOS);
                    physicianCompletedResults.add(url);
                    log.debug("physicianDTOS size: " + physicianDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                    physicianErrorResults.add(url);
                }
            }
        }

        log.debug("physicianDTOS size: " + physicianDTOS.size());
        log.debug("physicianSearchResults size: " + physicianSearchResults.size());
        log.debug("physicianDeltaResults size: " + physicianDeltaResults.size());
        log.debug("physicianSearchDeltaResults size: " + physicianSearchDeltaResults.size());
        log.debug("physicianCompletedResults size: " + physicianCompletedResults.size());
        log.debug("physicianErrorResults size: " + physicianErrorResults.size());

        for (String url : physicianErrorResults) {
            if (!physicianCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#physician-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractMedicalDoctors(url, rows, physicianDTOS);
                    physicianCompletedResults.add(url);
                    log.debug("motorCarrierDTOS size: " + physicianDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                }
            }
        }

        log.debug("physicianDTOS size: " + physicianDTOS.size());
        log.debug("physicianSearchResults size: " + physicianSearchResults.size());
        log.debug("physicianDeltaResults size: " + physicianDeltaResults.size());
        log.debug("physicianSearchDeltaResults size: " + physicianSearchDeltaResults.size());
        log.debug("physicianCompletedResults size: " + physicianCompletedResults.size());
        log.debug("physicianErrorResults size: " + physicianErrorResults.size());

        File doctorsCarriersFile = new File("/root/IdeaProjects/importer/src/test/resources/database/doctors.json");
        objectMapper().writeValue(doctorsCarriersFile, physicianDTOS);

        File doctorErrorsFile = new File("/root/IdeaProjects/importer/src/test/resources/database/doctors_errors.json");
        objectMapper().writeValue(doctorErrorsFile, physicianErrorResults);

    }

    //TODO there is more medical doc information to add
    private void extractMedicalDoctors(String url, List<Element> trows, Set<PhysicianDTO> physicianDTOS) {

        PhysicianDTO physician = new PhysicianDTO();
        physician.setUrl(url);
        for (Element row : trows) {
            List<Element> variables = row.getElementsByTag("td");
            if (variables.get(0).text().equalsIgnoreCase("NPI")) {
                physician.setnPI(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("PAC ID")) {
                physician.setpACID(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Professional Enrollment ID")) {
                physician.setProfessionalEnrollmentID(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Full Name")) {
                physician.setFullName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Gender")) {
                physician.setGender(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Medical School")) {
                physician.setMedicalSchool(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Graduation Year")) {
                physician.setGraduationYear(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Primary Specialty")) {
                physician.setPrimarySpecialty(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Organization Legal Name")) {
                physician.setOrganizationLegalName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Group Practice PAC ID")) {
                physician.setGroupPracticePACID(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Number of Group Practice Members")) {
                physician.setNumberOfGroupPracticeMembers(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Address")) {
                physician.setAddress(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Telephone")) {
                physician.setTelephone(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Professional Accepts Medicare Assignment")) {
                physician.setProfessionalAcceptsMedicareAssignment(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Group Accepts Medicare Assignment")) {
                physician.setGroupAcceptsMedicareAssignment(variables.get(1).text());
            }

        }
        physicianDTOS.add(physician);
    }

    //    //WASHINGTON LAWYERS https://opengovus.com/washington-lawyer
//
    @Test
    public void lawyerSearch() throws InterruptedException, IOException {
        addWorkingProxies();
        String doctorUrl = "https://opengovus.com/washington-lawyer";

        Set<LawyerDTO> lawyerDTOS = new HashSet<>();
        Set<String> lawyerSearchResults = new HashSet<>();
        Set<String> lawyerErrorResults = new HashSet<>();
        Set<String> lawyerDeltaResults = new HashSet<>();
        Set<String> lawyerSearchDeltaResults = new HashSet<>();
        Set<String> lawyerCompletedResults = new HashSet<>();

        genericSearch(doctorUrl, 10, lawyerSearchResults, "lawyer_search_results.json");

        //all the general pages x 10
        for (String url : lawyerSearchResults) {
            if (!lawyerCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#lawyer-overview > div.panel-body > div > table:nth-child(2) > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractLawyer(url, rows, lawyerDTOS);
                    lawyerCompletedResults.add(url);
                    lawyerDeltaResults.addAll(extractExtraUrls(doc, lawyerSearchResults, lawyerCompletedResults, doctorUrl));
                    lawyerSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    log.debug("lawyerDTOS size: " + lawyerDTOS.size());
                } catch (Exception e) {
                    log.error("Issue in connect to url: " + url + " with the generic pages");
                    lawyerErrorResults.add(url);
                }
            }
        }

        log.debug("lawyerDTOS size: " + lawyerDTOS.size());
        log.debug("lawyerSearchResults size: " + lawyerSearchResults.size());
        log.debug("lawyerDeltaResults size: " + lawyerDeltaResults.size());
        log.debug("lawyerSearchDeltaResults size: " + lawyerSearchDeltaResults.size());
        log.debug("lawyerCompletedResults size: " + lawyerCompletedResults.size());
        log.debug("lawyerErrorResults size: " + lawyerErrorResults.size());

        for (String url : lawyerSearchResults) {
            for (int i = 1; i <= 10; i++) {
                Document doc = null;
                try {
                    if (i == 1) {
                        WorkingProxyDTO randomProxy = getRandomProxy();
                        doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    } else {
                        WorkingProxyDTO randomProxy = getRandomProxy();
                        doc = Jsoup.connect(url + "?page=" + i).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    }
                    Elements select = doc.select("#lawyer-overview > div.panel-body > div > table:nth-child(2) > tbody");
                    lawyerSearchDeltaResults.addAll(extractExtraUrls(doc, lawyerSearchResults, lawyerCompletedResults, doctorUrl));
                    log.debug("lawyerDTOS size: " + lawyerDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url: " + url + "when looking in the delta search results");
                }

            }
        }

        log.debug("lawyerDTOS size: " + lawyerDTOS.size());
        log.debug("lawyerSearchResults size: " + lawyerSearchResults.size());
        log.debug("lawyerDeltaResults size: " + lawyerDeltaResults.size());
        log.debug("lawyerSearchDeltaResults size: " + lawyerSearchDeltaResults.size());
        log.debug("lawyerCompletedResults size: " + lawyerCompletedResults.size());
        log.debug("lawyerErrorResults size: " + lawyerErrorResults.size());

        for (String url : lawyerDeltaResults) {
            if (!lawyerCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#lawyer-overview > div.panel-body > div > table:nth-child(2) > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractLawyer(url, rows, lawyerDTOS);
                    lawyerCompletedResults.add(url);
                    log.debug("physicianDTOS size: " + lawyerDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                    lawyerErrorResults.add(url);
                }
            }
        }

        log.debug("lawyerDTOS size: " + lawyerDTOS.size());
        log.debug("lawyerSearchResults size: " + lawyerSearchResults.size());
        log.debug("lawyerDeltaResults size: " + lawyerDeltaResults.size());
        log.debug("lawyerSearchDeltaResults size: " + lawyerSearchDeltaResults.size());
        log.debug("lawyerCompletedResults size: " + lawyerCompletedResults.size());
        log.debug("lawyerErrorResults size: " + lawyerErrorResults.size());

        for (String url : lawyerErrorResults) {
            if (!lawyerCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#lawyer-overview > div.panel-body > div > table:nth-child(2) > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractLawyer(url, rows, lawyerDTOS);
                    lawyerCompletedResults.add(url);
                    log.debug("lawyerDTOS size: " + lawyerDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                }
            }
        }

        log.debug("lawyerDTOS size: " + lawyerDTOS.size());
        log.debug("lawyerSearchResults size: " + lawyerSearchResults.size());
        log.debug("lawyerDeltaResults size: " + lawyerDeltaResults.size());
        log.debug("lawyerSearchDeltaResults size: " + lawyerSearchDeltaResults.size());
        log.debug("lawyerCompletedResults size: " + lawyerCompletedResults.size());
        log.debug("lawyerErrorResults size: " + lawyerErrorResults.size());

        File doctorsCarriersFile = new File("/root/IdeaProjects/importer/src/test/resources/database/lawyers.json");
        objectMapper().writeValue(doctorsCarriersFile, lawyerDTOS);

        File doctorErrorsFile = new File("/root/IdeaProjects/importer/src/test/resources/database/lawyers_errors.json");
        objectMapper().writeValue(doctorErrorsFile, lawyerErrorResults);

    }

    private void extractLawyer(String url, List<Element> trows, Set<LawyerDTO> lawyerDTOS) {

        LawyerDTO lawyer = new LawyerDTO();
        lawyer.setUrl(url);
        for (Element row : trows) {

            List<Element> variables = row.getElementsByTag("td");
            if (variables.get(0).text().equalsIgnoreCase("WSBA Number")) {
                lawyer.setwSBANumber(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Full Name")) {
                lawyer.setFullName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Last Name")) {
                lawyer.setLastName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("First Name")) {
                lawyer.setFirstName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Address")) {
                lawyer.setAddress(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Phone")) {
                lawyer.setPhone(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Email")) {
                lawyer.setEmail(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("License Type")) {
                lawyer.setLicenseType(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Admit Date")) {
                lawyer.setAdmitDate(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("License Status")) {
                lawyer.setLicenseStatus(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Eligible To Practice")) {
                lawyer.setEligibleToPractice(variables.get(1).text());
            }

        }
        lawyerDTOS.add(lawyer);
    }

//    //TEXAS REAL ESTATE LICENSES  https://opengovus.com/texas-real-estate-license
    @Test
    public void realEstateAgentSearch() throws InterruptedException, IOException {
        addWorkingProxies();
        String estateAgentUrl = "https://opengovus.com/texas-real-estate-license";

        Set<EstateAgentDTO> estateAgentDTOS = new HashSet<>();
        Set<String> estateAgentSearchResults = new HashSet<>();
        Set<String> estateAgentErrorResults = new HashSet<>();
        Set<String> estateAgentDeltaResults = new HashSet<>();
        Set<String> estateAgentSearchDeltaResults = new HashSet<>();
        Set<String> estateAgentCompletedResults = new HashSet<>();

        genericSearch(estateAgentUrl, 10, estateAgentSearchResults, "estateagents_search_results.json");

        //all the general pages x 10
        for (String url : estateAgentSearchResults) {
            if (!estateAgentCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#lawyer-overview > div.panel-body > div > table:nth-child(2) > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractRealEstate(url, rows, estateAgentDTOS);
                    estateAgentCompletedResults.add(url);
                    estateAgentDeltaResults.addAll(extractExtraUrls(doc, estateAgentSearchResults, estateAgentCompletedResults, estateAgentUrl));
                    estateAgentSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    log.debug("estateAgentDTOS size: " + estateAgentDTOS.size());
                } catch (Exception e) {
                    log.error("Issue in connect to url: " + url + " with the generic pages");
                    estateAgentErrorResults.add(url);
                }
            }
        }

        log.debug("estateAgentDTOS size: " + estateAgentDTOS.size());
        log.debug("estateAgentSearchResults size: " + estateAgentSearchResults.size());
        log.debug("estateAgentDeltaResults size: " + estateAgentDeltaResults.size());
        log.debug("estateAgentSearchDeltaResults size: " + estateAgentSearchDeltaResults.size());
        log.debug("estateAgentCompletedResults size: " + estateAgentCompletedResults.size());
        log.debug("estateAgentErrorResults size: " + estateAgentErrorResults.size());

        for (String url : estateAgentSearchResults) {
            for (int i = 1; i <= 10; i++) {
                Document doc = null;
                try {
                    if (i == 1) {
                        WorkingProxyDTO randomProxy = getRandomProxy();
                        doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    } else {
                        WorkingProxyDTO randomProxy = getRandomProxy();
                        doc = Jsoup.connect(url + "?page=" + i).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    }
                    estateAgentSearchDeltaResults.addAll(extractExtraUrls(doc, estateAgentSearchResults, estateAgentCompletedResults, estateAgentUrl));
                    log.debug("estateAgentDTOS size: " + estateAgentDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url: " + url + "when looking in the delta search results");
                }

            }
        }

        log.debug("estateAgentDTOS size: " + estateAgentDTOS.size());
        log.debug("estateAgentSearchResults size: " + estateAgentSearchResults.size());
        log.debug("estateAgentDeltaResults size: " + estateAgentDeltaResults.size());
        log.debug("estateAgentSearchDeltaResults size: " + estateAgentSearchDeltaResults.size());
        log.debug("estateAgentCompletedResults size: " + estateAgentCompletedResults.size());
        log.debug("estateAgentErrorResults size: " + estateAgentErrorResults.size());

        for (String url : estateAgentDeltaResults) {
            if (!estateAgentCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#lawyer-overview > div.panel-body > div > table:nth-child(2) > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractRealEstate(url, rows, estateAgentDTOS);
                    estateAgentCompletedResults.add(url);
                    log.debug("physicianDTOS size: " + estateAgentDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                    estateAgentErrorResults.add(url);
                }
            }
        }

        log.debug("estateAgentDTOS size: " + estateAgentDTOS.size());
        log.debug("estateAgentSearchResults size: " + estateAgentSearchResults.size());
        log.debug("estateAgentDeltaResults size: " + estateAgentDeltaResults.size());
        log.debug("estateAgentSearchDeltaResults size: " + estateAgentSearchDeltaResults.size());
        log.debug("estateAgentCompletedResults size: " + estateAgentCompletedResults.size());
        log.debug("estateAgentErrorResults size: " + estateAgentErrorResults.size());

        for (String url : estateAgentErrorResults) {
            if (!estateAgentCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#lawyer-overview > div.panel-body > div > table:nth-child(2) > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractRealEstate(url, rows, estateAgentDTOS);
                    estateAgentCompletedResults.add(url);
                    log.debug("lawyerDTOS size: " + estateAgentDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                }
            }
        }

        log.debug("estateAgentDTOS size: " + estateAgentDTOS.size());
        log.debug("estateAgentSearchResults size: " + estateAgentSearchResults.size());
        log.debug("estateAgentDeltaResults size: " + estateAgentDeltaResults.size());
        log.debug("estateAgentSearchDeltaResults size: " + estateAgentSearchDeltaResults.size());
        log.debug("estateAgentCompletedResults size: " + estateAgentCompletedResults.size());
        log.debug("estateAgentErrorResults size: " + estateAgentErrorResults.size());

        File estateAgentCarriersFile = new File("/root/IdeaProjects/importer/src/test/resources/database/estateagents.json");
        objectMapper().writeValue(estateAgentCarriersFile, estateAgentDTOS);

        File estateAgentErrorsFile = new File("/root/IdeaProjects/importer/src/test/resources/database/estateagents_errors.json");
        objectMapper().writeValue(estateAgentErrorsFile, estateAgentErrorResults);

    }

    private void extractRealEstate(String url, List<Element> trows, Set<EstateAgentDTO> estateAgentDTOS){

        EstateAgentDTO estateAgent = new EstateAgentDTO();
        estateAgent.setUrl(url);
        for(Element row: trows){
                List<Element> variables = row.getElementsByTag("td");
                if(variables.get(0).text().equalsIgnoreCase("License Number")){
                    estateAgent.setLicenseNumber(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("Licensee Name")){
                    estateAgent.setLicenseeName(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("License Type")){
                    estateAgent.setLicenseType(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("Mailing Address")){
                    estateAgent.setMailingAddress(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("Telephone")){
                    estateAgent.setTelephone(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("Email")){
                    estateAgent.setEmail(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("License Status")){
                    estateAgent.setLicenseStatus(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("Original License Date")){
                    estateAgent.setOriginalLicenseDate(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("Expiration Date")){
                    estateAgent.setExpirationDate(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("Education Status")){
                    estateAgent.setEducationStatus(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("MCE Status")){
                    estateAgent.setmCEStatus(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("Designated Supervisor")){
                    estateAgent.setDesignatedSupervisor(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("Related License Number")){
                    estateAgent.setRelatedLicenseNumber(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("Related License Type")){
                    estateAgent.setRelatedLicenseType(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("Related License Start Date")){
                    estateAgent.setRelatedLicenseStartDate(variables.get(1).text());
                }
                if(variables.get(0).text().equalsIgnoreCase("Agency Identifier")){
                    estateAgent.setAgencyIdentifier(variables.get(1).text());
                }

        }
        estateAgentDTOS.add(estateAgent);
    }

    @Test
    public void corporateSearch() throws InterruptedException, IOException {
        addWorkingProxies();
        String corporateUrl = "https://opengovus.com/washington-corporation";

        Set<CorporationDTO> corporateDTOS = new HashSet<>();
        Set<String> corporateSearchResults = new HashSet<>();
        Set<String> corporateErrorResults = new HashSet<>();
        Set<String> corporateDeltaResults = new HashSet<>();
        Set<String> corporateSearchDeltaResults = new HashSet<>();
        Set<String> corporateCompletedResults = new HashSet<>();

        genericSearch(corporateUrl, 10, corporateSearchResults, "corporates_search_results.json");

        //all the general pages x 10
        for (String url : corporateSearchResults) {
            if (!corporateCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#entity-overview > div.panel-body > div > table:nth-child(3) > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractCorporate(url, rows, corporateDTOS);
                    corporateCompletedResults.add(url);
                    corporateDeltaResults.addAll(extractExtraUrls(doc, corporateSearchResults, corporateCompletedResults, corporateUrl));
                    corporateSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    log.debug("corporateDTOS size: " + corporateDTOS.size());
                } catch (Exception e) {
                    log.error("Issue in connect to url: " + url + " with the generic pages");
                    corporateErrorResults.add(url);
                }
            }
        }

        log.debug("corporateDTOS size: " + corporateDTOS.size());
        log.debug("corporateSearchResults size: " + corporateSearchResults.size());
        log.debug("corporateDeltaResults size: " + corporateDeltaResults.size());
        log.debug("corporateSearchDeltaResults size: " + corporateSearchDeltaResults.size());
        log.debug("corporateCompletedResults size: " + corporateCompletedResults.size());
        log.debug("corporateErrorResults size: " + corporateErrorResults.size());

        for (String url : corporateSearchResults) {
            for (int i = 1; i <= 10; i++) {
                Document doc = null;
                try {
                    if (i == 1) {
                        WorkingProxyDTO randomProxy = getRandomProxy();
                        doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    } else {
                        WorkingProxyDTO randomProxy = getRandomProxy();
                        doc = Jsoup.connect(url + "?page=" + i).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    }
                    corporateSearchDeltaResults.addAll(extractExtraUrls(doc, corporateSearchResults, corporateCompletedResults, corporateUrl));
                    log.debug("corporateDTOS size: " + corporateDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url: " + url + "when looking in the delta search results");
                }

            }
        }

        log.debug("corporateDTOS size: " + corporateDTOS.size());
        log.debug("corporateSearchResults size: " + corporateSearchResults.size());
        log.debug("corporateDeltaResults size: " + corporateDeltaResults.size());
        log.debug("corporateSearchDeltaResults size: " + corporateSearchDeltaResults.size());
        log.debug("corporateCompletedResults size: " + corporateCompletedResults.size());
        log.debug("corporateErrorResults size: " + corporateErrorResults.size());

        for (String url : corporateDeltaResults) {
            if (!corporateCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#entity-overview > div.panel-body > div > table:nth-child(3) > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractCorporate(url, rows, corporateDTOS);
                    corporateCompletedResults.add(url);
                    log.debug("physicianDTOS size: " + corporateDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                    corporateErrorResults.add(url);
                }
            }
        }

        log.debug("corporateDTOS size: " + corporateDTOS.size());
        log.debug("corporateSearchResults size: " + corporateSearchResults.size());
        log.debug("corporateDeltaResults size: " + corporateDeltaResults.size());
        log.debug("corporateSearchDeltaResults size: " + corporateSearchDeltaResults.size());
        log.debug("corporateCompletedResults size: " + corporateCompletedResults.size());
        log.debug("corporateErrorResults size: " + corporateErrorResults.size());

        for (String url : corporateErrorResults) {
            if (!corporateCompletedResults.contains(url)) {
                log.trace("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#entity-overview > div.panel-body > div > table:nth-child(3) > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    extractCorporate(url, rows, corporateDTOS);
                    corporateCompletedResults.add(url);
                    log.debug("corporateDTOS size: " + corporateDTOS.size());
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                }
            }
        }

        log.debug("corporateDTOS size: " + corporateDTOS.size());
        log.debug("corporateSearchResults size: " + corporateSearchResults.size());
        log.debug("corporateDeltaResults size: " + corporateDeltaResults.size());
        log.debug("corporateSearchDeltaResults size: " + corporateSearchDeltaResults.size());
        log.debug("corporateCompletedResults size: " + corporateCompletedResults.size());
        log.debug("corporateErrorResults size: " + corporateErrorResults.size());

        File corporateFile = new File("/root/IdeaProjects/importer/src/test/resources/database/corporates.json");
        objectMapper().writeValue(corporateFile, corporateDTOS);

        File corporateErrorsFile = new File("/root/IdeaProjects/importer/src/test/resources/database/corporates.json");
        objectMapper().writeValue(corporateErrorsFile, corporateErrorResults);

    }

    //
    private void extractCorporate(String url, List<Element> trows, Set<CorporationDTO> corporationDTOS){

        CorporationDTO corporate = new CorporationDTO();
        corporate.setUrl(url);
        for(Element row: trows){
            List<Element> variables = row.getElementsByTag("td");
            if(variables.get(0).text().equalsIgnoreCase("UBI")){
                corporate.setUBI(variables.get(1).text());
            }
            if(variables.get(0).text().equalsIgnoreCase("Business Name")){
                corporate.setBusinessName(variables.get(1).text());
            }
            if(variables.get(0).text().equalsIgnoreCase("Physical Address")){
                corporate.setPhysicalAddress(variables.get(1).text());
            }
            if(variables.get(0).text().equalsIgnoreCase("Type")){
                corporate.setType(variables.get(1).text());
            }
            if(variables.get(0).text().equalsIgnoreCase("Category")){
                corporate.setCategory(variables.get(1).text());
            }
            if(variables.get(0).text().equalsIgnoreCase("Record Status")){
                corporate.setRecordStatus(variables.get(1).text());
            }
            if(variables.get(0).text().equalsIgnoreCase("Incorporation State")){
                corporate.setIncorporationState(variables.get(1).text());
            }
            if(variables.get(0).text().equalsIgnoreCase("Incorporation Date")){
                corporate.setIncorporationDate(variables.get(1).text());
            }
            if(variables.get(0).text().equalsIgnoreCase("Expiration Date")){
                corporate.setExpirationDate(variables.get(1).text());
            }
            if(variables.get(0).text().equalsIgnoreCase("Duration")){
                corporate.setDuration(variables.get(1).text());
            }
            if(variables.get(0).text().equalsIgnoreCase("Email")){
                corporate.setEmail(variables.get(1).text());
            }

        }
        corporationDTOS.add(corporate);
    }

}


