package com.twitchsnitch.importer;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.internal.shaded.reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ReactiveWebClientTest {

    @Test
    public void basicTest(){
        WebClient webClient = WebClient.create("https://opengovus.com");

        String createdEmployee = webClient.get()
                .uri("/iowa-insurance-producer/259413")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(createdEmployee);
    }


//    @Test
//    public void testBasic(){
//        HttpClient httpClient = HttpClient.create()
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
//                .responseTimeout(Duration.ofMillis(5000))
//                .doOnConnected(conn ->
//                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
//                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));
//
//        WebClient client = WebClient.builder()
//                .baseUrl("https://opengovus.com")
//                .clientConnector(new ReactorClientHttpConnector(httpClient))
//                .build();
//
//        UriSpec<WebClient.RequestBodySpec> uriSpec = client.get();
//
//        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/resource");
//
//        Mono<String> response = bodySpec.exchangeToMono(response -> {
//            if (response.statusCode()
//                    .equals(HttpStatus.OK)) {
//                return response.bodyToMono(String.class);
//            } else if (response.statusCode()
//                    .is4xxClientError()) {
//                return Mono.just("Error response");
//            } else {
//                return response.createException()
//                        .flatMap(Mono::error);
//            }
//        });
//
//    }
}
