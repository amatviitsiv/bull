package com.alex.ua.service;

import com.alex.ua.client.FarmBullClientImpl;
import com.alex.ua.client.delivery.model.eddy.EddyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EddyService {
    @Autowired
    private FarmBullClientImpl client;

    public void rollEddy() {
        int money = 0;
        int count = 0;
        EddyResponse response;
        Map<String, Integer> requires;
        do {
            response = client.rollEddy();
            money = response.getResult().getC();
            requires = response.getRequires();
            System.out.println(++count);
        } while (money < 200000000
                || !requires.containsKey("bc")
                || requires.containsKey("sh")
                || requires.containsKey("so")
                || requires.containsKey("h")
                || requires.containsKey("cw")
                || requires.containsKey("w"));
        System.out.println(requires);
        System.out.println(money);
    }
}
