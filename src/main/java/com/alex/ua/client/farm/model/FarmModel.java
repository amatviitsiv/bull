package com.alex.ua.client.farm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * The type Farm model.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FarmModel {
    private final FarmDto farmDto;
    private LocalDateTime collectDateTime;
    private final Map<String, Integer> required;
    private final String subtype;
    private int storedAmount;

}
