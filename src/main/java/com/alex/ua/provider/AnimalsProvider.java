package com.alex.ua.provider;

import com.alex.ua.client.delivery.model.tap.AnimalTapDto;
import com.alex.ua.client.delivery.model.tap.AnimalTapModel;
import lombok.Getter;

import java.util.LinkedList;

public class AnimalsProvider {

    @Getter
    private final LinkedList<AnimalTapModel> animals = new LinkedList<>();

    public AnimalsProvider() {
        /*animals.add(new AnimalTapModel(new AnimalTapDto(1)));
        animals.add(new AnimalTapModel(new AnimalTapDto(2)));
        animals.add(new AnimalTapModel(new AnimalTapDto(3)));
        animals.add(new AnimalTapModel(new AnimalTapDto(4)));
        animals.add(new AnimalTapModel(new AnimalTapDto(5)));*/
        //animals.add(new AnimalTapModel(new AnimalTapDto(6)));
        animals.add(new AnimalTapModel(new AnimalTapDto(7)));
        //animals.add(new AnimalTapModel(new AnimalTapDto(11)));
    }
}
