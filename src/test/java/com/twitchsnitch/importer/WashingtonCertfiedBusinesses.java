package com.twitchsnitch.importer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.LobbyistDTO;
import com.twitchsnitch.importer.dto.WashingtonCertifiedDTO;
import com.twitchsnitch.importer.dto.WorkingProxyDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

public class WashingtonCertfiedBusinesses {

    private final static Logger log = LoggerFactory.getLogger(WashingtonCertfiedBusinesses.class);
    private static RestTemplate restTemplate = new RestTemplate();

    static Set<WorkingProxyDTO> workingProxies = new HashSet<>();

    private static String fileRoot = "/home/jamie/IdeaProjects/importer/src/test/resources/database/";
    //private static String fileRoot = "/Users/horizondeep/Desktop/importer/src/test/resources/database/";


    public WashingtonCertfiedBusinesses() throws IOException {
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
        workingProxies.add(new WorkingProxyDTO("138.197.1.206", 3128));
        workingProxies.add(new WorkingProxyDTO("209.97.148.204", 3128));
        workingProxies.add(new WorkingProxyDTO("167.71.172.166", 3128));
        workingProxies.add(new WorkingProxyDTO("159.89.41.55", 3128));
        workingProxies.add(new WorkingProxyDTO("167.172.224.130", 3128));
        workingProxies.add(new WorkingProxyDTO("167.172.28.201", 3128));
    }


    public static void main(String[] args) throws IOException, URISyntaxException {

        File completedfile = new File(fileRoot + "washington_certified_businesses_completed_results.json");
        File resultsfile = new File(fileRoot + "washington_certified_businesses.json");
        File errorsfile = new File(fileRoot + "washington_certified_businesses_errors.json");

        FileWriter completedFileWriter = new FileWriter(completedfile, true);
        FileWriter resultsFileWriter = new FileWriter(resultsfile, true);
        FileWriter errorsFileWriter = new FileWriter(errorsfile, true);

        SequenceWriter completedSeqWriter = objectMapper().writer().writeValuesAsArray(completedFileWriter);
        SequenceWriter resultsSeqWriter = objectMapper().writer().writeValuesAsArray(resultsFileWriter);
        SequenceWriter errorsSeqWriter = objectMapper().writer().writeValuesAsArray(errorsFileWriter);

        addWorkingProxies();
        int lobbyistsPageSize = 20;
        String iowaInsuranceURL = "https://opengovus.com/washington-dc-certified-business";

        Set<String> wcdSearchResults = new HashSet<>();
        Set<String> wcdErrorResults = new HashSet<>();
        Set<String> wcdDeltaResults = new HashSet<>();
        Set<String> wcdSearchDeltaResults = new HashSet<>();
        Set<String> wcdCompletedResults = new HashSet<>();

        try{
            File lobbyistsSearchFile = new File(fileRoot + "washington_certified_businesses_search_results.json");
            wcdSearchResults =  objectMapper().readValue(lobbyistsSearchFile, new TypeReference<Set<String>>() {});
            if(wcdSearchResults.size() == 0){
                genericSearch(iowaInsuranceURL, lobbyistsPageSize, wcdSearchResults, "washington_certified_businesses_search_results.json");
            }
        }
        catch(Exception e){
            log.error("wcdCompletedResults file is empty");
            genericSearch(iowaInsuranceURL, lobbyistsPageSize, wcdSearchResults, "washington_certified_businesses_search_results.json");
        }


        try{
            wcdCompletedResults =  objectMapper().readValue(completedfile, new TypeReference<Set<String>>() {});
        }
        catch(Exception e){
            log.error("wcdCompletedResults file is empty");
        }

        try{
            wcdErrorResults =  objectMapper().readValue(errorsfile, new TypeReference<Set<String>>() {});
        }
        catch(Exception e){
            log.error("wcdErrorResults file is empty");
        }

        //all the general pages
        int i = 0;
        for (String url : wcdSearchResults) {
            if (!wcdCompletedResults.contains(url)) {
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    WashingtonCertifiedDTO washingtonCertifiedDTO = extractBusiness(url, rows);
                    resultsSeqWriter.write(washingtonCertifiedDTO);
                    completedSeqWriter.write(url);
                    wcdDeltaResults.addAll(extractExtraUrls(doc, wcdSearchResults, wcdCompletedResults, iowaInsuranceURL));
                    wcdSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    i++;
                } catch (Exception e) {
                    log.error("Issue adding all the page: " + url);
                    log.debug("Total records processed so far : " + i);
                    log.debug("Total errors so far : " + wcdErrorResults.size());
                    errorsSeqWriter.write(url);
                    wcdErrorResults.add(url);
                }
            }
        }

        int j = 0;
        for (String url : wcdErrorResults) {
            if (!wcdCompletedResults.contains(url)) {
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    WashingtonCertifiedDTO washingtonCertifiedDTO = extractBusiness(url, rows);
                    resultsSeqWriter.write(washingtonCertifiedDTO);
                    completedSeqWriter.write(url);
                    wcdDeltaResults.addAll(extractExtraUrls(doc, wcdSearchResults, wcdCompletedResults, iowaInsuranceURL));
                    wcdSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
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

        log.debug("wcdSearchResults size: " + wcdSearchResults.size());
        log.debug("wcdSearchDeltaResults size: " + wcdSearchDeltaResults.size());
        log.debug("wcdCompletedResults size: " + wcdCompletedResults.size());
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
        File lobbyistsFile = new File(fileRoot  + fileName);
        objectMapper().writeValue(lobbyistsFile, searchResults);

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


    private static WashingtonCertifiedDTO extractBusiness(String url, List<Element> trows) {

        WashingtonCertifiedDTO washingtonCertifiedDTO = new WashingtonCertifiedDTO();
        washingtonCertifiedDTO.setUrl(url);
        for (Element row : trows) {
            List<Element> variables = row.getElementsByTag("td");
            if (variables.get(0).text().equalsIgnoreCase("Certification Number")) {
                washingtonCertifiedDTO.setCertificationNumber(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Business Name")) {
                washingtonCertifiedDTO.setBusinessName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Categories")) {
                washingtonCertifiedDTO.setCategories(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Small Business Enterprise")) {
                washingtonCertifiedDTO.setSmallBusinessEnterprise(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Bid Price Reduction")) {
                washingtonCertifiedDTO.setBidPriceReduction(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Proposal Points")) {
                washingtonCertifiedDTO.setProposalPoints(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Business Address")) {
                washingtonCertifiedDTO.setBusinessAddress(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Ward")) {
                washingtonCertifiedDTO.setWard(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Principal Owner")) {
                washingtonCertifiedDTO.setPrincipalOwner(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Contact Name")) {
                washingtonCertifiedDTO.setContactName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Phone Number")) {
                washingtonCertifiedDTO.setPhoneNumber(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Email")) {
                washingtonCertifiedDTO.setEmail(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Website")) {
                washingtonCertifiedDTO.setWebsite(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Start Date")) {
                washingtonCertifiedDTO.setStartDate(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Established Date")) {
                washingtonCertifiedDTO.setEstablishedDate(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Expiration Date")) {
                washingtonCertifiedDTO.setExpirationDate(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Organization Type")) {
                washingtonCertifiedDTO.setOrganizationType(variables.get(1).text());
            }

        }
        return washingtonCertifiedDTO;
    }

}


