package com.alex.ua.client.collection;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CollectionRequestDto {
    private String id;
    private List<Integer> animalIndexes;
    private int afHours;
}
