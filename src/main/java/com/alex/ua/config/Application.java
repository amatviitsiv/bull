package com.alex.ua.config;

import com.alex.ua.client.FarmBullClientImpl;
import com.alex.ua.client.delivery.model.DeliveryModel;
import com.alex.ua.client.farm.booster.BoosterDto;
import com.alex.ua.client.farm.model.FarmDto;
import com.alex.ua.client.farm.model.FarmModel;
import com.alex.ua.provider.DeliveryObjectProvider;
import com.alex.ua.provider.FarmObjectProvider;
import com.alex.ua.service.DeliveryService;
import com.alex.ua.service.FarmService;
import com.alex.ua.service.WorkShopService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.LinkedList;

@SpringBootApplication(
        scanBasePackages = {"com.alex.ua"}
)
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // Get the FarmService bean from the application context
        FarmService farmService = context.getBean(FarmService.class);
        WorkShopService workShopService = context.getBean(WorkShopService.class);
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

        FarmObjectProvider objectProvider = new FarmObjectProvider();
        LinkedList<FarmModel> farmModelList = objectProvider.getFarmModelList();

        LinkedList<FarmModel> workshopModelList = objectProvider.getWorkshopModelList();
        FarmModel workshopModelForCycle = workshopModelList.get(0);

        LinkedList<FarmModel> workshopKitchenModelList = objectProvider.getWorkshopKitchenModelList();
        FarmModel workshopKitchenModelForCycle = workshopKitchenModelList.get(0);

        LinkedList<FarmModel> workshopFactoryModelList = objectProvider.getWorkshopFactoryModelList();
        FarmModel workshopFactoryModelForCycle = workshopFactoryModelList.get(0);

        do {
            farmModelList.forEach(farmService::runFarmEvent);

            /*//Bakaly
            if (workShopService.shouldCollect(workshopModelForCycle)) {
                workShopService.collect(workshopModelForCycle);
                workshopModelForCycle = workshopModelForCycle.getNext();
            } else {
                workShopService.runFarmEvent(workshopModelForCycle);
            }*/

            /*//Kitchen
            if (workShopService.shouldCollect(workshopKitchenModelForCycle)) {
                workShopService.collect(workshopKitchenModelForCycle);
                workshopKitchenModelForCycle = workshopKitchenModelForCycle.getNext();
            } else {
                workShopService.runFarmEvent(workshopKitchenModelForCycle);
            }

            //Factory
            if (workShopService.shouldCollect(workshopFactoryModelForCycle)) {
                workShopService.collect(workshopFactoryModelForCycle);
                workshopFactoryModelForCycle = workshopFactoryModelForCycle.getNext();
            } else {
                workShopService.runFarmEvent(workshopFactoryModelForCycle);
            }*/

            burundiModels.forEach(bur -> deliveryService.runBurundiEvent(bur, objectProvider.getAllFarmModels()));
            //ugandaModels.forEach(uga -> deliveryService.runUgandaEvent(uga, objectProvider.getAllFarmModels()));
            /*laosModels.forEach(lao -> deliveryService.runLaosEvent(lao, objectProvider.getAllFarmModels()));
            moldovaModels.forEach(mol -> deliveryService.runMoldovaEvent(mol, objectProvider.getWorkshopModelList()));*/

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
