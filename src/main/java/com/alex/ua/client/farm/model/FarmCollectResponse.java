package com.alex.ua.client.farm.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Farm collect response.
 */
public class FarmCollectResponse {
    private Map<String, Integer> responseMap = new HashMap<>();

    /**
     * Sets response field.
     *
     * @param key   the key
     * @param value the value
     */
    @JsonAnySetter
    public void setResponseField(String key, Integer value) {
        responseMap.put(key, value);
    }

    /**
     * Gets response map.
     *
     * @return the response map
     */
    public Map<String, Integer> getResponseMap() {
        return responseMap;
    }

    @Override
    public String toString() {
        return "FarmResponse{" +
                "responseMap=" + responseMap +
                '}';
    }
}
