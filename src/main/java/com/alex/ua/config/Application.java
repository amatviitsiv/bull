package com.alex.ua.config;

import com.alex.ua.client.FarmBullClientImpl;
import com.alex.ua.client.delivery.model.DeliveryModel;
import com.alex.ua.client.delivery.model.tap.AnimalTapModel;
import com.alex.ua.client.farm.model.FarmModel;
import com.alex.ua.provider.AnimalsProvider;
import com.alex.ua.provider.DeliveryObjectProvider;
import com.alex.ua.provider.FarmObjectProviderV2;
import com.alex.ua.service.DeliveryService;
import com.alex.ua.service.EddyService;
import com.alex.ua.service.FarmServiceV2;
import com.alex.ua.service.TapService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication(
        scanBasePackages = {"com.alex.ua"}
)
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // Get the FarmService bean from the application context
        FarmServiceV2 farmService = context.getBean(FarmServiceV2.class);
        DeliveryService deliveryService = context.getBean(DeliveryService.class);
        EddyService eddyService = context.getBean(EddyService.class);
        TapService tapService = context.getBean(TapService.class);
        FarmBullClientImpl farmBullClient = context.getBean(FarmBullClientImpl.class);

        DeliveryObjectProvider deliveryObjectProvider = new DeliveryObjectProvider();
        LinkedList<DeliveryModel> burundiModels = deliveryObjectProvider.getBurundiModels();
        LinkedList<DeliveryModel> laosModels = deliveryObjectProvider.getLaosModels();
        LinkedList<DeliveryModel> ugandaModels = deliveryObjectProvider.getUgandaModels();
        LinkedList<DeliveryModel> moldovaModels = deliveryObjectProvider.getMoldovaModels();

        AnimalsProvider animalsProvider = new AnimalsProvider();
        LinkedList<AnimalTapModel> animals = animalsProvider.getAnimals();

        FarmObjectProviderV2 farmObjectProviderV2 = new FarmObjectProviderV2();
        LinkedList<FarmModel> farmModelList = farmObjectProviderV2.getFarmModelList();
        farmService.initializeFarm(farmObjectProviderV2, deliveryObjectProvider, animals);

        LocalDateTime tapBoxDate = null;
        AtomicReference<LocalDateTime> animalTaps = new AtomicReference<>(LocalDateTime.now());


        //eddyService.rollEddy();

        do {
            if (Objects.isNull(tapBoxDate) || tapBoxDate.plusSeconds(3333).isBefore(LocalDateTime.now())) {
                try {
                    tapBoxDate = tapService.tapBox();
                } catch (Exception exception) {
                    System.out.println("Exception while box tapping");
                }
            }

            if (animalTaps.get().plusSeconds(352).isBefore(LocalDateTime.now())) {
                animals.forEach(animal -> animalTaps.set(tapService.tapAnimal(animal.getDto())));
            }

            try {
                animals.forEach(animal -> tapService.collectAnimal(animal, farmObjectProviderV2.getFarmModelList()));
            } catch (Exception exception) {
                System.out.println("Exception while animal collecting");
            }

            farmModelList.forEach(farmModel -> farmService.runFarmEvent(farmModel, farmObjectProviderV2));

            burundiModels.forEach(bur -> deliveryService.runBurundiEvent(bur, farmObjectProviderV2.getFarmModelList()));
            ugandaModels.forEach(uga -> deliveryService.runUgandaEvent(uga, farmObjectProviderV2.getFarmModelList()));
            laosModels.forEach(lao -> deliveryService.runLaosEvent(lao, farmObjectProviderV2.getFarmModelList()));
            //moldovaModels.forEach(mol -> deliveryService.runMoldovaEvent(mol, farmObjectProviderV2.getFarmModelList()));

            try {
                Thread.sleep(10000); // 60000 milliseconds = 1 minute
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread was interrupted: " + e.getMessage());
                return;
            }
        } while (true);

        /*FarmDto wh = new FarmDto("crops", "pc");
        //BoosterDto whBooster = new BoosterDto("ad", "pc", "crops");
        BoosterDto whBooster = new BoosterDto("booster", "pc", "crops");

        for (int i = 0; i < 6; i++) {
            farmBullClient.farmRun(wh);
            farmBullClient.boostRun(whBooster);
            farmBullClient.boostRun(whBooster);
            farmBullClient.boostRun(whBooster);
            farmBullClient.farmCollect(wh);
        }
        System.out.println("DONE");*/
    }
}
