package com.alex.ua.client.farm.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * The type Farm model.
 */
@Data
@RequiredArgsConstructor
public class FarmModel {
    private final FarmDto farmDto;
    private final long growTime;
    private LocalDateTime startDateTime;
    private int storedAmount = 0;
    private final Map<String, Integer> required;
}
