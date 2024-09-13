package com.alex.ua.service;

import com.alex.ua.client.FarmBullClientImpl;
import com.alex.ua.client.farm.model.FarmCollectResponse;
import com.alex.ua.client.farm.model.FarmModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Service
public class WorkShopService {
    @Autowired
    private FarmBullClientImpl farmBullClient;

    public void runFarmEvent(FarmModel farmModel) {
        if (shouldStartNewEvent(farmModel)) {
            startNewEvent(farmModel, "STARTED");
        }
    }

    public void collect(FarmModel model) {
        System.out.println("ATTEMPT to collect: " + model.getFarmDto().getId());
        FarmCollectResponse farmCollect = farmBullClient.farmCollect(model.getFarmDto());
        logEvent("COLLECTED", model, farmCollect);
        model.setStartDateTime(null);
        model.setTimesCollected(model.getTimesCollected() + 1);
        model.setStoredAmount(farmCollect.getResponseMap().get(model.getFarmDto().getId()));
        System.out.println(LocalTime.now() + " COLLECTED AMOUNT: " + model.getTimesCollected());
    }

    public boolean shouldCollect(FarmModel model) {
        return Objects.nonNull(model.getStartDateTime())
                && model.getStartDateTime().plusMinutes(model.getGrowTime()).isBefore(LocalDateTime.now());
    }

    private void logEvent(String action, FarmModel model, FarmCollectResponse response) {
        System.out.println("WS: " + LocalTime.now() + " " + action + ": " + model.getFarmDto().getType() + " id: " + model.getFarmDto().getId());
        System.out.println(response);
    }

    private boolean shouldStartNewEvent(FarmModel model) {
        return Objects.isNull(model.getStartDateTime());
    }

    private void startNewEvent(FarmModel model, String entity) {
        String farmRun = farmBullClient.farmRun(model.getFarmDto());
        logEvent(entity, model, farmRun);
        model.setStartDateTime(LocalDateTime.now());
    }

    private void logEvent(String action, FarmModel model, String response) {
        System.out.println("WS: " + LocalTime.now() + " " + action + ": " + model.getFarmDto().getType() + " id: " + model.getFarmDto().getId());
        System.out.println(response);
    }
}
