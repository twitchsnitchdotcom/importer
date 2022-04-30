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

public class Lawyers {

    private final static Logger log = LoggerFactory.getLogger(Lawyers.class);
    private static RestTemplate restTemplate = new RestTemplate();

    static Set<WorkingProxyDTO> workingProxies = new HashSet<>();

    //private String fileRoot = "/root/IdeaProjects/importer/src/test/resources/database/";
    private static String fileRoot = "/Users/horizondeep/Desktop/importer/src/test/resources/database/";


    public Lawyers() throws IOException {
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


    public static  void genericSearch(String url, int pageSize, Set<String> searchResults, String fileName) throws IOException {
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

    private static  Set<String> extractExtraSearchUrls(Document doc, String urlKey) {
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

    public static void main(String[] args)  throws InterruptedException, IOException {

        File completedfile = new File(fileRoot + "lawyers_completed_results.json");
        File resultsfile = new File(fileRoot + "lawyers.json");

        FileWriter completedFileWriter = new FileWriter(completedfile, true);
        FileWriter resultsFileWriter = new FileWriter(resultsfile, true);
        SequenceWriter completedSeqWriter = objectMapper().writer().writeValuesAsArray(completedFileWriter);
        SequenceWriter resultsSeqWriter = objectMapper().writer().writeValuesAsArray(resultsFileWriter);

        addWorkingProxies();
        String doctorUrl = "https://opengovus.com/washington-lawyer";

        Set<String> lawyerSearchResults = new HashSet<>();
        Set<String> lawyerErrorResults = new HashSet<>();
        Set<String> lawyerDeltaResults = new HashSet<>();
        Set<String> lawyerSearchDeltaResults = new HashSet<>();
        Set<String> lawyerCompletedResults = new HashSet<>();

        File lawyerSearchFile = new File(fileRoot + "lawyer_search_results.json");
        lawyerSearchResults =  objectMapper().readValue(lawyerSearchFile, new TypeReference<Set<String>>() {});

        if(lawyerSearchResults.size() == 0){
            genericSearch(doctorUrl, 10, lawyerSearchResults, "lawyers_search_results.json");
        }

        File lawyerSearchFileCompletedFile = new File(fileRoot + "lawyers_completed_results.json");
        lawyerCompletedResults =  objectMapper().readValue(lawyerSearchFileCompletedFile, new TypeReference<Set<String>>() {});


        //all the general pages x 10
        for (String url : lawyerSearchResults) {
            if (!lawyerCompletedResults.contains(url)) {
                log.debug("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#lawyer-overview > div.panel-body > div > table:nth-child(2) > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    resultsSeqWriter.write(extractLawyer(url, rows));
                    completedSeqWriter.write(url);
                    lawyerDeltaResults.addAll(extractExtraUrls(doc, lawyerSearchResults, lawyerCompletedResults, doctorUrl));
                    lawyerSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                } catch (Exception e) {
                    log.error("Issue in connect to url: " + url + " with the generic pages");
                    lawyerErrorResults.add(url);
                }
            }
        }

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
                } catch (Exception e) {
                    log.error("Couldnt connect to url: " + url + "when looking in the delta search results");
                }

            }
        }

        log.debug("lawyerSearchResults size: " + lawyerSearchResults.size());
        log.debug("lawyerDeltaResults size: " + lawyerDeltaResults.size());
        log.debug("lawyerSearchDeltaResults size: " + lawyerSearchDeltaResults.size());
        log.debug("lawyerCompletedResults size: " + lawyerCompletedResults.size());
        log.debug("lawyerErrorResults size: " + lawyerErrorResults.size());

        for (String url : lawyerDeltaResults) {
            if (!lawyerCompletedResults.contains(url)) {
                log.debug("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#lawyer-overview > div.panel-body > div > table:nth-child(2) > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    resultsSeqWriter.write(extractLawyer(url, rows));
                    completedSeqWriter.write(url);
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                    lawyerErrorResults.add(url);
                }
            }
        }

        log.debug("lawyerSearchResults size: " + lawyerSearchResults.size());
        log.debug("lawyerDeltaResults size: " + lawyerDeltaResults.size());
        log.debug("lawyerSearchDeltaResults size: " + lawyerSearchDeltaResults.size());
        log.debug("lawyerCompletedResults size: " + lawyerCompletedResults.size());
        log.debug("lawyerErrorResults size: " + lawyerErrorResults.size());

        for (String url : lawyerErrorResults) {
            if (!lawyerCompletedResults.contains(url)) {
                log.debug("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#lawyer-overview > div.panel-body > div > table:nth-child(2) > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    resultsSeqWriter.write(extractLawyer(url, rows));
                    completedSeqWriter.write(url);
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                }
            }
        }

        log.debug("lawyerSearchResults size: " + lawyerSearchResults.size());
        log.debug("lawyerDeltaResults size: " + lawyerDeltaResults.size());
        log.debug("lawyerSearchDeltaResults size: " + lawyerSearchDeltaResults.size());
        log.debug("lawyerCompletedResults size: " + lawyerCompletedResults.size());
        log.debug("lawyerErrorResults size: " + lawyerErrorResults.size());

        resultsSeqWriter.close();
        completedSeqWriter.close();


        File doctorErrorsFile = new File(fileRoot + "lawyers_errors.json");
        objectMapper().writeValue(doctorErrorsFile, lawyerErrorResults);



    }

    private static LawyerDTO extractLawyer(String url, List<Element> trows) {

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
        return lawyer;
    }

}


