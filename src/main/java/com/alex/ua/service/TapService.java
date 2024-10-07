package com.alex.ua.service;

import com.alex.ua.client.delivery.model.tap.TapDto;
import com.alex.ua.client.farm.TapClient;
import com.alex.ua.client.farm.model.RunResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * The type Tab service.
 */
@Service
public class TapService {

    @Autowired
    private TapClient client;

    /**
     * Tap box local date time.
     *
     * @return the local date time
     */
    public LocalDateTime tapBox() {
        TapDto dto = new TapDto(9999, 1);
        RunResponse runResponse = client.tapBox(dto);
        Object eml = runResponse.getProperties().get("eml");
        return LocalDateTime.ofInstant(Instant.ofEpochSecond((int) eml), ZoneId.systemDefault());
    }
}
