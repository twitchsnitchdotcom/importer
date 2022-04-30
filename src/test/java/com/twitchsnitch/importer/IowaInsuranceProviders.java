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
import java.util.*;

public class IowaInsuranceProviders {

    private final static Logger log = LoggerFactory.getLogger(IowaInsuranceProviders.class);
    private static RestTemplate restTemplate = new RestTemplate();

    static Set<WorkingProxyDTO> workingProxies = new HashSet<>();

    private static String fileRoot = "/root/IdeaProjects/importer/src/test/resources/database/";
    //private static String fileRoot = "/Users/horizondeep/Desktop/importer/src/test/resources/database/";


    public IowaInsuranceProviders() throws IOException {
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


    public static void main(String[] args) throws IOException, URISyntaxException {

        File completedfile = new File(fileRoot + "insurers_completed_results.json");
        File resultsfile = new File(fileRoot + "insurers.json");
        File errorsfile = new File(fileRoot + "insurers_errors.json");

        FileWriter completedFileWriter = new FileWriter(completedfile, true);
        FileWriter resultsFileWriter = new FileWriter(resultsfile, true);
        FileWriter errorsFileWriter = new FileWriter(errorsfile, true);

        SequenceWriter completedSeqWriter = objectMapper().writer().writeValuesAsArray(completedFileWriter);
        SequenceWriter resultsSeqWriter = objectMapper().writer().writeValuesAsArray(resultsFileWriter);
        SequenceWriter errorsSeqWriter = objectMapper().writer().writeValuesAsArray(errorsFileWriter);

        addWorkingProxies();
        int insurancePageSize = 1000;
        String iowaInsuranceURL = "https://opengovus.com/iowa-insurance-producer";

        Set<String> insuranceSearchResults = new HashSet<>();
        Set<String> insuranceErrorResults = new HashSet<>();
        Set<String> insuranceDeltaResults = new HashSet<>();
        Set<String> insuranceSearchDeltaResults = new HashSet<>();
        Set<String> insuranceCompletedResults = new HashSet<>();

        File insurersSearchFile = new File(fileRoot + "insurers_search_results.json");
        insuranceSearchResults =  objectMapper().readValue(insurersSearchFile, new TypeReference<Set<String>>() {});
        if(insuranceSearchResults.size() == 0){
            genericSearch(iowaInsuranceURL, insurancePageSize, insuranceSearchResults, "insurers_search_results.json");
        }

        try{
            insuranceCompletedResults =  objectMapper().readValue(completedfile, new TypeReference<Set<String>>() {});
        }
        catch(Exception e){
            log.error("insuranceCompletedResults file is empty");
        }

        try{
            insuranceErrorResults =  objectMapper().readValue(errorsfile, new TypeReference<Set<String>>() {});
        }
        catch(Exception e){
            log.error("insuranceErrorResults file is empty");
        }

        //all the general pages
        int i = 0;
        for (String url : insuranceSearchResults) {
            if (!insuranceCompletedResults.contains(url)) {
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    InsuranceProviderDTO insuranceProviderDTO = extractInsuranceProvider(url, rows);
                    resultsSeqWriter.write(insuranceProviderDTO);
                    completedSeqWriter.write(url);
                    insuranceDeltaResults.addAll(extractExtraUrls(doc, insuranceSearchResults, insuranceCompletedResults, iowaInsuranceURL));
                    insuranceSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    i++;
                } catch (Exception e) {
                    log.error("Issue adding all the page: " + url);
                    log.debug("Total records processed so far : " + i);
                    log.debug("Total errors so far : " + insuranceErrorResults.size());
                    errorsSeqWriter.write(url);
                    insuranceErrorResults.add(url);
                }
            }
        }

        int j = 0;
        for (String url : insuranceErrorResults) {
            if (!insuranceCompletedResults.contains(url)) {
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    InsuranceProviderDTO insuranceProviderDTO = extractInsuranceProvider(url, rows);
                    resultsSeqWriter.write(insuranceProviderDTO);
                    completedSeqWriter.write(url);
                    insuranceDeltaResults.addAll(extractExtraUrls(doc, insuranceSearchResults, insuranceCompletedResults, iowaInsuranceURL));
                    insuranceSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    j++;
                } catch (Exception e) {
                    log.error("Issue adding all the error url : " + url);
                    log.debug("Total error records processed so far : " + j);
                }
            }
        }

        resultsSeqWriter.close();
        completedSeqWriter.close();
        errorsSeqWriter.close();

        log.debug("insuranceSearchResults size: " + insuranceSearchResults.size());
        log.debug("insuranceSearchDeltaResults size: " + insuranceSearchDeltaResults.size());
        log.debug("insuranceCompletedResults size: " + insuranceCompletedResults.size());
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

    //INDIVIDUAL IOWA SEARCH

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

    private static InsuranceProviderDTO extractInsuranceProvider(String url, List<Element> trows) {

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
        return insuranceProviderDTO;
    }

}


