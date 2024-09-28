package com.alex.ua.client.farm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.management.ConstructorParameters;
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
    private final long growTime;
    private LocalDateTime startDateTime;
    private final Map<String, Integer> required;
    private final String subtype;
    private int storedAmount;

    public FarmModel(FarmDto farmDto, long growTime, Map<String, Integer> required, String subtype, int storedAmount) {
        this.farmDto = farmDto;
        this.growTime = growTime;
        this.required = required;
        this.subtype = subtype;
        this.storedAmount = storedAmount;
    }
}
