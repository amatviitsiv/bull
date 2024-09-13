package com.alex.ua.client.delivery.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequiredAttribute {
    private String id;
    private int amount;
}
