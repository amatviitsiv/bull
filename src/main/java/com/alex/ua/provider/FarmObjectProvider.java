package com.alex.ua.provider;

import com.alex.ua.client.farm.model.FarmDto;
import com.alex.ua.client.farm.model.FarmModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FarmObjectProvider {
    @Getter
    private final LinkedList<FarmModel> farmModelList = new LinkedList<>();
    @Getter
    private final LinkedList<FarmModel> workshopModelList = new LinkedList<>();
    @Getter
    private final LinkedList<FarmModel> workshopKitchenModelList = new LinkedList<>();
    @Getter
    private final LinkedList<FarmModel> workshopFactoryModelList = new LinkedList<>();
    public FarmObjectProvider() {
        //farm models
        farmModelList.add(new FarmModel(new FarmDto("crops", "w"), 48, 7,
                        List.of(new FarmModel(new FarmDto("animals", "cw"), 31, 0, null))));
        farmModelList.add(new FarmModel(new FarmDto("crops", "p"), 22, 6,
                        List.of(new FarmModel(new FarmDto("animals", "pi"), 33, 0, null))));
        farmModelList.add(new FarmModel(new FarmDto("crops", "cr"), 47, 8,
                        List.of(new FarmModel(new FarmDto("animals", "gt"), 46, 0, null))));
        farmModelList.add(new FarmModel(new FarmDto("crops", "co"), 63, 6,
                Arrays.asList(  new FarmModel(new FarmDto("animals", "ch"), 44, 0, null),
                                new FarmModel(new FarmDto("animals", "gs"), 22, 0, null))));
        farmModelList.add(new FarmModel(new FarmDto("crops", "t"), 63, 8,
                        List.of(new FarmModel(new FarmDto("animals", "sh"), 89, 0, null))));
        farmModelList.add(new FarmModel(new FarmDto("crops", "a"), 19, 0, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "s"), 26, 0, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "wh"), 54, 0, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "r"), 36, 0, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "b"), 61, 0, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "pc"), 36, 7,
                        List.of(new FarmModel(new FarmDto("animals", "h"), 44, 0, null))));

        //workshop models
        workshopModelList.add(new FarmModel(new FarmDto("workshops", "m"), 19, 0, null));
        workshopModelList.add(new FarmModel(new FarmDto("workshops", "sg"), 55, 0, null));
        workshopModelList.add(new FarmModel(new FarmDto("workshops", "gc"), 38, 0, null));
        workshopModelList.add(new FarmModel(new FarmDto("workshops", "bu"), 95, 0, null));

        workshopModelList.get(0).setNext(workshopModelList.get(1));
        workshopModelList.get(1).setNext(workshopModelList.get(2));
        workshopModelList.get(2).setNext(workshopModelList.get(3));
        workshopModelList.get(3).setNext(workshopModelList.get(0));

        workshopKitchenModelList.add(new FarmModel(new FarmDto("workshops", "ap"), 33, 0, null));
        workshopKitchenModelList.add(new FarmModel(new FarmDto("workshops", "cb"), 15, 0, null));
        workshopKitchenModelList.add(new FarmModel(new FarmDto("workshops", "cac"), 62, 0, null));
        workshopKitchenModelList.add(new FarmModel(new FarmDto("workshops", "ss"), 41, 0, null));
        workshopKitchenModelList.add(new FarmModel(new FarmDto("workshops", "fs"), 68, 0, null));
        workshopKitchenModelList.add(new FarmModel(new FarmDto("workshops", "sm"), 33, 0, null));
        workshopKitchenModelList.add(new FarmModel(new FarmDto("workshops", "py"), 62, 0, null));
        workshopKitchenModelList.add(new FarmModel(new FarmDto("workshops", "gb"), 58, 0, null));
        workshopKitchenModelList.add(new FarmModel(new FarmDto("workshops", "pf"), 34, 0, null));

        workshopKitchenModelList.get(0).setNext(workshopKitchenModelList.get(1));
        workshopKitchenModelList.get(1).setNext(workshopKitchenModelList.get(2));
        workshopKitchenModelList.get(2).setNext(workshopKitchenModelList.get(3));
        workshopKitchenModelList.get(3).setNext(workshopKitchenModelList.get(4));
        workshopKitchenModelList.get(4).setNext(workshopKitchenModelList.get(5));
        workshopKitchenModelList.get(5).setNext(workshopKitchenModelList.get(6));
        workshopKitchenModelList.get(6).setNext(workshopKitchenModelList.get(7));
        workshopKitchenModelList.get(7).setNext(workshopKitchenModelList.get(8));
        workshopKitchenModelList.get(8).setNext(workshopKitchenModelList.get(0));

        workshopFactoryModelList.add(new FarmModel(new FarmDto("workshops", "bc"), 143, 0, null));
        workshopFactoryModelList.add(new FarmModel(new FarmDto("workshops", "bn"), 154, 0, null));
        workshopFactoryModelList.add(new FarmModel(new FarmDto("workshops", "tr"), 205, 0, null));
        workshopFactoryModelList.add(new FarmModel(new FarmDto("workshops", "so"), 129, 0, null));

        workshopFactoryModelList.get(0).setNext(workshopFactoryModelList.get(1));
        workshopFactoryModelList.get(1).setNext(workshopFactoryModelList.get(2));
        workshopFactoryModelList.get(2).setNext(workshopFactoryModelList.get(3));
        workshopFactoryModelList.get(3).setNext(workshopFactoryModelList.get(0));
    }

    public List<FarmModel> getAllFarmModels() {
        List<FarmModel> allFarmModels = new ArrayList<>();
        for (FarmModel model : farmModelList) {
            collectAllModels(model, allFarmModels);
        }
        return allFarmModels;
    }

    private void collectAllModels(FarmModel model, List<FarmModel> allFarmModels) {
        allFarmModels.add(model);
        if (model.getChildren() != null) {
            for (FarmModel child : model.getChildren()) {
                collectAllModels(child, allFarmModels);
            }
        }
    }
}
