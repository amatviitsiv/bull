package com.alex.ua.provider;

import com.alex.ua.client.farm.model.FarmDto;
import com.alex.ua.client.farm.model.FarmModel;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FarmObjectProviderV2 {
    @Getter
    private final LinkedList<FarmModel> farmModelList = new LinkedList<>();

    public FarmObjectProviderV2() {
        // -----------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("crops", "w"), 48, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "p"), 22, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "cr"), 47, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "co"), 63, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "t"), 63, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "a"), 19, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "s"), 26, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "wh"), 54, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "r"), 36, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "b"), 61, null));
        farmModelList.add(new FarmModel(new FarmDto("crops", "pc"), 47, null));
//-----------------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("animals", "ch"), 53, Map.of("co", 1200)));
        farmModelList.add(new FarmModel(new FarmDto("animals", "cw"), 31, Map.of("w", 3070)));
        farmModelList.add(new FarmModel(new FarmDto("animals", "pi"), 40, Map.of("p", 1020)));
        farmModelList.add(new FarmModel(new FarmDto("animals", "gs"), 27, Map.of("co", 512)));
        farmModelList.add(new FarmModel(new FarmDto("animals", "sh"), 101, Map.of("t", 6140)));
        farmModelList.add(new FarmModel(new FarmDto("animals", "gt"), 46, Map.of("cr", 2050)));
        farmModelList.add(new FarmModel(new FarmDto("animals", "h"), 67, Map.of("pc", 4100)));
//----------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("workshops", "m"), 19, Map.of("w", 512)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "sg"), 55, Map.of("co", 1024)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "gc"), 38, Map.of("gt", 1024)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "bu"), 95, Map.of("cw", 1540)));
//------------------------------------------------
        /*farmModelList.add(new FarmModel(new FarmDto("workshops", "ap"), 33, Map.of(
                "a", 1024,
                "m", 512,
                "cw", 256)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "cb"), 15, Map.of(
                "m", 256,
                "cw", 256)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "cac"), 62, Map.of(
                "cr", 512,
                "m", 1024,
                "ch", 512)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "ss"), 41, Map.of(
                "bu", 512,
                "m", 1280,
                "s", 1024)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "fs"), 68, Map.of(
                "gt", 1024,
                "cr", 1280,
                "t", 512)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "sm"), 33, Map.of(
                "cw", 768,
                "s", 512,
                "sg", 256)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "py"), 62, Map.of(
                "pc", 512,
                "gt", 256)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "gb"), 58, Map.of(
                "w", 1280,
                "h", 256,
                "s", 1024)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "pf"), 34, Map.of(
                "p", 512,
                "bu", 256)));*/
//-------------------------------------------------------------------------------
        farmModelList.add(new FarmModel(new FarmDto("workshops", "bc"), 199, Map.of("h", 4100)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "bn"), 170, Map.of("sh", 1540)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "tr"), 226, Map.of("sh", 3070)));
        farmModelList.add(new FarmModel(new FarmDto("workshops", "so"), 155, Map.of("sh", 4100)));
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

    /**
     * Метод для добавления новой модели фермы.
     *
     * @param farmModel новая модель фермы.
     */
    public void addFarmModel(FarmModel farmModel) {
        farmModelList.add(farmModel);
    }
}
