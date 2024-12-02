package com.alex.ua.client;

import com.alex.ua.client.delivery.model.DeliveryDto;
import com.alex.ua.client.delivery.model.burundi.BurundiCollectResponse;
import com.alex.ua.client.delivery.model.laos.LaosCollectResponse;
import com.alex.ua.client.delivery.model.moldova.MoldovaCollectResponse;
import com.alex.ua.client.delivery.model.uganda.UgandaCollectResponse;
import com.alex.ua.client.farm.model.RunResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
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
public class ContinentFarmClient {

    private static final String DELIVERY_RUN_URL = "/continent/run";
    private static final String DELIVERY_COLLECT_URL = "/continent/collect";

    private final WebClient webClient;

    public RunResponse farmRun(DeliveryDto dto) {
        return webClient
                .post()
                .uri(DELIVERY_RUN_URL)
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

    public <T> T collect(DeliveryDto dto, ParameterizedTypeReference<T> type) {
        return webClient
                .post()
                .uri(DELIVERY_COLLECT_URL)
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
                .bodyToMono(type)
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
