package com.alex.ua.service;

import com.alex.ua.client.ContinentFarmClient;
import com.alex.ua.client.delivery.model.DeliveryModel;
import com.alex.ua.client.delivery.model.RequiredAttribute;
import com.alex.ua.client.delivery.model.burundi.BurundiCollectResponse;
import com.alex.ua.client.delivery.model.finland.FinlandCollectResponse;
import com.alex.ua.client.delivery.model.laos.LaosCollectResponse;
import com.alex.ua.client.delivery.model.moldova.MoldovaCollectResponse;
import com.alex.ua.client.delivery.model.serbiya.SerbiaCollectResponse;
import com.alex.ua.client.delivery.model.uganda.UgandaCollectResponse;
import com.alex.ua.client.farm.model.FarmModel;
import com.alex.ua.client.farm.model.RunResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.alex.ua.util.ColorUtils.GREEN;
import static com.alex.ua.util.ColorUtils.RESET;

@Service
public class DeliveryService {

    @Autowired
    private ContinentFarmClient continentFarmClient;
    @Autowired
    private ObjectMapper objectMapper;

    public void runUgandaEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        if (shouldCollect(model)) {
            collectUganda(model);
        } else if (shouldStartNewEvent(model, allFarmModels)) {
            startNewUgandaEvent(model, allFarmModels);
        }
    }

    public void runLaosEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        if (shouldCollect(model)) {
            collectLaos(model);
        } else if (shouldStartNewEvent(model, allFarmModels)) {
            startNewLaosEvent(model, allFarmModels);
        }
    }

    public void runBurundiEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        if (shouldCollect(model)) {
            collectBurundi(model);
        } else if (shouldStartNewEvent(model, allFarmModels)) {
            startNewBurundiEvent(model, allFarmModels);
        }
    }

    public void runMoldovaEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        if (shouldCollect(model)) {
            collectMoldova(model);
        } else if (shouldStartNewEvent(model, allFarmModels)) {
            startNewMoldovaEvent(model, allFarmModels);
        }
    }

    public void runSerbiaEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        if (shouldCollect(model)) {
            collectSerbia(model);
        } else if (shouldStartNewEvent(model, allFarmModels)) {
            startNewSerbiaEvent(model, allFarmModels);
        }
    }

    public void runFinlandEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        if (shouldCollect(model)) {
            collectFinland(model);
        } else if (shouldStartNewEvent(model, allFarmModels)) {
            startNewFinlandEvent(model, allFarmModels);
        }
    }

    private void collectUganda(DeliveryModel model) {
        ParameterizedTypeReference<UgandaCollectResponse> type = new ParameterizedTypeReference<>() {};
        UgandaCollectResponse ugandaCollectResponse = continentFarmClient.collect(model.getDeliveryDto(), type);
        System.out.println(GREEN + "collected uganda " + RESET);
        model.setCollectDateTime(null);
        UgandaCollectResponse.UgrqItem ugrqItem = ugandaCollectResponse.getUgrq().get(model.getDeliveryDto().getRid());
        Map<String, Integer> requires = ugrqItem.getRequires();
        model.getRequired().clear();
        for (Map.Entry<String, Integer> entry : requires.entrySet()) {
            model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
        }
    }

    private void collectLaos(DeliveryModel model) {
        ParameterizedTypeReference<LaosCollectResponse> type = new ParameterizedTypeReference<>() {};
        LaosCollectResponse laosCollectResponse = continentFarmClient.collect(model.getDeliveryDto(), type);
        System.out.println(GREEN + "collected laos " + RESET);
        model.setCollectDateTime(null);
        LaosCollectResponse.LarqItem ugrqItem = laosCollectResponse.getLarq().get(model.getDeliveryDto().getRid());
        Map<String, Integer> requires = ugrqItem.getRequires();
        model.getRequired().clear();
        for (Map.Entry<String, Integer> entry : requires.entrySet()) {
            model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
        }
    }

    private void collectBurundi(DeliveryModel model) {
        ParameterizedTypeReference<BurundiCollectResponse> type = new ParameterizedTypeReference<>() {};
        BurundiCollectResponse burundiCollectResponse = continentFarmClient.collect(model.getDeliveryDto(), type);
        System.out.println(GREEN + "collected burundi " + RESET);
        model.setCollectDateTime(null);
        BurundiCollectResponse.BrrqItem ugrqItem = burundiCollectResponse.getBrrq().get(model.getDeliveryDto().getRid());
        Map<String, Integer> requires = ugrqItem.getRequires();
        model.getRequired().clear();
        for (Map.Entry<String, Integer> entry : requires.entrySet()) {
            model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
        }
    }

    private void collectMoldova(DeliveryModel model) {
        ParameterizedTypeReference<MoldovaCollectResponse> type = new ParameterizedTypeReference<>() {};
        MoldovaCollectResponse moldovaCollectResponse = continentFarmClient.collect(model.getDeliveryDto(), type);
        System.out.println(GREEN + "collected moldova " + RESET);
        model.setCollectDateTime(null);
        MoldovaCollectResponse.MdrqItem ugrqItem = moldovaCollectResponse.getMdrq().get(model.getDeliveryDto().getRid());
        Map<String, Integer> requires = ugrqItem.getRequires();
        model.getRequired().clear();
        for (Map.Entry<String, Integer> entry : requires.entrySet()) {
            model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
        }
    }

    private void collectSerbia(DeliveryModel model) {
        ParameterizedTypeReference<SerbiaCollectResponse> type = new ParameterizedTypeReference<>() {};
        SerbiaCollectResponse serbiaCollectResponse = continentFarmClient.collect(model.getDeliveryDto(), type);
        System.out.println(GREEN + "collected serbia " + RESET);
        model.setCollectDateTime(null);
        SerbiaCollectResponse.SbrqItem sbrqItem = serbiaCollectResponse.getSbrq().get(model.getDeliveryDto().getRid());
        Map<String, Integer> requires = sbrqItem.getRequires();
        model.getRequired().clear();
        for (Map.Entry<String, Integer> entry : requires.entrySet()) {
            model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
        }
    }

    private void collectFinland(DeliveryModel model) {
        ParameterizedTypeReference<FinlandCollectResponse> type = new ParameterizedTypeReference<>() {};
        FinlandCollectResponse moldovaCollectResponse = continentFarmClient.collect(model.getDeliveryDto(), type);
        System.out.println(GREEN + "collected finland " + RESET);
        model.setCollectDateTime(null);
        FinlandCollectResponse.FirqItem firqItem = moldovaCollectResponse.getFirq().get(model.getDeliveryDto().getRid());
        Map<String, Integer> requires = firqItem.getRequires();
        model.getRequired().clear();
        for (Map.Entry<String, Integer> entry : requires.entrySet()) {
            model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
        }
    }

    private boolean shouldStartNewEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        return Objects.isNull(model.getCollectDateTime()) && model.getRequired().stream().allMatch(required -> {
            FarmModel farmModel = allFarmModels.stream()
                    .filter(farm -> farm.getFarmDto().getId().equals(required.getId()))
                    .findFirst()
                    .orElseThrow();
            System.out.println(GREEN + "DS: new event require: " + farmModel.getFarmDto().getId() + ": stored: " + farmModel.getStoredAmount() + " required: " + required.getAmount());
            return farmModel.getStoredAmount() > required.getAmount();
        });
    }

    private void startNewBurundiEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        RunResponse runResponse = continentFarmClient.farmRun(model.getDeliveryDto());
        Object brrqObj = runResponse.getProperties().get("brrq");

        // Check if the 'brrq' field is present and is a List
        if (brrqObj instanceof List) {
            // Use ObjectMapper to convert the list of maps to a list of BrrqItems
            List<BurundiCollectResponse.BrrqItem> brrq = objectMapper.convertValue(brrqObj, new TypeReference<>() {
            });
            model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(brrq.get(model.getDeliveryDto().getRid()).getBre()), ZoneId.systemDefault()));
            fillRequiredResources(model, allFarmModels);
        }
    }

    private void startNewUgandaEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        RunResponse runResponse = continentFarmClient.farmRun(model.getDeliveryDto());
        Object ugrqObject = runResponse.getProperties().get("ugrq");

        if (ugrqObject instanceof List) {
            List<UgandaCollectResponse.UgrqItem> ugrq = objectMapper.convertValue(ugrqObject, new TypeReference<>() {
            });
            model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(ugrq.get(model.getDeliveryDto().getRid()).getUge()), ZoneId.systemDefault()));
            fillRequiredResources(model, allFarmModels);
        }
    }

    private void startNewLaosEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        RunResponse runResponse = continentFarmClient.farmRun(model.getDeliveryDto());
        Object larqObject = runResponse.getProperties().get("larq");

        if (larqObject instanceof List) {
            List<LaosCollectResponse.LarqItem> larq = objectMapper.convertValue(larqObject, new TypeReference<>() {});
            model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(larq.get(model.getDeliveryDto().getRid()).getLae()), ZoneId.systemDefault()));
            fillRequiredResources(model, allFarmModels);
        }
    }

    private void startNewMoldovaEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        RunResponse runResponse = continentFarmClient.farmRun(model.getDeliveryDto());
        Object mdrqObject = runResponse.getProperties().get("mdrq");

        if (mdrqObject instanceof List) {
            List<MoldovaCollectResponse.MdrqItem> mdrq = objectMapper.convertValue(mdrqObject, new TypeReference<>() {
            });
            model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(mdrq.get(model.getDeliveryDto().getRid()).getMde()), ZoneId.systemDefault()));
            fillRequiredResources(model, allFarmModels);
        }
    }

    private void startNewSerbiaEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        RunResponse runResponse = continentFarmClient.farmRun(model.getDeliveryDto());
        Object sbrqObject = runResponse.getProperties().get("sbrq");

        if (sbrqObject instanceof List) {
            List<SerbiaCollectResponse.SbrqItem> sbrq = objectMapper.convertValue(sbrqObject, new TypeReference<>() {
            });
            model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(sbrq.get(model.getDeliveryDto().getRid()).getSbe()), ZoneId.systemDefault()));
            fillRequiredResources(model, allFarmModels);
        }
    }

    private void startNewFinlandEvent(DeliveryModel model, List<FarmModel> allFarmModels) {
        RunResponse runResponse = continentFarmClient.farmRun(model.getDeliveryDto());
        Object firqObject = runResponse.getProperties().get("firq");

        if (firqObject instanceof List) {
            List<FinlandCollectResponse.FirqItem> firq = objectMapper.convertValue(firqObject, new TypeReference<>() {
            });
            model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(firq.get(model.getDeliveryDto().getRid()).getFie()), ZoneId.systemDefault()));
            fillRequiredResources(model, allFarmModels);
        }
    }

    private static void fillRequiredResources(DeliveryModel model, List<FarmModel> allFarmModels) {
        System.out.println(GREEN + "DS: " + LocalTime.now() + " started delivery for: " + model.getDeliveryDto().getId() + " " + model.getDeliveryDto().getRid() + RESET);
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
        return Objects.nonNull(model.getCollectDateTime())
                && model.getCollectDateTime().plusSeconds(10).isBefore(LocalDateTime.now());
    }


}
