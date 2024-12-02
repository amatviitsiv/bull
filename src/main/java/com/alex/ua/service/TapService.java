package com.alex.ua.service;

import com.alex.ua.client.delivery.model.RequiredAttribute;
import com.alex.ua.client.delivery.model.tap.AnimalCollectDto;
import com.alex.ua.client.delivery.model.tap.AnimalTapDto;
import com.alex.ua.client.delivery.model.tap.AnimalTapModel;
import com.alex.ua.client.delivery.model.tap.TapDto;
import com.alex.ua.client.farm.TapClient;
import com.alex.ua.client.farm.model.FarmModel;
import com.alex.ua.client.farm.model.RunResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.alex.ua.util.ColorUtils.GREEN;

/**
 * The type Tab service.
 */
@Service
public class TapService {

    @Autowired
    private TapClient client;

    /**
     * Tap box local date time.
     *
     * @return the local date time
     */
    public LocalDateTime tapBox() {
        TapDto dto = new TapDto(9999, 1);
        RunResponse runResponse = client.tapBox(dto);
        Object eml = runResponse.getProperties().get("eml");
        System.out.println("BOX taped");
        return LocalDateTime.ofInstant(Instant.ofEpochSecond((int) eml), ZoneId.systemDefault());
    }

    /**
     * Tap animal local date time.
     *
     * @param dto the dto
     * @return the local date time
     */
    public LocalDateTime tapAnimal(AnimalTapDto dto) {
        RunResponse runResponse = client.tapAnimal(dto);
        Object eml = runResponse.getProperties().get("eml");
        System.out.println(LocalTime.now() + " ANIMAL taped");
        return LocalDateTime.ofInstant(Instant.ofEpochSecond((int) eml), ZoneId.systemDefault());
    }

    public void collectAnimal(AnimalTapModel model, List<FarmModel> allFarmModels) {
        if (Objects.nonNull(model.getCollectTime())
                && model.getCollectTime().plusSeconds(5).isBefore(LocalDateTime.now())) {
            boolean isEnoughResources = model.getRequired().stream().allMatch(required -> {
                FarmModel farmModel = allFarmModels.stream()
                        .filter(farm -> farm.getFarmDto().getId().equals(required.getId()))
                        .findFirst()
                        .orElseThrow();
                System.out.println(GREEN + "TapService: new event require: " + farmModel.getFarmDto().getId() + ": stored: " + farmModel.getStoredAmount() + " required: " + required.getAmount());
                return farmModel.getStoredAmount() > required.getAmount();
            });
            if (isEnoughResources) {
                AnimalCollectDto collectDto = new AnimalCollectDto(model.getDto().getAnimal_idx());
                model.getRequired().forEach(required2 -> {
                    FarmModel farmModel = allFarmModels.stream()
                            .filter(farm -> farm.getFarmDto().getId().equals(required2.getId()))
                            .findFirst()
                            .orElseThrow();
                    System.out.println(GREEN + " was stored amount: " + farmModel.getFarmDto().getId() + " " + farmModel.getStoredAmount());
                    farmModel.setStoredAmount(farmModel.getStoredAmount() - required2.getAmount());
                    System.out.println(GREEN + " set stored amount: " + farmModel.getFarmDto().getId() + " " + farmModel.getStoredAmount());
                });
                RunResponse runResponse = client.collectAnimal(collectDto);
                model.setCollectTime(
                        LocalDateTime.ofInstant(
                                Instant.ofEpochSecond((int) runResponse.getProperties().get(
                                        model.getAnimalPrefix() + model.getDto().getAnimal_idx() + model.getCollectPostfix())), ZoneId.systemDefault()));

                Map<String, Integer> required = (Map<String, Integer>) runResponse.getProperties().get(
                        model.getAnimalPrefix() + model.getDto().getAnimal_idx() + model.getRequiredPostfix());
                model.getRequired().clear();
                for (Map.Entry<String, Integer> entry : required.entrySet()) {
                    model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
                }
            }
        }
    }
}
