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
        farmModelList.add(new FarmModel(new FarmDto("crops", "w"), 62, null, "", 7593));
        farmModelList.add(new FarmModel(new FarmDto("crops", "p"), 22, null, "", 25319));
        farmModelList.add(new FarmModel(new FarmDto("crops", "cr"), 63, null, "", 6457));
        farmModelList.add(new FarmModel(new FarmDto("crops", "co"), 63, null, "", 5372));
        farmModelList.add(new FarmModel(new FarmDto("crops", "t"), 85, null, "", 6866));
        farmModelList.add(new FarmModel(new FarmDto("crops", "a"), 26, null, "", 140641));
        farmModelList.add(new FarmModel(new FarmDto("crops", "s"), 35, null, "", 71187));
        farmModelList.add(new FarmModel(new FarmDto("crops", "wh"), 54, null, "", 60926));
        farmModelList.add(new FarmModel(new FarmDto("crops", "r"), 36, null, "", 82212));
        farmModelList.add(new FarmModel(new FarmDto("crops", "b"), 61, null, "", 80928));
        farmModelList.add(new FarmModel(new FarmDto("crops", "pc"), 81, null, "", 6002));
//-----------------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("animals", "ch"), 60, Map.of("co", 2048), "animals", 37981));
        farmModelList.add(new FarmModel(new FarmDto("animals", "cw"), 31, Map.of("w", 3070), "animals", 4586));
        farmModelList.add(new FarmModel(new FarmDto("animals", "pi"), 40, Map.of("p", 1020), "animals", 148599));
        farmModelList.add(new FarmModel(new FarmDto("animals", "gs"), 27, Map.of("co", 512), "animals", 82033));
        farmModelList.add(new FarmModel(new FarmDto("animals", "sh"), 101, Map.of("t", 6140), "animals", 4879));
        farmModelList.add(new FarmModel(new FarmDto("animals", "gt"), 46, Map.of("cr", 2050), "animals", 16728));
        farmModelList.add(new FarmModel(new FarmDto("animals", "h"), 67, Map.of("pc", 4100), "animals", 4160));
//----------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("workshops", "m"), 25, Map.of("w", 1024), "bakaly", 47987));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "sg"), 55, Map.of("co", 1024), "bakaly", 27148));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "gc"), 38, Map.of("gt", 50000), "bakaly", 29482));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "bu"), 95, Map.of("cw", 1540), "bakaly", 4462));
//------------------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("workshops", "ap"), 40, Map.of(
                "a", 2048,
                "m", 1024,
                "cw", 512), "kitchen", 11050));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "cb"), 20, Map.of(
                "m", 512,
                "cw", 512), "kitchen", 14355));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "cac"), 73, Map.of(
                "cr", 1024,
                "m", 2048,
                "ch", 1024), "kitchen", 6758));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "ss"), 49, Map.of(
                "bu", 1024,
                "m", 2560,
                "s", 2048), "kitchen", 1404));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "fs"), 81, Map.of(
                "gt", 2048,
                "cr", 2560,
                "t", 1024), "kitchen", 1163));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "sm"), 39, Map.of(
                "cw", 1540,
                "s", 1024,
                "sg", 512), "kitchen", 1998));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "py"), 73, Map.of(
                "pc", 1024,
                "gt", 512), "kitchen", 8023));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "gb"), 69, Map.of(
                "w", 2560,
                "h", 512,
                "s", 2048), "kitchen", 3238));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "pf"), 41, Map.of(
                "p", 1024,
                "bu", 512), "kitchen", 8528));
//-------------------------------------------------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("workshops", "bc"), 199, Map.of("h", 4100), "factory", 1327));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "bn"), 186, Map.of("sh", 3070), "factory", 1259));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "tr"), 248, Map.of("sh", 6140), "factory", 543));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "so"), 155, Map.of("sh", 4100), "factory", 3191));
    }

    /**
     * Метод для получения активных моделей фермы (те, которые уже начали процесс выращивания).
     */
    public List<FarmModel> getActiveFarms() {
        return farmModelList.stream()
                .filter(model -> Objects.nonNull(model.getStartDateTime()))
                .collect(Collectors.toList());
    }

    /**
     * Метод для получения неактивных моделей фермы (те, которые можно начать выращивать).
     */
    public List<FarmModel> getInactiveFarms() {
        return farmModelList.stream()
                .filter(model -> Objects.isNull(model.getStartDateTime()))
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
