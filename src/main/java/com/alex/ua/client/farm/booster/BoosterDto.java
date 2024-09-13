package com.alex.ua.client.farm.booster;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BoosterDto {
    private String boostBy;
    private String id;
    private String type;
}
