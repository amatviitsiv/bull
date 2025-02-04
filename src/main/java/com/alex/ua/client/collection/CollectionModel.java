package com.alex.ua.client.collection;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CollectionModel {
    private final CollectionCollectDto dto;
    private final CollectionRequestDto requestDto;
    private LocalDateTime collectDateTime;
}
