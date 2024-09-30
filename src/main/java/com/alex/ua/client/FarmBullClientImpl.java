package com.alex.ua.client;

import com.alex.ua.client.farm.booster.BoosterDto;
import com.alex.ua.client.farm.model.FarmCollectResponse;
import com.alex.ua.client.farm.model.FarmDto;
import com.alex.ua.client.farm.model.RunResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * The type Farm bull client.
 */
@Service
@AllArgsConstructor
public class FarmBullClientImpl {
    private static final String FARM_RUN_URL = "/farm/run";
    private static final String FARM_COLLECT_URL = "/farm/collect";
    private static final String FARM_BOOST_URL = "/farm/boost";

    private final WebClient webClient;

    public RunResponse farmRun(FarmDto dto) {
        return webClient
                .post()
                .uri(FARM_RUN_URL)
                .bodyValue(dto)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    // Handle client errors
                    return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException("Client error: " + body)));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    // Handle server errors
                    return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException("Server error: " + body)));
                })
                .bodyToMono(RunResponse.class)
                .block();
    }

    public FarmCollectResponse farmCollect(FarmDto dto) {
        return webClient
                .post()
                .uri(FARM_COLLECT_URL)
                .bodyValue(dto)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    // Handle client errors
                    return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException("Client error: " + body)));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    // Handle server errors
                    return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException("Server error: " + body)));
                })
                .bodyToMono(FarmCollectResponse.class)
                .block();
    }

    public String boostRun(BoosterDto dto) {
        return webClient
                .post()
                .uri(FARM_BOOST_URL)
                .bodyValue(dto)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    // Handle client errors
                    return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException("Client error: " + body)));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    // Handle server errors
                    return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException("Server error: " + body)));
                })
                .bodyToMono(String.class)
                .block();

    }
}
