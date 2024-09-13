package com.alex.ua.client;

import com.alex.ua.client.delivery.model.DeliveryDto;
import com.alex.ua.client.delivery.model.DeliveryModel;
import com.alex.ua.client.delivery.model.burundi.BurundiCollectResponse;
import com.alex.ua.client.delivery.model.laos.LaosCollectResponse;
import com.alex.ua.client.delivery.model.moldova.MoldovaCollectResponse;
import com.alex.ua.client.delivery.model.uganda.UgandaCollectResponse;
import com.alex.ua.client.farm.model.FarmDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ContinentFarmClient {

    private static final String DELIVERY_RUN_URL = "/continent/run";
    private static final String DELIVERY_COLLECT_URL = "/continent/collect";

    private final WebClient webClient;

    public String farmRun(DeliveryDto dto) {
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
                .bodyToMono(String.class)
                .block();

    }

    public BurundiCollectResponse burundiCollect(DeliveryDto dto) {
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
                .bodyToMono(BurundiCollectResponse.class)
                .block();
    }

    public LaosCollectResponse laosCollect(DeliveryDto dto) {
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
                .bodyToMono(LaosCollectResponse.class)
                .block();
    }

    public UgandaCollectResponse ugandaCollect(DeliveryDto dto) {
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
                .bodyToMono(UgandaCollectResponse.class)
                .block();
    }

    public MoldovaCollectResponse moldovaCollect(DeliveryDto dto) {
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
                .bodyToMono(MoldovaCollectResponse.class)
                .block();
    }
}
