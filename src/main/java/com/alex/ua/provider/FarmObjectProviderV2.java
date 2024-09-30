package com.alex.ua.provider;

import com.alex.ua.client.farm.model.FarmDto;
import com.alex.ua.client.farm.model.FarmModel;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class FarmObjectProviderV2 {
    @Getter
    private final LinkedList<FarmModel> farmModelList = new LinkedList<>();

    public FarmObjectProviderV2() {
        // -----------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("crops", "w"), null, ""));
        farmModelList.add(new FarmModel(new FarmDto("crops", "p"),  null, ""));
        farmModelList.add(new FarmModel(new FarmDto("crops", "cr"),  null, ""));
        farmModelList.add(new FarmModel(new FarmDto("crops", "co"),  null, ""));
        farmModelList.add(new FarmModel(new FarmDto("crops", "t"),  null, ""));
        farmModelList.add(new FarmModel(new FarmDto("crops", "a"), null, ""));
        farmModelList.add(new FarmModel(new FarmDto("crops", "s"), null, ""));
        farmModelList.add(new FarmModel(new FarmDto("crops", "wh"), null, ""));
        farmModelList.add(new FarmModel(new FarmDto("crops", "r"), null, ""));
        farmModelList.add(new FarmModel(new FarmDto("crops", "b"), null, ""));
        farmModelList.add(new FarmModel(new FarmDto("crops", "pc"), null, ""));
//-----------------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("animals", "ch"), Map.of("co", 2048), "animals"));
        farmModelList.add(new FarmModel(new FarmDto("animals", "cw"), Map.of("w", 3070), "animals"));
        farmModelList.add(new FarmModel(new FarmDto("animals", "pi"), Map.of("p", 500000), "animals"));
        farmModelList.add(new FarmModel(new FarmDto("animals", "gs"), Map.of("co", 500000), "animals"));
        farmModelList.add(new FarmModel(new FarmDto("animals", "sh"), Map.of("t", 6140), "animals"));
        farmModelList.add(new FarmModel(new FarmDto("animals", "gt"), Map.of("cr", 2050), "animals"));
        farmModelList.add(new FarmModel(new FarmDto("animals", "h"), Map.of("pc", 4100), "animals"));
//----------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("workshops", "m"), Map.of("w", 1024), "bakaly"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "sg"), Map.of("co", 1024), "bakaly"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "gc"), Map.of("gt", 500000), "bakaly"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "bu"), Map.of("cw", 1540), "bakaly"));
//------------------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("workshops", "ap"), Map.of(
                "a", 2048,
                "m", 1024,
                "cw", 512), "kitchen"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "cb"), Map.of(
                "m", 512,
                "cw", 512), "kitchen"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "cac"), Map.of(
                "cr", 1024,
                "m", 2048,
                "ch", 1024), "kitchen"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "ss"), Map.of(
                "bu", 1024,
                "m", 2560,
                "s", 2048), "kitchen"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "fs"), Map.of(
                "gt", 2048,
                "cr", 2560,
                "t", 1024), "kitchen"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "sm"), Map.of(
                "cw", 1540,
                "s", 1024,
                "sg", 512), "kitchen"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "py"), Map.of(
                "pc", 1024,
                "gt", 512), "kitchen"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "gb"), Map.of(
                "w", 2560,
                "h", 512,
                "s", 2048), "kitchen"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "pf"), Map.of(
                "p", 1024,
                "bu", 512), "kitchen"));
//-------------------------------------------------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("workshops", "bc"), Map.of("h", 4100), ""));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "bn"), Map.of("sh", 3070), "factory"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "tr"), Map.of("sh", 6140), "factory"));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "so"), Map.of("sh", 4100), "factory"));
    }

    /**
     * Метод для получения активных моделей фермы (те, которые уже начали процесс выращивания).
     */
    public List<FarmModel> getActiveFarms() {
        return farmModelList.stream()
                .filter(model -> Objects.nonNull(model.getCollectDateTime()))
                .collect(Collectors.toList());
    }

    /**
     * Метод для получения неактивных моделей фермы (те, которые можно начать выращивать).
     */
    public List<FarmModel> getInactiveFarms() {
        return farmModelList.stream()
                .filter(model -> Objects.isNull(model.getCollectDateTime()))
                .collect(Collectors.toList());
    }

    /**
     * Метод для поиска конкретного объекта фермы по типу (например, по названию культуры).
     *
     * @param id тип культуры или животного (например, "w" для Wheat).
     * @return найденный объект FarmModel или null, если не найден.
     */
    public FarmModel findFarmModelById(String id) {
        return farmModelList.stream()
                .filter(model -> model.getFarmDto().getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<FarmModel> findFarmModelsBySubtype(String subtype) {
        return farmModelList.stream()
                .filter(model -> model.getSubtype().equals(subtype))
                .collect(Collectors.toList());
    }

    public boolean isEligibleForFarming(FarmModel model, int amount) {
        AtomicBoolean eligible = new AtomicBoolean(true);
        List<FarmModel> farmModelsBySubtype = findFarmModelsBySubtype(model.getSubtype());
        farmModelsBySubtype.forEach(stored -> {
            if (model.getStoredAmount() > stored.getStoredAmount()
                    && (model.getStoredAmount() - stored.getStoredAmount()) >= amount) {
                eligible.set(false);
            }
        });
        return eligible.get();
    }

    /**
     * Метод для добавления новой модели фермы.
     *
     * @param farmModel новая модель фермы.
     */
    public void addFarmModel(FarmModel farmModel) {
        farmModelList.add(farmModel);
    }
}
