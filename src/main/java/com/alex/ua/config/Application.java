package com.alex.ua.config;

import com.alex.ua.client.FarmBullClientImpl;
import com.alex.ua.client.delivery.model.DeliveryModel;
import com.alex.ua.client.farm.booster.BoosterDto;
import com.alex.ua.client.farm.model.FarmDto;
import com.alex.ua.client.farm.model.FarmModel;
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

        /*//burundiModels.forEach(model -> model.setStartDateTime(LocalDateTime.now().minusMinutes(120)));
        ugandaModels.forEach(model -> model.setStartDateTime(LocalDateTime.now().minusMinutes(150)));
        laosModels.forEach(model -> model.setStartDateTime(LocalDateTime.now().minusMinutes(130)));
        moldovaModels.forEach(model -> model.setStartDateTime(LocalDateTime.now().minusMinutes(310)));*/

        FarmObjectProviderV2 farmObjectProviderV2 = new FarmObjectProviderV2();
        LinkedList<FarmModel> farmModelList = farmObjectProviderV2.getFarmModelList();
        farmService.initializeFarm(farmObjectProviderV2, deliveryObjectProvider);
        LocalDateTime tapBoxDate = null;


        //eddyService.rollEddy();

        do {
            if (Objects.isNull(tapBoxDate) || tapBoxDate.plusSeconds(3333).isBefore(LocalDateTime.now())) {
                tapBoxDate = tapService.tapBox();
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
