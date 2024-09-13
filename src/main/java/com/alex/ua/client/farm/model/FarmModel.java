package com.alex.ua.client.farm.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Farm model.
 */
@Data
@RequiredArgsConstructor
public class FarmModel {
    private final FarmDto farmDto;
    private final long growTime;
    private final int amountForChildren;
    private int timesCollected = 0;
    private LocalDateTime startDateTime;
    private final List<FarmModel> children;
    private int storedAmount = 5000;
    private FarmModel next;
}
