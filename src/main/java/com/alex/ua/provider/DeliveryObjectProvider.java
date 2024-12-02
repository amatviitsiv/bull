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

    @Getter
    private final LinkedList<DeliveryModel> serbiyaModels = new LinkedList<>();

    @Getter
    private final LinkedList<DeliveryModel> finlandModels = new LinkedList<>();

    public DeliveryObjectProvider() {
        burundiModels.add(new DeliveryModel(new DeliveryDto("br", 0), 305));
        burundiModels.add(new DeliveryModel(new DeliveryDto("br", 1), 305));
        burundiModels.add(new DeliveryModel(new DeliveryDto("br", 2), 305));

        ugandaModels.add(new DeliveryModel(new DeliveryDto("ug", 0), 342));
        ugandaModels.add(new DeliveryModel(new DeliveryDto("ug", 1), 342));
        ugandaModels.add(new DeliveryModel(new DeliveryDto("ug", 2), 342));

        laosModels.add(new DeliveryModel(new DeliveryDto("la", 0), 152));
        laosModels.add(new DeliveryModel(new DeliveryDto("la", 1), 152));
        laosModels.add(new DeliveryModel(new DeliveryDto("la", 2), 152));

        moldovaModels.add(new DeliveryModel(new DeliveryDto("md", 0), 406));
        moldovaModels.add(new DeliveryModel(new DeliveryDto("md", 1), 406));
        moldovaModels.add(new DeliveryModel(new DeliveryDto("md", 2), 406));

        serbiyaModels.add(new DeliveryModel(new DeliveryDto("sb", 0), 406));
        serbiyaModels.add(new DeliveryModel(new DeliveryDto("sb", 1), 406));
        serbiyaModels.add(new DeliveryModel(new DeliveryDto("sb", 2), 406));

        finlandModels.add(new DeliveryModel(new DeliveryDto("fi", 0), 406));
        finlandModels.add(new DeliveryModel(new DeliveryDto("fi", 1), 406));
        finlandModels.add(new DeliveryModel(new DeliveryDto("fi", 2), 406));
    }
}
