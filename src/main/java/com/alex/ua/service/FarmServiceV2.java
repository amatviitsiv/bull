package com.alex.ua.service;

import com.alex.ua.client.AuthClient;
import com.alex.ua.client.FarmBullClientImpl;
import com.alex.ua.client.auth.AuthenticateResponse;
import com.alex.ua.client.collection.CollectionModel;
import com.alex.ua.client.delivery.model.DeliveryModel;
import com.alex.ua.client.delivery.model.RequiredAttribute;
import com.alex.ua.client.delivery.model.burundi.BurundiCollectResponse;
import com.alex.ua.client.delivery.model.finland.FinlandCollectResponse;
import com.alex.ua.client.delivery.model.laos.LaosCollectResponse;
import com.alex.ua.client.delivery.model.moldova.MoldovaCollectResponse;
import com.alex.ua.client.delivery.model.serbiya.SerbiaCollectResponse;
import com.alex.ua.client.delivery.model.tap.AnimalTapModel;
import com.alex.ua.client.delivery.model.uganda.UgandaCollectResponse;
import com.alex.ua.client.farm.model.FarmCollectResponse;
import com.alex.ua.client.farm.model.FarmModel;
import com.alex.ua.client.farm.model.RunResponse;
import com.alex.ua.provider.DeliveryObjectProvider;
import com.alex.ua.provider.FarmObjectProviderV2;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static final int MAX_PLOTS = 14; // Максимальное количество грядок
    private final AtomicInteger activePlots = new AtomicInteger(0); // Счётчик активных грядок
    private final Set<String> activeCrops = new HashSet<>();

    @Autowired
    private FarmBullClientImpl farmBullClient;
    @Autowired
    private AuthClient authClient;
    @Autowired
    private ObjectMapper objectMapper;

    public void initializeFarm(FarmObjectProviderV2 providerV2, DeliveryObjectProvider deliveryObjectProvider,
                               LinkedList<AnimalTapModel> animals, List<CollectionModel> collectionModels) {
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

        animals.forEach(model -> {
            model.setCollectTime(
                    LocalDateTime.ofInstant(
                            Instant.ofEpochSecond((int) user.get(
                                    model.getAnimalPrefix() + model.getDto().getAnimal_idx() + model.getCollectPostfix())), ZoneId.systemDefault()));
            Map<String, Integer> required = (Map<String, Integer>) user.get(model.getAnimalPrefix() + model.getDto().getAnimal_idx() + model.getRequiredPostfix());
            for (Map.Entry<String, Integer> entry : required.entrySet()) {
                model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
            }
        });

        collectionModels.forEach(model -> {
            Object collect = user.get(model.getDto().getId() + "e");
            if (Objects.nonNull(collect) && (int) collect != 0) {
                model.setCollectDateTime(
                        LocalDateTime.ofInstant(
                                Instant.ofEpochSecond((int) collect), ZoneId.systemDefault()));
            }
        });


        fillBurundiDeliveryState(user, deliveryObjectProvider);
        fillUgandaDeliveryState(user, deliveryObjectProvider);
        fillLaosDeliveryState(user, deliveryObjectProvider);
        fillMoldovaDeliveryState(user, deliveryObjectProvider);
        fillSerbiaDeliveryState(user, deliveryObjectProvider);
        fillFinlandDeliveryState(user, deliveryObjectProvider);
        System.out.println(PURPLE + " Finished initializing Farm. " + RESET);
    }

    private void fillLaosDeliveryState(Map<String, Object> user, DeliveryObjectProvider deliveryObjectProvider) {
        List<LaosCollectResponse.LarqItem> larq;
        Object larqObject = user.get("larq");

        if (larqObject instanceof List) {
            larq = objectMapper.convertValue(larqObject, new TypeReference<>() {
            });
            LinkedList<DeliveryModel> laosModels = deliveryObjectProvider.getLaosModels();
            laosModels.forEach(model -> {
                LaosCollectResponse.LarqItem larqItem = larq.get(model.getDeliveryDto().getRid());
                Map<String, Integer> requires = larqItem.getRequires();
                fillRequires(requires, model);
                if (Objects.nonNull(larqItem.getLae())) {
                    model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(larqItem.getLae()), ZoneId.systemDefault()));
                }
            });
        }
    }

    private void fillBurundiDeliveryState(Map<String, Object> user, DeliveryObjectProvider deliveryObjectProvider) {
        List<BurundiCollectResponse.BrrqItem> brrq;
        Object brrqObj = user.get("brrq");

        // Check if the 'brrq' field is present and is a List
        if (brrqObj instanceof List) {
            // Use ObjectMapper to convert the list of maps to a list of BrrqItems
            brrq = objectMapper.convertValue(brrqObj, new TypeReference<>() {
            });
            LinkedList<DeliveryModel> laosModels = deliveryObjectProvider.getBurundiModels();
            laosModels.forEach(model -> {
                BurundiCollectResponse.BrrqItem brrqItem = brrq.get(model.getDeliveryDto().getRid());
                Map<String, Integer> requires = brrqItem.getRequires();
                fillRequires(requires, model);
                if (Objects.nonNull(brrqItem.getBre())) {
                    model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(brrqItem.getBre()), ZoneId.systemDefault()));
                }
            });
        }
    }

    private void fillMoldovaDeliveryState(Map<String, Object> user, DeliveryObjectProvider deliveryObjectProvider) {
        List<MoldovaCollectResponse.MdrqItem> mdrq;
        Object mdrqObject = user.get("mdrq");

        if (mdrqObject instanceof List) {
            mdrq = objectMapper.convertValue(mdrqObject, new TypeReference<>() {
            });
            LinkedList<DeliveryModel> moldovaModels = deliveryObjectProvider.getMoldovaModels();
            moldovaModels.forEach(model -> {
                MoldovaCollectResponse.MdrqItem mdrqItem = mdrq.get(model.getDeliveryDto().getRid());
                Map<String, Integer> requires = mdrqItem.getRequires();
                fillRequires(requires, model);
                if (Objects.nonNull(mdrqItem.getMde())) {
                    model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(mdrqItem.getMde()), ZoneId.systemDefault()));
                }
            });
        }
    }

    private void fillUgandaDeliveryState(Map<String, Object> user, DeliveryObjectProvider deliveryObjectProvider) {
        List<UgandaCollectResponse.UgrqItem> ugrq;
        Object ugrqObject = user.get("ugrq");

        if (ugrqObject instanceof List) {
            ugrq = objectMapper.convertValue(ugrqObject, new TypeReference<>() {
            });
            LinkedList<DeliveryModel> ugandaModels = deliveryObjectProvider.getUgandaModels();
            ugandaModels.forEach(model -> {
                UgandaCollectResponse.UgrqItem ugrqItem = ugrq.get(model.getDeliveryDto().getRid());
                Map<String, Integer> requires = ugrqItem.getRequires();
                fillRequires(requires, model);
                if (Objects.nonNull(ugrqItem.getUge())) {
                    model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(ugrqItem.getUge()), ZoneId.systemDefault()));
                }
            });
        }
    }

    private void fillSerbiaDeliveryState(Map<String, Object> user, DeliveryObjectProvider deliveryObjectProvider) {
        List<SerbiaCollectResponse.SbrqItem> sbrq;
        Object sbrqObject = user.get("sbrq");

        if (sbrqObject instanceof List) {
            sbrq = objectMapper.convertValue(sbrqObject, new TypeReference<>() {
            });
            LinkedList<DeliveryModel> serbiaModels = deliveryObjectProvider.getSerbiyaModels();
            serbiaModels.forEach(model -> {
                SerbiaCollectResponse.SbrqItem sbrqItem = sbrq.get(model.getDeliveryDto().getRid());
                Map<String, Integer> requires = sbrqItem.getRequires();
                fillRequires(requires, model);
                if (Objects.nonNull(sbrqItem.getSbe())) {
                    model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(sbrqItem.getSbe()), ZoneId.systemDefault()));
                }
            });
        }
    }

    private void fillFinlandDeliveryState(Map<String, Object> user, DeliveryObjectProvider deliveryObjectProvider) {
        List<FinlandCollectResponse.FirqItem> firq;
        Object firqObject = user.get("firq");

        if (firqObject instanceof List) {
            firq = objectMapper.convertValue(firqObject, new TypeReference<>() {
            });
            LinkedList<DeliveryModel> finlandModels = deliveryObjectProvider.getFinlandModels();
            finlandModels.forEach(model -> {
                FinlandCollectResponse.FirqItem firqItem = firq.get(model.getDeliveryDto().getRid());
                Map<String, Integer> requires = firqItem.getRequires();
                fillRequires(requires, model);
                if (Objects.nonNull(firqItem.getFie())) {
                    model.setCollectDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(firqItem.getFie()), ZoneId.systemDefault()));
                }
            });
        }
    }

    private void fillRequires(Map<String, Integer> requires, DeliveryModel model) {
        model.getRequired().clear();
        for (Map.Entry<String, Integer> entry : requires.entrySet()) {
            model.getRequired().add(new RequiredAttribute(entry.getKey(), entry.getValue()));
        }
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
        attemptLogEvent(model, "collect");
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
        attemptLogEvent(model, "start");
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

    private void attemptLogEvent(FarmModel model, String action) {
        System.out.println(RED + "ATTEMPT to " + action + ": " + model.getFarmDto().getId());
    }
}

