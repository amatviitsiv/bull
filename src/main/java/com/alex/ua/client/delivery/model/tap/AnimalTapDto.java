package com.alex.ua.client.delivery.model.tap;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AnimalTapDto {
    private final int animal_idx;
    int energy = 16;
    int energy_left = 9984;
}