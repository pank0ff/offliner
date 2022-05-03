package com.example.offliner.service;

import com.example.offliner.domain.Message;
import com.example.offliner.domain.Rate;
import com.example.offliner.domain.User;
import com.example.offliner.repos.RateRepo;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        double averageRate;
        List<Rate> rates = rateRepo.findByMessageId(message.getId());
        for(Rate rate : rates){
            sum+=rate.getRate();
            counter++;
        }
        if (counter != 0) {
            averageRate = DoubleRounder.round(sum / counter, 2);
        } else {
            averageRate = 0;
        }
        return averageRate;
    }

    public List<Rate> getRateByUserId(Long id) {
        return rateRepo.findByUserId(id);
    }

    public int getRateCounter(User user, Message message) {
        int counter = 0;
        for (Rate rate1 : rateRepo.findByUserId(user.getId())) {
            if (Objects.equals(rate1.getMessage(), message)) {
                counter++;
            }
        }
        return counter;
    }

}
