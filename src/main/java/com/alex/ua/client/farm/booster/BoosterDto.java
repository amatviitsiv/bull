package com.alex.ua.client.farm.booster;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Booster dto.
 */
@AllArgsConstructor
@Data
public class BoosterDto {
    private String boostBy;
    private String id;
    private String type;
}
