package com.example.offliner.service;

import com.example.offliner.domain.Message;
import com.example.offliner.domain.User;
import com.example.offliner.domain.Rate;
import com.example.offliner.repos.RateRepo;
import org.apache.commons.math3.util.Precision;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateService {
    private final RateRepo rateRepo;

    @Autowired
    public RateService(RateRepo rateRepo){
        this.rateRepo = rateRepo;
    }

    public void createRate(int num, User user, Message message){
        Rate rate = new Rate();
        rate.setRate(num);
        rate.setUser(user);
        rate.setMessage(message);
        rateRepo.save(rate);
    }
    public double calcAverageRate(Message message){
        float counter = 0;
        float sum = 0;
        double averageRate = 0;
        Integer i;
        List<Rate> rates  = rateRepo.findByMessageId(message.getId());
        for(Rate rate : rates){
            sum+=rate.getRate();
            counter++;
        }
        if(counter != 0){
        averageRate = sum / counter;}else{
            averageRate = 0;
        }
        DoubleRounder.round(averageRate,2);
        return averageRate;
    }

}
