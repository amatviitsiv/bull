package com.alex.ua.service;

import com.alex.ua.client.FarmBullClientImpl;
import com.alex.ua.client.farm.model.FarmCollectResponse;
import com.alex.ua.client.farm.model.FarmModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static com.alex.ua.util.ColorUtils.RED;
import static com.alex.ua.util.ColorUtils.RESET;

@Service
public class FarmService {
    @Autowired
    private FarmBullClientImpl farmBullClient;

    public void runFarmEvent(FarmModel model) {
        if (shouldCollect(model)) {
            collect(model);
        } else if (shouldStartNewEvent(model)) {
            if (shouldRunChildEvents(model)) {
                runChildFarmEvent(model.getChildren(), model);
            } else {
                startNewEvent(model, "STARTED");
            }
        }
    }

    private boolean shouldCollect(FarmModel model) {
        return Objects.nonNull(model.getStartDateTime())
                && model.getStartDateTime().plusMinutes(model.getGrowTime()).isBefore(LocalDateTime.now());
    }

    private void collect(FarmModel model) {
        System.out.println("ATTEMPT to collect: " + model.getFarmDto().getId());
        FarmCollectResponse farmCollect = farmBullClient.farmCollect(model.getFarmDto());
        logEvent("COLLECTED", model, farmCollect);
        model.setStartDateTime(null);
        model.setTimesCollected(model.getTimesCollected() + 1);
        model.setStoredAmount(farmCollect.getResponseMap().get(model.getFarmDto().getId()));
        System.out.println(RED + LocalTime.now() + " COLLECTED AMOUNT: " + model.getTimesCollected()+ " " + RESET);
    }

    private void collectChildEvents(List<FarmModel> farmModels) {
        farmModels.forEach(model -> {
            if (shouldCollect(model)) {
                collect(model);
            }
        });
    }

    private boolean shouldStartNewEvent(FarmModel model) {
        return Objects.isNull(model.getStartDateTime());
    }

    private boolean shouldRunChildEvents(FarmModel model) {
        return !CollectionUtils.isEmpty(model.getChildren())
                && model.getTimesCollected() >= model.getAmountForChildren();
    }

    private void startChildEvents(List<FarmModel> farmModels) {
        farmModels.forEach(model -> startNewEvent(model, "STARTED CHILD"));
    }

    private void startNewEvent(FarmModel model, String entity) {
        String farmRun = farmBullClient.farmRun(model.getFarmDto());
        logEvent(entity, model, farmRun);
        model.setStartDateTime(LocalDateTime.now());
    }

    private void logEvent(String action, FarmModel model, FarmCollectResponse response) {
        System.out.println(RED + LocalTime.now() + " " + action + ": " + model.getFarmDto().getType() + " id: " + model.getFarmDto().getId()+ " " + RESET);
        System.out.println(RED + response + RESET);
    }

    private void logEvent(String action, FarmModel model, String response) {
        System.out.println(RED + LocalTime.now() + " " + action + ": " + model.getFarmDto().getType() + " id: " + model.getFarmDto().getId()+ " " + RESET);
        System.out.println(RED + response + RESET);
    }

    public void runChildFarmEvent(List<FarmModel> farmModels, FarmModel parent) {
        if (allModelsReadyToStart(farmModels)) {
            startChildEvents(farmModels);
        } else {
            collectChildEvents(farmModels);
            if (allModelsReadyToStart(farmModels)) {
                parent.setTimesCollected(0);
            }
        }
    }

    private boolean allModelsReadyToStart(List<FarmModel> farmModels) {
        return farmModels.stream().allMatch(model -> Objects.isNull(model.getStartDateTime()));
    }
}
