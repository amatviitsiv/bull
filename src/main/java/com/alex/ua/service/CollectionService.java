package com.alex.ua.service;

import com.alex.ua.client.CollectionClient;
import com.alex.ua.client.collection.CollectionModel;
import com.alex.ua.client.collection.CollectionRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import static com.alex.ua.util.ColorUtils.GREEN;
import static com.alex.ua.util.ColorUtils.RESET;

@Service
public class CollectionService {
    @Autowired
    private CollectionClient client;

    public void run(CollectionModel model) {
        if (shouldCollect(model)) {
            collect(model);
        } else if (shouldStartNewEvent(model)) {
            startNewEvent(model);
        }
    }

    private void collect(CollectionModel model) {
        CollectionRequestResponse collectionRequestResponse = client.collectionCollect(model.getDto());
        System.out.println(GREEN + "collection collected: " + model.getDto().getId() + RESET);
        model.setCollectDateTime(null);
    }

    private void startNewEvent(CollectionModel model) {
        CollectionRequestResponse collectionRequestResponse = client.collectionRun(model.getRequestDto());
        long timeToCollect = 0;
        switch (model.getDto().getId()) {
            case ("ba") :
                timeToCollect = collectionRequestResponse.getBae();
                break;
            case ("mn") :
                timeToCollect = collectionRequestResponse.getMne();
                break;
            case ("lk") :
                timeToCollect = collectionRequestResponse.getLke();
                break;
            case ("fr") :
                timeToCollect = collectionRequestResponse.getFre();
                break;
        }
        model.setCollectDateTime(LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timeToCollect), ZoneId.systemDefault()));
        System.out.println(GREEN + "collection started: " + model.getDto().getId() + RESET);
    }

    private boolean shouldCollect(CollectionModel model) {
        return Objects.nonNull(model.getCollectDateTime())
                && model.getCollectDateTime().plusSeconds(10).isBefore(LocalDateTime.now());
    }

    private boolean shouldStartNewEvent(CollectionModel model) {
        return Objects.isNull(model.getCollectDateTime());
    }
}
