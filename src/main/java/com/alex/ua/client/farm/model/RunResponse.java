package com.alex.ua.client.farm.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Run response.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RunResponse {
    @Getter
    private final Map<String, Object> properties = new HashMap<>();

    /**
     * Sets property.
     *
     * @param key   the key
     * @param value the value
     */
    @JsonAnySetter
    public void setProperty(String key, Object value) {
        this.properties.put(key, value);
    }

    @Override
    public String toString() {
        return "RunResponse{" +
                "properties=" + properties +
                '}';
    }
}
