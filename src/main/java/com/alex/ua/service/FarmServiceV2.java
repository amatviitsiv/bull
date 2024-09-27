package com.alex.ua.service;

import com.alex.ua.client.FarmBullClientImpl;
import com.alex.ua.client.farm.model.FarmCollectResponse;
import com.alex.ua.client.farm.model.FarmModel;
import com.alex.ua.provider.FarmObjectProviderV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.alex.ua.util.ColorUtils.RED;
import static com.alex.ua.util.ColorUtils.RESET;
import static com.alex.ua.util.ColorUtils.YELLOW;

@Service
public class FarmServiceV2 {
    private static final int MAX_PLOTS = 13; // Максимальное количество грядок
    private final AtomicInteger activePlots = new AtomicInteger(0); // Счётчик активных грядок
    private final Set<String> activeCrops = new HashSet<>();

    @Autowired
    private FarmBullClientImpl farmBullClient;

    public void runFarmEvent(FarmModel model, FarmObjectProviderV2 providerV2) {
        if (shouldCollect(model)) {
            collect(model);
        } else if (canStartNewEvent(model) && shouldStartNewEvent(model, providerV2) && isEnoughResources(model, providerV2.getFarmModelList())) {
            startNewEvent(model, providerV2.getFarmModelList());
        }
    }

    private boolean canStartNewEvent(FarmModel model) {
        return activePlots.get() < MAX_PLOTS && !activeCrops.contains(model.getFarmDto().getId());
    }

    private boolean isEnoughResources(FarmModel model, List<FarmModel> allFarmModels) {
       return CollectionUtils.isEmpty(model.getRequired()) || model.getRequired().entrySet().stream().allMatch(required -> {
            FarmModel farmModel = allFarmModels.stream()
                    .filter(farm -> farm.getFarmDto().getId().equals(required.getKey()))
                    .findFirst()
                    .orElseThrow();
            return farmModel.getStoredAmount() > (required.getValue() + 4000);
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
        activeCrops.remove(model.getFarmDto().getId());
        System.out.println(RED + LocalTime.now() + " COLLECTED " + RESET + " ");
    }

    private boolean shouldStartNewEvent(FarmModel model, FarmObjectProviderV2 providerV2) {
        String subtype = model.getSubtype();
        boolean flag = true;
        if (subtype.equals("factory")) {
            flag = providerV2.isEligibleForFarming(model, 1024);
        }
        if (subtype.equals("kitchen")) {
            flag = providerV2.isEligibleForFarming(model, 512);
        }
        return Objects.isNull(model.getStartDateTime()) && flag;
    }

    private void startNewEvent(FarmModel model, List<FarmModel> allFarmModels) {
        String farmRun = farmBullClient.farmRun(model.getFarmDto());
        logEvent(model, farmRun);
        model.setStartDateTime(LocalDateTime.now());
        System.out.println("active plots: " + activePlots.incrementAndGet()); // Увеличиваем количество активных грядок при старте нового события
        activeCrops.add(model.getFarmDto().getId());
        if (!CollectionUtils.isEmpty(model.getRequired())) {
            model.getRequired().entrySet().forEach(required -> {
                FarmModel farmModel = allFarmModels.stream()
                        .filter(farm -> farm.getFarmDto().getId().equals(required.getKey()))
                        .findFirst()
                        .orElseThrow();
                System.out.println(YELLOW + " was stored amount: " + farmModel.getFarmDto().getId() + " " + farmModel.getStoredAmount() + " " + RESET + " ");
                farmModel.setStoredAmount(farmModel.getStoredAmount() - required.getValue());
                System.out.println(YELLOW + " set stored amount: " + farmModel.getFarmDto().getId() + " " + farmModel.getStoredAmount() + " " + RESET + " ");
            });
        }
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

