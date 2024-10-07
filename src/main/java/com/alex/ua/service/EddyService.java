package com.alex.ua.service;

import com.alex.ua.client.FarmBullClientImpl;
import com.alex.ua.client.delivery.model.eddy.EddyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EddyService {
    @Autowired
    private FarmBullClientImpl client;

    public void rollEddy() {
        int money = 0;
        EddyResponse response;
        do {
            response = client.rollEddy();
            money = response.getResult().getC();
        } while (money < 100000000);
        System.out.println(response.getRequires());
        System.out.println(money);
    }
}
