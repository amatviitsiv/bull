package com.alex.ua.client;

import com.alex.ua.client.delivery.model.eddy.EddyResponse;
import com.alex.ua.client.farm.booster.BoosterDto;
import com.alex.ua.client.farm.model.FarmCollectResponse;
import com.alex.ua.client.farm.model.FarmDto;
import com.alex.ua.client.farm.model.RunResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.SocketException;
import java.time.Duration;

/**
 * The type Farm bull client.
 */
@Service
@AllArgsConstructor
public class FarmBullClientImpl {
    private static final String FARM_RUN_URL = "/farm/run";
    private static final String FARM_COLLECT_URL = "/farm/collect";
    private static final String FARM_BOOST_URL = "/farm/boost";
    private static final String FARM_CHANGE_EDDY = "/task/hot/change";

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
                .retryWhen(Retry
                        .fixedDelay(3, Duration.ofSeconds(5)) // Retry up to 3 times with a 2-second delay
                        .filter(throwable -> throwable instanceof WebClientRequestException
                                || throwable instanceof SocketException) // Retry only on these exceptions
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new RuntimeException("Max retries exhausted", retrySignal.failure()))
                )
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
                .retryWhen(Retry
                        .fixedDelay(3, Duration.ofSeconds(5)) // Retry up to 3 times with a 2-second delay
                        .filter(throwable -> throwable instanceof WebClientRequestException
                                || throwable instanceof SocketException) // Retry only on these exceptions
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new RuntimeException("Max retries exhausted", retrySignal.failure()))
                )
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

    public EddyResponse rollEddy() {
        return webClient
                .post()
                .uri(FARM_CHANGE_EDDY)
                .bodyValue("{}")
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
                .bodyToMono(EddyResponse.class)
                .block();

    }
}
