package com.alex.ua.client;

import com.alex.ua.client.auth.AuthenticateDto;
import com.alex.ua.client.auth.AuthenticateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * The type Auth client.
 */
@Service
@RequiredArgsConstructor
public class AuthClient {
    private static final String AUTH_URL = "/auth/tg-mini-app";

    @Value("${auth.initdata}")
    private String authInitData;
    @Value("${tg.platform}")
    private String tgPlatform;
    @Value("${tg.version}")
    private String tgVersion;

    private final WebClient webClient;

    /**
     * Authenticate authenticate response.
     *
     * @return the authenticate response
     */
    public AuthenticateResponse authenticate() {
        AuthenticateDto dto = new AuthenticateDto(authInitData, tgPlatform, tgVersion);
        return webClient
                .post()
                .uri(AUTH_URL)
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
                .bodyToMono(AuthenticateResponse.class)
                .block();
    }
}