package com.alex.ua.client.delivery.model.tap;

import com.alex.ua.client.delivery.model.RequiredAttribute;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class AnimalTapModel {
    private final AnimalTapDto dto;
    private String animalPrefix = "za";
    private String collectPostfix = "e";
    private String idPostfix = "id";
    private String requiredPostfix = "rq";
    private List<RequiredAttribute> required = new ArrayList<>();
    private LocalDateTime collectTime;
}
