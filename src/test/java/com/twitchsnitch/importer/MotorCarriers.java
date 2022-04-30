package com.twitchsnitch.importer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MotorCarriers {

    private final static Logger log = LoggerFactory.getLogger(MotorCarriers.class);
    private static RestTemplate restTemplate = new RestTemplate();

    static Set<WorkingProxyDTO> workingProxies = new HashSet<>();

    //private String fileRoot = "/root/IdeaProjects/importer/src/test/resources/database/";
    private static String fileRoot = "/Users/horizondeep/Desktop/importer/src/test/resources/database/";


    public MotorCarriers() throws IOException {
    }

    public static final ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    static <E> E getRandomSetElement(Set<E> set) {
        return set.stream().skip(new Random().nextInt(set.size())).findFirst().orElse(null);
    }

    public static  WorkingProxyDTO getRandomProxy() {
        return getRandomSetElement(workingProxies);
    }


    public static void addWorkingProxies(){
        workingProxies.add(new WorkingProxyDTO("134.209.168.113", 3128));
        workingProxies.add(new WorkingProxyDTO("134.209.175.142", 3128));
        workingProxies.add(new WorkingProxyDTO("134.209.39.197", 3128));
        workingProxies.add(new WorkingProxyDTO("159.65.41.46", 3128));
        workingProxies.add(new WorkingProxyDTO("68.183.159.153", 3128));
        workingProxies.add(new WorkingProxyDTO("167.99.123.172", 3128));
        workingProxies.add(new WorkingProxyDTO("104.248.13.219", 3128));
        workingProxies.add(new WorkingProxyDTO("104.248.5.67", 3128));
        workingProxies.add(new WorkingProxyDTO("104.248.5.67", 3128));
        workingProxies.add(new WorkingProxyDTO("167.99.123.202", 3128));

    }


    public static void genericSearch(String url, int pageSize, Set<String> searchResults, String fileName) throws IOException {
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
        File insurersFile = new File(fileRoot  + fileName);
        objectMapper().writeValue(insurersFile, searchResults);

    }

    private static Set<String> extractExtraSearchUrls(Document doc, String urlKey) {
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

    private static Set<String> extractExtraUrls(Document doc, Set<String> searchResults, Set<String> completedResults, String urlKey) {
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

    public static void main(String[] args) throws InterruptedException, IOException {

        File completedfile = new File(fileRoot + "motorcarriers_completed_results.json");
        File resultsfile = new File(fileRoot + "motorcarriers.json");

        FileWriter completedFileWriter = new FileWriter(completedfile, true);
        FileWriter resultsFileWriter = new FileWriter(resultsfile, true);
        SequenceWriter completedSeqWriter = objectMapper().writer().writeValuesAsArray(completedFileWriter);
        SequenceWriter resultsSeqWriter = objectMapper().writer().writeValuesAsArray(resultsFileWriter);

        addWorkingProxies();
        String motorCarrierUrl = "https://opengovus.com/motor-carrier";

        Set<String> motorCarriersSearchResults = new HashSet<>();
        Set<String> motorCarriersDeltaResults = new HashSet<>();
        Set<String> motorCarrierErrorResults = new HashSet<>();
        Set<String> motorCarriersSearchDeltaResults = new HashSet<>();
        Set<String> motorCarriersCompletedResults = new HashSet<>();

        File motorCarriersSearchFile = new File(fileRoot + "motorcarriers_search_results.json");
        motorCarriersSearchResults =  objectMapper().readValue(motorCarriersSearchFile, new TypeReference<Set<String>>() {});
        if(motorCarriersSearchResults.size() == 0){
            genericSearch(motorCarrierUrl, 10, motorCarriersSearchResults, "motorcarriers_search_results.json");
        }

        File motorCarriersCompletedFile = new File(fileRoot + "insurers_completed_results.json");
        motorCarriersCompletedResults =  objectMapper().readValue(motorCarriersCompletedFile, new TypeReference<Set<String>>() {});


        //all the general pages x 10
        for (String url : motorCarriersSearchResults) {
            if (!motorCarriersCompletedResults.contains(url)) {
                log.debug("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#entity-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    resultsSeqWriter.write(extractMotorCarrier(url, rows));
                    completedSeqWriter.write(url);
                    motorCarriersDeltaResults.addAll(extractExtraUrls(doc, motorCarriersSearchResults, motorCarriersCompletedResults, motorCarrierUrl));
                    motorCarriersSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                } catch (Exception e) {
                    log.error("Issue in connect to url: " + url + " with the generic pages");
                    motorCarrierErrorResults.add(url);
                }
            }
        }

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
                } catch (Exception e) {
                    log.error("Couldnt connect to url: " + url + "when looking in the delta search results");
                }

            }
        }

        log.debug("motorCarriersSearchResults size: " + motorCarriersSearchResults.size());
        log.debug("motorCarriersDeltaResults size: " + motorCarriersDeltaResults.size());
        log.debug("motorCarriersSearchDeltaResults size: " + motorCarriersSearchDeltaResults.size());
        log.debug("motorCarriersCompletedResults size: " + motorCarriersCompletedResults.size());
        log.debug("motorCarrierErrorResults size: " + motorCarrierErrorResults.size());

        for (String url : motorCarriersDeltaResults) {
            if (!motorCarriersCompletedResults.contains(url)) {
                log.debug("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#entity-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    resultsSeqWriter.write(extractMotorCarrier(url, rows));
                    completedSeqWriter.write(url);
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                    motorCarrierErrorResults.add(url);
                }
            }
        }

        for (String url : motorCarrierErrorResults) {
            if (!motorCarriersCompletedResults.contains(url)) {
                log.debug("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#entity-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    resultsSeqWriter.write(extractMotorCarrier(url, rows));
                    completedSeqWriter.write(url);
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                }
            }
        }

        log.debug("motorCarriersSearchResults size: " + motorCarriersSearchResults.size());
        log.debug("motorCarriersDeltaResults size: " + motorCarriersDeltaResults.size());
        log.debug("motorCarriersSearchDeltaResults size: " + motorCarriersSearchDeltaResults.size());
        log.debug("motorCarriersCompletedResults size: " + motorCarriersCompletedResults.size());
        log.debug("motorCarrierErrorResults size: " + motorCarrierErrorResults.size());

        File motorCarriersErrorsFile = new File(fileRoot + "motorcarriers_errors.json");
        objectMapper().writeValue(motorCarriersErrorsFile, motorCarrierErrorResults);

        resultsSeqWriter.close();
        completedSeqWriter.close();

    }

    private static MotorCarrierDTO extractMotorCarrier(String url, List<Element> trows) {

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
        return motorCarrier;
    }



}


