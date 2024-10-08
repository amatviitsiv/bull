package com.alex.ua.provider;

import com.alex.ua.client.delivery.model.tap.AnimalTapDto;
import com.alex.ua.client.delivery.model.tap.AnimalTapModel;
import lombok.Getter;

import java.util.LinkedList;

public class AnimalsProvider {

    @Getter
    private final LinkedList<AnimalTapModel> animals = new LinkedList<>();

    public AnimalsProvider() {
        animals.add(new AnimalTapModel(new AnimalTapDto(1)));
        animals.add(new AnimalTapModel(new AnimalTapDto(2)));
    }
}
