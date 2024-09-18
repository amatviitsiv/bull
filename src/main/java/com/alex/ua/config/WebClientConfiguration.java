package com.alex.ua.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfiguration {

    private static final String BASE_URL = "https://api.bullishfarm.app";
    private static final String X_APP_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzUxNjMwNjgwLCJyZWZJZCI6IiIsInRhc2tGYWN0b3IiOjEw" +
            "MiwiaWF0IjoxNzI2NjkwMjg0LCJleHAiOjE3MjkyODIyODR9.hIKvMs_hnykWIpf6iuy1RWINQFpkJn0Q-UeJs8Ck0ng";
    private static final String CONTENT_TYPE = "application/json";
    private static final int TIMEOUT = 1000;

    @Bean
    public WebClient webClient() {
        final var tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                });

        return WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("content-type", CONTENT_TYPE)
                .defaultHeader("x-app-token", X_APP_TOKEN)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
