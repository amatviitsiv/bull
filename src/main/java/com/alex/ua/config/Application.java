package com.alex.ua.config;

import com.alex.ua.client.delivery.model.DeliveryModel;
import com.alex.ua.client.farm.model.FarmModel;
import com.alex.ua.provider.DeliveryObjectProvider;
import com.alex.ua.provider.FarmObjectProviderV2;
import com.alex.ua.service.DeliveryService;
import com.alex.ua.service.FarmServiceV2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.LinkedList;

@SpringBootApplication(
        scanBasePackages = {"com.alex.ua"}
)
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // Get the FarmService bean from the application context
        FarmServiceV2 farmService = context.getBean(FarmServiceV2.class);
        DeliveryService deliveryService = context.getBean(DeliveryService.class);
        //FarmBullClientImpl farmBullClient = context.getBean(FarmBullClientImpl.class);

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
        farmService.initializeFarm(farmObjectProviderV2);

        do {
            farmModelList.forEach(farmModel -> farmService.runFarmEvent(farmModel, farmObjectProviderV2));

            //burundiModels.forEach(bur -> deliveryService.runBurundiEvent(bur, farmObjectProviderV2.getFarmModelList()));
            //ugandaModels.forEach(uga -> deliveryService.runUgandaEvent(uga, farmObjectProviderV2.getFarmModelList()));
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

        /*FarmDto wh = new FarmDto("crops", "wh");
        BoosterDto whBooster = new BoosterDto("booster", "wh", "crops");

        for (int i = 0; i < 550; i++) {
            System.out.println("started");
            farmBullClient.farmRun(wh);
            System.out.println("started");
            farmBullClient.boostRun(whBooster);
            System.out.println("boost 1");
            farmBullClient.boostRun(whBooster);
            System.out.println("boost 2");
            farmBullClient.farmCollect(wh);
            System.out.println("collected");
        }*/
    }
}
