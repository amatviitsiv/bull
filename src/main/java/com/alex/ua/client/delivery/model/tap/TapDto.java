package com.alex.ua.client.delivery.model.tap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TapDto {
    int energy;
    int energy_left;
}