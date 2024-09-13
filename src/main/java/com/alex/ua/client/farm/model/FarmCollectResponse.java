package com.alex.ua.client.farm.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class FarmCollectResponse {
    private Map<String, Integer> responseMap = new HashMap<>();

    @JsonAnySetter
    public void setResponseField(String key, Integer value) {
        responseMap.put(key, value);
    }

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
