package com.alex.ua.client.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

/**
 * The type Authenticate response.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateResponse {
    @Getter
    private Map<String, Object> user;
}
