package com.alex.ua.client.delivery.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class DeliveryModel {
    private final DeliveryDto deliveryDto;
    private List<RequiredAttribute> required = new ArrayList<>();
    private final long deliveryTime;
    private LocalDateTime startDateTime = LocalDateTime.now();
}
