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

public class Doctors {

    private final static Logger log = LoggerFactory.getLogger(Doctors.class);
    private static RestTemplate restTemplate = new RestTemplate();

    static Set<WorkingProxyDTO> workingProxies = new HashSet<>();

    //private String fileRoot = "/root/IdeaProjects/importer/src/test/resources/database/";
    private static String fileRoot = "/Users/horizondeep/Desktop/importer/src/test/resources/database/";


    public Doctors() throws IOException {
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


    public static void main(String[] args) throws InterruptedException, IOException {

        File completedfile = new File(fileRoot + "doctors_completed_results.json");
        File resultsfile = new File(fileRoot + "doctors.json");

        FileWriter completedFileWriter = new FileWriter(completedfile, true);
        FileWriter resultsFileWriter = new FileWriter(resultsfile, true);
        SequenceWriter completedSeqWriter = objectMapper().writer().writeValuesAsArray(completedFileWriter);
        SequenceWriter resultsSeqWriter = objectMapper().writer().writeValuesAsArray(resultsFileWriter);

        addWorkingProxies();
        String doctorUrl = "https://opengovus.com/physician";

        Set<String> physicianSearchResults = new HashSet<>();
        Set<String> physicianErrorResults = new HashSet<>();
        Set<String> physicianDeltaResults = new HashSet<>();
        Set<String> physicianSearchDeltaResults = new HashSet<>();
        Set<String> physicianCompletedResults = new HashSet<>();

        File physicianSearchFile = new File(fileRoot + "doctors_search_results.json");
        physicianSearchResults =  objectMapper().readValue(physicianSearchFile, new TypeReference<Set<String>>() {});
        if(physicianSearchResults.size() == 0){
            genericSearch(doctorUrl, 10, physicianSearchResults, "doctors_search_results.json");
        }

        File physicianSearchFileCompletedFile = new File(fileRoot + "doctors_completed_results.json");
        physicianCompletedResults =  objectMapper().readValue(physicianSearchFileCompletedFile, new TypeReference<Set<String>>() {});


        //all the general pages x 10
        for (String url : physicianSearchResults) {
            if (!physicianCompletedResults.contains(url)) {
                log.debug("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#physician-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    resultsSeqWriter.write(extractMedicalDoctors(url, rows));
                    completedSeqWriter.write(url);
                    physicianDeltaResults.addAll(extractExtraUrls(doc, physicianSearchResults, physicianCompletedResults, doctorUrl));
                    physicianSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                } catch (Exception e) {
                    log.error("Issue in connect to url: " + url + " with the generic pages");
                    physicianErrorResults.add(url);
                }
            }
        }

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
                } catch (Exception e) {
                    log.error("Couldnt connect to url: " + url + "when looking in the delta search results");
                }

            }
        }

        log.debug("physicianSearchResults size: " + physicianSearchResults.size());
        log.debug("physicianDeltaResults size: " + physicianDeltaResults.size());
        log.debug("physicianSearchDeltaResults size: " + physicianSearchDeltaResults.size());
        log.debug("physicianCompletedResults size: " + physicianCompletedResults.size());
        log.debug("physicianErrorResults size: " + physicianErrorResults.size());

        for (String url : physicianDeltaResults) {
            if (!physicianCompletedResults.contains(url)) {
                log.debug("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#physician-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    resultsSeqWriter.write(extractMedicalDoctors(url, rows));
                    completedSeqWriter.write(url);
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                    physicianErrorResults.add(url);
                }
            }
        }

        log.debug("physicianSearchResults size: " + physicianSearchResults.size());
        log.debug("physicianDeltaResults size: " + physicianDeltaResults.size());
        log.debug("physicianSearchDeltaResults size: " + physicianSearchDeltaResults.size());
        log.debug("physicianCompletedResults size: " + physicianCompletedResults.size());
        log.debug("physicianErrorResults size: " + physicianErrorResults.size());

        for (String url : physicianErrorResults) {
            if (!physicianCompletedResults.contains(url)) {
                log.debug("Completed list does not contain url, fetching it: " + url);
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#physician-overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    resultsSeqWriter.write(extractMedicalDoctors(url, rows));
                    completedSeqWriter.write(url);
                } catch (Exception e) {
                    log.error("Couldnt connect to url in the final collection loop: " + url);
                }
            }
        }

        log.debug("physicianSearchResults size: " + physicianSearchResults.size());
        log.debug("physicianDeltaResults size: " + physicianDeltaResults.size());
        log.debug("physicianSearchDeltaResults size: " + physicianSearchDeltaResults.size());
        log.debug("physicianCompletedResults size: " + physicianCompletedResults.size());
        log.debug("physicianErrorResults size: " + physicianErrorResults.size());

        resultsSeqWriter.close();
        completedSeqWriter.close();

        File doctorErrorsFile = new File(fileRoot + "doctors_errors.json");
        objectMapper().writeValue(doctorErrorsFile, physicianErrorResults);

    }

    private static PhysicianDTO extractMedicalDoctors(String url, List<Element> trows) {

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
        return physician;
    }

}


