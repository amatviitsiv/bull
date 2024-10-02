package com.alex.ua.provider;

import com.alex.ua.client.delivery.model.DeliveryDto;
import com.alex.ua.client.delivery.model.DeliveryModel;
import lombok.Getter;

import java.util.LinkedList;

public class DeliveryObjectProvider {
    @Getter
    private final LinkedList<DeliveryModel> ugandaModels = new LinkedList<>();
    @Getter
    private final LinkedList<DeliveryModel> laosModels = new LinkedList<>();
    @Getter
    private final LinkedList<DeliveryModel> burundiModels = new LinkedList<>();

    @Getter
    private final LinkedList<DeliveryModel> moldovaModels = new LinkedList<>();

    public DeliveryObjectProvider() {
        burundiModels.add(new DeliveryModel(new DeliveryDto("br", 0), 305));
        burundiModels.add(new DeliveryModel(new DeliveryDto("br", 1), 305));
        burundiModels.add(new DeliveryModel(new DeliveryDto("br", 2), 305));

        ugandaModels.add(new DeliveryModel(new DeliveryDto("ug", 0), 342));
        ugandaModels.add(new DeliveryModel(new DeliveryDto("ug", 1), 342));
        ugandaModels.add(new DeliveryModel(new DeliveryDto("ug", 2), 342));

        laosModels.add(new DeliveryModel(new DeliveryDto("la", 0), 342));
        laosModels.add(new DeliveryModel(new DeliveryDto("la", 1), 342));
        laosModels.add(new DeliveryModel(new DeliveryDto("la", 2), 342));

        moldovaModels.add(new DeliveryModel(new DeliveryDto("md", 0), 406));
        moldovaModels.add(new DeliveryModel(new DeliveryDto("md", 1), 406));
        moldovaModels.add(new DeliveryModel(new DeliveryDto("md", 2), 406));
    }
}
