package com.twitchsnitch.importer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.InsuranceProviderDTO;
import com.twitchsnitch.importer.dto.LobbyistDTO;
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

public class NewYorkLobbyists {

    private final static Logger log = LoggerFactory.getLogger(NewYorkLobbyists.class);
    private static RestTemplate restTemplate = new RestTemplate();

    static Set<WorkingProxyDTO> workingProxies = new HashSet<>();

    //private static String fileRoot = "/root/IdeaProjects/importer/src/test/resources/database/";
    private static String fileRoot = "/Users/horizondeep/Desktop/importer/src/test/resources/database/";


    public NewYorkLobbyists() throws IOException {
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
        workingProxies.add(new WorkingProxyDTO("159.65.160.32", 3128));
        workingProxies.add(new WorkingProxyDTO("159.89.34.116", 3128));
        workingProxies.add(new WorkingProxyDTO("159.89.184.194", 3128));
        workingProxies.add(new WorkingProxyDTO("165.227.210.160", 3128));
        workingProxies.add(new WorkingProxyDTO("159.203.84.179", 3128));
    }


    public static void main(String[] args) throws IOException, URISyntaxException {

        File completedfile = new File(fileRoot + "lobbyists_completed_results.json");
        File resultsfile = new File(fileRoot + "lobbyists.json");
        File errorsfile = new File(fileRoot + "lobbyists_errors.json");

        FileWriter completedFileWriter = new FileWriter(completedfile, true);
        FileWriter resultsFileWriter = new FileWriter(resultsfile, true);
        FileWriter errorsFileWriter = new FileWriter(errorsfile, true);

        SequenceWriter completedSeqWriter = objectMapper().writer().writeValuesAsArray(completedFileWriter);
        SequenceWriter resultsSeqWriter = objectMapper().writer().writeValuesAsArray(resultsFileWriter);
        SequenceWriter errorsSeqWriter = objectMapper().writer().writeValuesAsArray(errorsFileWriter);

        addWorkingProxies();
        int lobbyistsPageSize = 271;
        String iowaInsuranceURL = "https://opengovus.com/new-york-state-lobbyist";

        Set<String> lobbyistsSearchResults = new HashSet<>();
        Set<String> lobbyistsErrorResults = new HashSet<>();
        Set<String> lobbyistsDeltaResults = new HashSet<>();
        Set<String> lobbyistsSearchDeltaResults = new HashSet<>();
        Set<String> lobbyistsCompletedResults = new HashSet<>();

        File lobbyistsSearchFile = new File(fileRoot + "lobbyists_search_results.json");
        lobbyistsSearchResults =  objectMapper().readValue(lobbyistsSearchFile, new TypeReference<Set<String>>() {});
        if(lobbyistsSearchResults.size() == 0){
            genericSearch(iowaInsuranceURL, lobbyistsPageSize, lobbyistsSearchResults, "lobbyists_search_results.json");
        }

        try{
            lobbyistsCompletedResults =  objectMapper().readValue(completedfile, new TypeReference<Set<String>>() {});
        }
        catch(Exception e){
            log.error("lobbyistsCompletedResults file is empty");
        }

        try{
            lobbyistsErrorResults =  objectMapper().readValue(errorsfile, new TypeReference<Set<String>>() {});
        }
        catch(Exception e){
            log.error("lobbyistsErrorResults file is empty");
        }

        //all the general pages
        int i = 0;
        for (String url : lobbyistsSearchResults) {
            if (!lobbyistsCompletedResults.contains(url)) {
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    LobbyistDTO lobbyistDTO = extractLobbyists(url, rows);
                    resultsSeqWriter.write(lobbyistDTO);
                    completedSeqWriter.write(url);
                    lobbyistsDeltaResults.addAll(extractExtraUrls(doc, lobbyistsSearchResults, lobbyistsCompletedResults, iowaInsuranceURL));
                    lobbyistsSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    i++;
                } catch (Exception e) {
                    log.error("Issue adding all the page: " + url);
                    log.debug("Total records processed so far : " + i);
                    log.debug("Total errors so far : " + lobbyistsErrorResults.size());
                    errorsSeqWriter.write(url);
                    lobbyistsErrorResults.add(url);
                }
            }
        }

        int j = 0;
        for (String url : lobbyistsErrorResults) {
            if (!lobbyistsCompletedResults.contains(url)) {
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    LobbyistDTO lobbyistDTO = extractLobbyists(url, rows);
                    resultsSeqWriter.write(lobbyistDTO);
                    completedSeqWriter.write(url);
                    lobbyistsDeltaResults.addAll(extractExtraUrls(doc, lobbyistsSearchResults, lobbyistsCompletedResults, iowaInsuranceURL));
                    lobbyistsSearchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
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

        log.debug("lobbyistsSearchResults size: " + lobbyistsSearchResults.size());
        log.debug("lobbyistsSearchDeltaResults size: " + lobbyistsSearchDeltaResults.size());
        log.debug("lobbyistsCompletedResults size: " + lobbyistsCompletedResults.size());
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

    private static LobbyistDTO extractLobbyists(String url, List<Element> trows) {

        LobbyistDTO lobbyistDTO = new LobbyistDTO();
        lobbyistDTO.setUrl(url);
        for (Element row : trows) {
            List<Element> variables = row.getElementsByTag("td");
            if (variables.get(0).text().equalsIgnoreCase("Lobbyist Name")) {
                lobbyistDTO.setName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Lobbyist Address")) {
                lobbyistDTO.setAddress(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Lobbyist Phone")) {
                lobbyistDTO.setPhone(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Lobbyist Email")) {
                lobbyistDTO.setEmail(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Lobbyist Type")) {
                lobbyistDTO.setLobbyistType(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Lobbying Type")) {
                lobbyistDTO.setLobbyingType(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Government Level")) {
                lobbyistDTO.setGovernmentLevel(variables.get(1).text());
            }


        }
        return lobbyistDTO;
    }

}


