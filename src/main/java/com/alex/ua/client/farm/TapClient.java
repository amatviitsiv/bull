package com.alex.ua.client.farm;

import com.alex.ua.client.delivery.model.tap.AnimalCollectDto;
import com.alex.ua.client.delivery.model.tap.AnimalTapDto;
import com.alex.ua.client.delivery.model.tap.TapDto;
import com.alex.ua.client.farm.model.FarmDto;
import com.alex.ua.client.farm.model.RunResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.SocketException;
import java.time.Duration;

@Service
@AllArgsConstructor
public class TapClient {
    private static final String TAP_BOX_URL = "/zoo/box/collect";
    private static final String ANIMAL_TAP_URL = "/zoo/animal/tap";
    private static final String ANIMAL_COLLECT = "/zoo/animal/collect";

    private final WebClient webClient;

    public RunResponse tapBox(TapDto dto) {
        return webClient
                .post()
                .uri(TAP_BOX_URL)
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

    public RunResponse tapAnimal(AnimalTapDto dto) {
        return webClient
                .post()
                .uri(ANIMAL_TAP_URL)
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

    public RunResponse collectAnimal(AnimalCollectDto dto) {
        return webClient
                .post()
                .uri(ANIMAL_COLLECT)
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
}
