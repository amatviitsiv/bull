package com.alex.ua.service;

import com.alex.ua.client.AuthClient;
import com.alex.ua.client.FarmBullClientImpl;
import com.alex.ua.client.auth.AuthenticateResponse;
import com.alex.ua.client.farm.model.FarmCollectResponse;
import com.alex.ua.client.farm.model.FarmModel;
import com.alex.ua.client.farm.model.RunResponse;
import com.alex.ua.provider.FarmObjectProviderV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.alex.ua.util.ColorUtils.PURPLE;
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
    @Autowired
    private AuthClient authClient;

    public void initializeFarm(FarmObjectProviderV2 providerV2) {
        AuthenticateResponse authResponse = authClient.authenticate();
        Map<String, Object> user = authResponse.getUser();
        LinkedList<FarmModel> farmModelList = providerV2.getFarmModelList();
        System.out.println(PURPLE + " Initializing Farm. " + RESET);
        farmModelList.forEach(model -> {
            model.setStoredAmount((int) user.getOrDefault(model.getFarmDto().getId(), 0));
            Object collect = user.get(model.getFarmDto().getId() + "e");
            if (Objects.nonNull(collect) && (int) collect != 0) {
                model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond((int) collect), ZoneId.systemDefault()));
                activePlots.incrementAndGet();
                activeCrops.add(model.getFarmDto().getId());
            }
            System.out.println(PURPLE + " " + model.getFarmDto().getId() + ": " + model.getStoredAmount() + " " + model.getCollectDateTime() + " " + RESET);
        });
        System.out.println(PURPLE + " Finished initializing Farm. " + RESET);
    }

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
        return Objects.nonNull(model.getCollectDateTime())
                && model.getCollectDateTime().plusSeconds(10).isBefore(LocalDateTime.now());
    }

    private void collect(FarmModel model) {
        System.out.println("ATTEMPT to collect: " + model.getFarmDto().getId());
        FarmCollectResponse farmCollect = farmBullClient.farmCollect(model.getFarmDto());
        logEvent(model, farmCollect);
        model.setCollectDateTime(null);
        model.setStoredAmount(farmCollect.getResponseMap().get(model.getFarmDto().getId()));
        activePlots.decrementAndGet(); // Уменьшаем количество активных грядок после сбора урожая
        activeCrops.remove(model.getFarmDto().getId());
        System.out.println(RED + LocalTime.now() + " COLLECTED " + RESET + " ");
    }

    private boolean shouldStartNewEvent(FarmModel model, FarmObjectProviderV2 providerV2) {
        boolean flag = true;
        if (!CollectionUtils.isEmpty(model.getSubtype())) {
            flag = providerV2.isEligibleForFarming(model, 1024);
        }
        return Objects.isNull(model.getCollectDateTime()) && flag;
    }

    private void startNewEvent(FarmModel model, List<FarmModel> allFarmModels) {
        RunResponse farmRun = farmBullClient.farmRun(model.getFarmDto());
        logEvent(model, farmRun.toString());
        model.setCollectDateTime(LocalDateTime.ofInstant(
                Instant.ofEpochSecond((int) farmRun.getProperties()
                        .get(model.getFarmDto().getId() + "e")), ZoneId.systemDefault()));
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

