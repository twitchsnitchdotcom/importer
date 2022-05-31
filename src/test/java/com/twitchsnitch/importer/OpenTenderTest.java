package com.twitchsnitch.importer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.twitch.OAuthTokenDTO;
import com.twitchsnitch.importer.opentender.tendersearch.TenderSearchDTO;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.data.neo4j.core.Neo4jClient;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class OpenTenderTest {

    Driver driver = GraphDatabase
            .driver("neo4j://165.232.187.113:7687", AuthTokens.basic("neo4j", "traidable"));

    Neo4jClient client = Neo4jClient.create(driver);

    public final ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    @Test
    public void tenderSearch(){
        try {
            URL resource = getClass().getClassLoader().getResource("openeutender/tendersearch.json");
            File tokensFile = new File (resource.toURI());
            TenderSearchDTO tenderSearchDTO =  objectMapper().readValue(tokensFile, TenderSearchDTO.class);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    
    public void authorityById(){

    }

    public void companyById(){

    }
}
