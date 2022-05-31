package com.twitchsnitch.importer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.CaliforniaCertifiedHealthDTO;
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

public class BostonBusiness {

    private final static Logger log = LoggerFactory.getLogger(BostonBusiness.class);
    private static RestTemplate restTemplate = new RestTemplate();

    static Set<WorkingProxyDTO> workingProxies = new HashSet<>();

    private static String fileRoot = "/home/jamie/IdeaProjects/importer/src/test/resources/database/";
    //private static String fileRoot = "/Users/horizondeep/Desktop/importer/src/test/resources/database/";


    public BostonBusiness() throws IOException {
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
        workingProxies.add(new WorkingProxyDTO("134.209.35.175", 3128));
        workingProxies.add(new WorkingProxyDTO("134.209.170.175", 3128));
        workingProxies.add(new WorkingProxyDTO("138.197.4.38", 3128));
        workingProxies.add(new WorkingProxyDTO("159.65.43.173", 3128));
        workingProxies.add(new WorkingProxyDTO("167.71.172.53", 3128));
    }


    public static void main(String[] args) throws IOException, URISyntaxException {

        File completedfile = new File(fileRoot + "california_certified_health_completed_results.json");
        File resultsfile = new File(fileRoot + "california_certified_health.json");
        File errorsfile = new File(fileRoot + "california_certified_health_errors.json");

        FileWriter completedFileWriter = new FileWriter(completedfile, true);
        FileWriter resultsFileWriter = new FileWriter(resultsfile, true);
        FileWriter errorsFileWriter = new FileWriter(errorsfile, true);

        SequenceWriter completedSeqWriter = objectMapper().writer().writeValuesAsArray(completedFileWriter);
        SequenceWriter resultsSeqWriter = objectMapper().writer().writeValuesAsArray(resultsFileWriter);
        SequenceWriter errorsSeqWriter = objectMapper().writer().writeValuesAsArray(errorsFileWriter);

        addWorkingProxies();
        int lobbyistsPageSize = 1;
        String iowaInsuranceURL = "https://opengovus.com/california-health-facility";

        Set<String> searchResults = new HashSet<>();
        Set<String> errorResults = new HashSet<>();
        Set<String> deltaResults = new HashSet<>();
        Set<String> searchDeltaResults = new HashSet<>();
        Set<String> completedResults = new HashSet<>();

        try{
            File lobbyistsSearchFile = new File(fileRoot + "california_certified_health_search_results.json");
            searchResults =  objectMapper().readValue(lobbyistsSearchFile, new TypeReference<Set<String>>() {});
            if(searchResults.size() == 0){
                genericSearch(iowaInsuranceURL, lobbyistsPageSize, searchResults, "california_certified_health_search_results.json");
            }
        }
        catch(Exception e){
            log.error("completedResults file is empty");
            genericSearch(iowaInsuranceURL, lobbyistsPageSize, searchResults, "california_certified_health_search_results.json");
        }


        try{
            completedResults =  objectMapper().readValue(completedfile, new TypeReference<Set<String>>() {});
        }
        catch(Exception e){
            log.error("completedResults file is empty");
        }

        try{
            errorResults =  objectMapper().readValue(errorsfile, new TypeReference<Set<String>>() {});
        }
        catch(Exception e){
            log.error("errorResults file is empty");
        }

        //all the general pages
        int i = 0;
        for (String url : searchResults) {
            if (!completedResults.contains(url)) {
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    Elements select2 = doc.select("table.table-dl:nth-child(5) > tbody:nth-child(1)");
                    List<Element> rows2 = select2.get(0).getElementsByTag("tr");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    CaliforniaCertifiedHealthDTO californiaCertifiedHealthDTO = extractBusiness(url, rows, rows2);
                    resultsSeqWriter.write(californiaCertifiedHealthDTO);
                    completedSeqWriter.write(url);
                    deltaResults.addAll(extractExtraUrls(doc, searchResults, completedResults, iowaInsuranceURL));
                    searchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
                    i++;
                } catch (Exception e) {
                    log.error("Issue adding all the page: " + url);
                    log.debug("Total records processed so far : " + i);
                    log.debug("Total errors so far : " + errorResults.size());
                    errorsSeqWriter.write(url);
                    errorResults.add(url);
                }
            }
        }

        int j = 0;
        for (String url : errorResults) {
            if (!completedResults.contains(url)) {
                Document doc = null;
                try {
                    WorkingProxyDTO randomProxy = getRandomProxy();
                    doc = Jsoup.connect(url).proxy(randomProxy.getIp(), randomProxy.getPort()).timeout(5000).get();
                    Elements select = doc.select("#overview > div.panel-body > div > table > tbody");
                    Elements select2 = doc.select("table.table-dl:nth-child(5) > tbody:nth-child(1)");
                    List<Element> rows = select.get(0).getElementsByTag("tr");
                    List<Element> rows2 = select2.get(0).getElementsByTag("tr");
                    CaliforniaCertifiedHealthDTO californiaCertifiedHealthDTO = extractBusiness(url, rows, rows2);
                    resultsSeqWriter.write(californiaCertifiedHealthDTO);
                    completedSeqWriter.write(url);
                    deltaResults.addAll(extractExtraUrls(doc, searchResults, completedResults, iowaInsuranceURL));
                    searchDeltaResults.addAll(extractExtraSearchUrls(doc, url));
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

        log.debug("searchResults size: " + searchResults.size());
        log.debug("searchDeltaResults size: " + searchDeltaResults.size());
        log.debug("completedResults size: " + completedResults.size());
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



    /**



     File Number
     Business Name
     Owner Name
     Business Type
     Address
     Email
     Issue Date
     Expiration Date
     */
    private static CaliforniaCertifiedHealthDTO extractBusiness(String url, List<Element> trows, List<Element> trows2 ) {

        CaliforniaCertifiedHealthDTO californiaCertifiedHealthDTO = new CaliforniaCertifiedHealthDTO();
        californiaCertifiedHealthDTO.setUrl(url);

        for (Element row : trows2) {
            List<Element> variables = row.getElementsByTag("td");
            if (variables.get(0).text().equalsIgnoreCase("Contact Telephone")) {
                californiaCertifiedHealthDTO.setContactTelephone(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Contact Fax")) {
                californiaCertifiedHealthDTO.setContactFax(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Contact Email")) {
                californiaCertifiedHealthDTO.setContactEmail(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Facility Administrator")) {
                californiaCertifiedHealthDTO.setFacilityAdministrator(variables.get(1).text());
            }
        }

        for (Element row : trows) {
            List<Element> variables = row.getElementsByTag("td");
            if (variables.get(0).text().equalsIgnoreCase("Initial License Date")) {
                californiaCertifiedHealthDTO.setInitialLicenseDate(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("License Effective Date")) {
                californiaCertifiedHealthDTO.setLicenseEffectiveDate(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("License Expiration Date")) {
                californiaCertifiedHealthDTO.setLicenseExpirationDate(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Entity Type")) {
                californiaCertifiedHealthDTO.setEntityType(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Long Term Care")) {
                californiaCertifiedHealthDTO.setLongTermCare(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Capacity")) {
                californiaCertifiedHealthDTO.setCapacity(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Facility Onsite Services")) {
                californiaCertifiedHealthDTO.setFacilityOnsiteServices(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("CCLHO Name")) {
                californiaCertifiedHealthDTO.setCCLHOName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Facility Status")) {
                californiaCertifiedHealthDTO.setFacilityStatus(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Licensed Certified")) {
                californiaCertifiedHealthDTO.setLicensedCertified(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Facility Type")) {
                californiaCertifiedHealthDTO.setFacilityType(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("License Number")) {
                californiaCertifiedHealthDTO.setLicenseNumber(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Business Name")) {
                californiaCertifiedHealthDTO.setBusinessName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("License Status")) {
                californiaCertifiedHealthDTO.setLicenseStatus(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Facility ID")) {
                californiaCertifiedHealthDTO.setFacilityID(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Facility Name")) {
                californiaCertifiedHealthDTO.setFacilityName(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("Address")) {
                californiaCertifiedHealthDTO.setAddress(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("County")) {
                californiaCertifiedHealthDTO.setCounty(variables.get(1).text());
            }
            if (variables.get(0).text().equalsIgnoreCase("District Name")) {
                californiaCertifiedHealthDTO.setDistrictName(variables.get(1).text());
            }
        }
        return californiaCertifiedHealthDTO;
    }

}


