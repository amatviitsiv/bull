package com.alex.ua.provider;

import com.alex.ua.client.farm.model.FarmDto;
import com.alex.ua.client.farm.model.FarmModel;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class FarmObjectProviderV2 {
    @Getter
    private final LinkedList<FarmModel> farmModelList = new LinkedList<>();

    public FarmObjectProviderV2() {
        // -----------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("crops", "w"), null, new HashSet<>()));
        farmModelList.add(new FarmModel(new FarmDto("crops", "p"),  null, new HashSet<>()));
        farmModelList.add(new FarmModel(new FarmDto("crops", "cr"),  null, new HashSet<>()));
        farmModelList.add(new FarmModel(new FarmDto("crops", "co"),  null, new HashSet<>()));
        farmModelList.add(new FarmModel(new FarmDto("crops", "t"),  null, new HashSet<>()));
        farmModelList.add(new FarmModel(new FarmDto("crops", "a"), null, new HashSet<>()));
        farmModelList.add(new FarmModel(new FarmDto("crops", "s"), null, new HashSet<>()));
        farmModelList.add(new FarmModel(new FarmDto("crops", "wh"), null, new HashSet<>()));
        farmModelList.add(new FarmModel(new FarmDto("crops", "r"), null, new HashSet<>()));
        farmModelList.add(new FarmModel(new FarmDto("crops", "b"), null, new HashSet<>()));
        farmModelList.add(new FarmModel(new FarmDto("crops", "pc"), null, new HashSet<>()));
//-----------------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("animals", "ch"), Map.of("co", 32800), new HashSet<>(Arrays.asList("Corn"))));
        farmModelList.add(new FarmModel(new FarmDto("animals", "cw"), Map.of("w", 49200), new HashSet<>(Arrays.asList("Wheat"))));
        farmModelList.add(new FarmModel(new FarmDto("animals", "pi"), Map.of("p", 32800), new HashSet<>(Arrays.asList("Potato"))));
        farmModelList.add(new FarmModel(new FarmDto("animals", "gs"), Map.of("co", 16400), new HashSet<>(Arrays.asList("Corn"))));
        farmModelList.add(new FarmModel(new FarmDto("animals", "sh"), Map.of("t", 49200), new HashSet<>(Arrays.asList("Tomato"))));
        farmModelList.add(new FarmModel(new FarmDto("animals", "gt"), Map.of("cr", 32800), new HashSet<>(Arrays.asList("Carrot"))));
        farmModelList.add(new FarmModel(new FarmDto("animals", "h"), Map.of("pc", 65500), new HashSet<>(Arrays.asList("Peach"))));
//----------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("workshops", "m"), Map.of("w", 4100), new HashSet<>(Arrays.asList("Wheat"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "sg"), Map.of("co", 4100), new HashSet<>(Arrays.asList("Corn"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "gc"), Map.of("gt", 4100), new HashSet<>(Arrays.asList("Goat"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "bu"), Map.of("cw", 6140), new HashSet<>(Arrays.asList("Cow"))));
//------------------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("workshops", "ap"), Map.of(
                "a", 4100,
                "m", 2048,
                "cw", 1024), new HashSet<>(Arrays.asList("Apple Tree", "Flour"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "cb"), Map.of(
                "m", 1024,
                "cw", 1024), new HashSet<>(Arrays.asList("Flour"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "cac"), Map.of(
                "m", 4100,
                "cr", 2050,
                "ch", 2050), new HashSet<>(Arrays.asList("Carrot", "Flour"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "ss"), Map.of(
                "m", 5120,
                "bu", 2050,
                "s", 4100), new HashSet<>(Arrays.asList("Butter", "Flour", "Strawberry"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "fs"), Map.of(
                "gt", 4100,
                "cr", 5120,
                "t", 2050), new HashSet<>(Arrays.asList("Carrot", "Goat"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "sm"), Map.of(
                "cw", 3070,
                "s", 2050,
                "sg", 1024), new HashSet<>(Arrays.asList("Strawberry", "Sugar"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "py"), Map.of(
                "pc", 2050,
                "gt", 1024), new HashSet<>(Arrays.asList("Goat"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "gb"), Map.of(
                "w", 5120,
                "h", 1024,
                "s", 4100), new HashSet<>(Arrays.asList("Strawberry"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "pf"), Map.of(
                "p", 2050,
                "bu", 1024), new HashSet<>(Arrays.asList("Potato", "Butter"))));
//-------------------------------------------------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("workshops", "bc"), Map.of("h", 65500), new HashSet<>(Arrays.asList("Bee"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "bn"), Map.of("sh", 3070), new HashSet<>(Arrays.asList("Sheep"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "tr"), Map.of("sh", 6140), new HashSet<>(Arrays.asList("Sheep"))));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "so"), Map.of("sh", 4100), new HashSet<>(Arrays.asList("Sheep"))));
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
                .filter(model -> model.getSubtype().contains(subtype))
                .collect(Collectors.toList());
    }

    public boolean isEligibleForFarming(FarmModel model, int amount) {
        AtomicBoolean eligible = new AtomicBoolean(true);
        Set<String> subtypes = model.getSubtype();
        subtypes.forEach(subtype -> {
            List<FarmModel> farmModelsBySubtype = findFarmModelsBySubtype(subtype);
            farmModelsBySubtype.forEach(stored -> {
                if (model.getStoredAmount() > stored.getStoredAmount()
                        && (model.getStoredAmount() - stored.getStoredAmount()) >= amount) {
                    eligible.set(false);
                }
            });
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
