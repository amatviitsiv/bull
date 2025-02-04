package com.alex.ua.client;

import com.alex.ua.client.collection.CollectionCollectDto;
import com.alex.ua.client.collection.CollectionRequestDto;
import com.alex.ua.client.collection.CollectionRequestResponse;
import com.alex.ua.client.delivery.model.DeliveryDto;
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
public class CollectionClient {
    private static final String COLLECTION_REQUEST_URL = "/location/run";
    private static final String COLLECTION_COLLECT_URL = "/location/collect";

    private final WebClient webClient;

    public CollectionRequestResponse collectionRun(CollectionRequestDto dto) {
        return webClient
                .post()
                .uri(COLLECTION_REQUEST_URL)
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
                .bodyToMono(CollectionRequestResponse.class)
                .retryWhen(Retry
                        .fixedDelay(3, Duration.ofSeconds(5)) // Retry up to 3 times with a 2-second delay
                        .filter(throwable -> throwable instanceof WebClientRequestException
                                || throwable instanceof SocketException) // Retry only on these exceptions
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new RuntimeException("Max retries exhausted", retrySignal.failure()))
                )
                .block();

    }

    public CollectionRequestResponse collectionCollect(CollectionCollectDto dto) {
        return webClient
                .post()
                .uri(COLLECTION_COLLECT_URL)
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
                .bodyToMono(CollectionRequestResponse.class)
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
