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
import java.util.concurrent.atomic.AtomicInteger;

import static com.alex.ua.util.ColorUtils.RED;
import static com.alex.ua.util.ColorUtils.RESET;

@Service
public class FarmServiceV2 {
    private static final int MAX_PLOTS = 13; // Максимальное количество грядок
    private final AtomicInteger activePlots = new AtomicInteger(0); // Счётчик активных грядок

    @Autowired
    private FarmBullClientImpl farmBullClient;

    public void runFarmEvent(FarmModel model, List<FarmModel> allFarmModels) {
        if (shouldCollect(model)) {
            collect(model);
        } else if (hasFreePlots() && shouldStartNewEvent(model) && isEnoughResources(model, allFarmModels)) {
            startNewEvent(model);
        }
    }

    private boolean hasFreePlots() {
        return activePlots.get() < MAX_PLOTS;
    }

    private boolean isEnoughResources(FarmModel model, List<FarmModel> allFarmModels) {
       return CollectionUtils.isEmpty(model.getRequired()) || model.getRequired().entrySet().stream().allMatch(required -> {
            FarmModel farmModel = allFarmModels.stream()
                    .filter(farm -> farm.getFarmDto().getId().equals(required.getKey()))
                    .findFirst()
                    .orElseThrow();
            return farmModel.getStoredAmount() > required.getValue();
        });
    }

    private boolean shouldCollect(FarmModel model) {
        return Objects.nonNull(model.getStartDateTime())
                && model.getStartDateTime().plusMinutes(model.getGrowTime()).isBefore(LocalDateTime.now());
    }

    private void collect(FarmModel model) {
        System.out.println("ATTEMPT to collect: " + model.getFarmDto().getId());
        FarmCollectResponse farmCollect = farmBullClient.farmCollect(model.getFarmDto());
        logEvent(model, farmCollect);
        model.setStartDateTime(null);
        model.setStoredAmount(farmCollect.getResponseMap().get(model.getFarmDto().getId()));
        activePlots.decrementAndGet(); // Уменьшаем количество активных грядок после сбора урожая
        System.out.println(RED + LocalTime.now() + " COLLECTED " + RESET + " ");
    }

    private boolean shouldStartNewEvent(FarmModel model) {
        return Objects.isNull(model.getStartDateTime());
    }

    private void startNewEvent(FarmModel model) {
        String farmRun = farmBullClient.farmRun(model.getFarmDto());
        logEvent(model, farmRun);
        model.setStartDateTime(LocalDateTime.now());
        System.out.println("active plots: " + activePlots.incrementAndGet()); // Увеличиваем количество активных грядок при старте нового события
    }

    private void logEvent(FarmModel model, FarmCollectResponse response) {
        System.out.println(RED + LocalTime.now() + " COLLECTED: " + model.getFarmDto().getType() + " id: " + model.getFarmDto().getId() + " " + RESET + " ");
        System.out.println(RED + response + RESET + " ");
    }

    private void logEvent(FarmModel model, String response) {
        System.out.println(RED + LocalTime.now() + " STARTED: " + model.getFarmDto().getType() + " id: " + model.getFarmDto().getId() + " " + RESET + " ");
        System.out.println(RED + response + RESET + " ");
    }
}

