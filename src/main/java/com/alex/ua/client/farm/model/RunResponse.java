package com.alex.ua.client.farm.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RunResponse {
    @Getter
    private final Map<String, Object> properties = new HashMap<>();
}
