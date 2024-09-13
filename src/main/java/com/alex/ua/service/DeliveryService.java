package com.alex.ua.service;

import com.alex.ua.client.ContinentFarmClient;
import com.alex.ua.client.delivery.model.DeliveryModel;
import com.alex.ua.client.delivery.model.RequiredAttribute;
import com.alex.ua.client.delivery.model.burundi.BurundiCollectResponse;
import com.alex.ua.client.delivery.model.laos.LaosCollectResponse;
import com.alex.ua.client.delivery.model.moldova.MoldovaCollectResponse;
import com.alex.ua.client.delivery.model.uganda.UgandaCollectResponse;
import com.alex.ua.client.farm.model.FarmModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.alex.ua.util.ColorUtils.GREEN;
import static com.alex.ua.util.ColorUtils.RESET;

@Service
public class DeliveryService {

    @Autowired
    private ContinentFarmClient continentFarmClient;

    public void runUgandaEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        if (shouldCollect(model)) {
            collectUganda(model);
        } else if (shouldStartNewEvent(model, allFarmModels)) {
            startNewEvent(model, allFarmModels);
        }
    }

    public void runLaosEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        if (shouldCollect(model)) {
            collectLaos(model);
        } else if (shouldStartNewEvent(model, allFarmModels)) {
            startNewEvent(model, allFarmModels);
        }
    }

    public void runBurundiEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        if (shouldCollect(model)) {
            collectBurundi(model);
        } else if (shouldStartNewEvent(model, allFarmModels)) {
            startNewEvent(model, allFarmModels);
        }
    }

    public void runMoldovaEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        if (shouldCollect(model)) {
            collectMoldova(model);
        } else if (shouldStartNewEvent(model, allFarmModels)) {
            startNewEvent(model, allFarmModels);
        }
    }

    private void collectUganda(DeliveryModel model) {
        UgandaCollectResponse ugandaCollectResponse = continentFarmClient.ugandaCollect(model.getDeliveryDto());
        System.out.println(GREEN + "collected uganda " + RESET);
        model.setStartDateTime(null);
        UgandaCollectResponse.UgrqItem ugrqItem = ugandaCollectResponse.getUgrq().get(model.getDeliveryDto().getRid());
        Map<String, Integer> requires = ugrqItem.getRequires();
        model.getRequired().clear();
        for (Map.Entry<String, Integer> entry : requires.entrySet()) {
            model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
        }
    }

    private void collectLaos(DeliveryModel model) {
        LaosCollectResponse laosCollectResponse = continentFarmClient.laosCollect(model.getDeliveryDto());
        System.out.println(GREEN + "collected laos " + RESET);
        model.setStartDateTime(null);
        LaosCollectResponse.LarqItem ugrqItem = laosCollectResponse.getLarq().get(model.getDeliveryDto().getRid());
        Map<String, Integer> requires = ugrqItem.getRequires();
        model.getRequired().clear();
        for (Map.Entry<String, Integer> entry : requires.entrySet()) {
            model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
        }
    }

    private void collectBurundi(DeliveryModel model) {
        BurundiCollectResponse burundiCollectResponse = continentFarmClient.burundiCollect(model.getDeliveryDto());
        System.out.println(GREEN + "collected burundi " + RESET);
        model.setStartDateTime(null);
        BurundiCollectResponse.BrrqItem ugrqItem = burundiCollectResponse.getBrrq().get(model.getDeliveryDto().getRid());
        Map<String, Integer> requires = ugrqItem.getRequires();
        model.getRequired().clear();
        for (Map.Entry<String, Integer> entry : requires.entrySet()) {
            model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
        }
    }

    private void collectMoldova(DeliveryModel model) {
        MoldovaCollectResponse moldovaCollectResponse = continentFarmClient.moldovaCollect(model.getDeliveryDto());
        System.out.println(GREEN + "collected moldova " + RESET);
        model.setStartDateTime(null);
        MoldovaCollectResponse.MdrqItem ugrqItem = moldovaCollectResponse.getMdrq().get(model.getDeliveryDto().getRid());
        Map<String, Integer> requires = ugrqItem.getRequires();
        model.getRequired().clear();
        for (Map.Entry<String, Integer> entry : requires.entrySet()) {
            model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
        }
    }

    private boolean shouldStartNewEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        return Objects.isNull(model.getStartDateTime()) && model.getRequired().stream().allMatch(required -> {
            FarmModel farmModel = allFarmModels.stream()
                    .filter(farm -> farm.getFarmDto().getId().equals(required.getId()))
                    .findFirst()
                    .orElseThrow();
            return farmModel.getStoredAmount() > required.getAmount();
        });
    }

    private void startNewEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        continentFarmClient.farmRun(model.getDeliveryDto());
        System.out.println(GREEN + "DS: " + LocalTime.now() + " started delivery for: " + model.getDeliveryDto().getId() + " " + model.getDeliveryDto().getRid() + RESET);
        model.setStartDateTime(LocalDateTime.now());
        model.getRequired().forEach(required -> {
            FarmModel farmModel = allFarmModels.stream()
                    .filter(farm -> farm.getFarmDto().getId().equals(required.getId()))
                    .findFirst()
                    .orElseThrow();
            System.out.println(GREEN + " was stored amount: " + farmModel.getFarmDto().getId() + " " + farmModel.getStoredAmount());
            farmModel.setStoredAmount(farmModel.getStoredAmount() - required.getAmount());
            System.out.println(GREEN + " set stored amount: " + farmModel.getFarmDto().getId() + " " + farmModel.getStoredAmount());
        });
    }

    private boolean shouldCollect(DeliveryModel model) {
        return Objects.nonNull(model.getStartDateTime())
                && model.getStartDateTime().plusMinutes(model.getDeliveryTime()).isBefore(LocalDateTime.now());
    }


}
