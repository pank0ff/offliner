package com.example.offliner.controller;

import com.example.offliner.domain.Message;
import com.example.offliner.domain.User;
import com.example.offliner.service.MessageService;
import com.example.offliner.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Validated
@Controller
public class RateController {

    private final RateService rateService;
    private final MessageService messageService;

    @Autowired
    public RateController(RateService rateService, MessageService messageService) {
        this.messageService = messageService;
        this.rateService = rateService;
    }


    @PostMapping("/rate/{id}/{username}")
    public String rate(@PathVariable Integer id, @AuthenticationPrincipal User user, @Valid @RequestParam int rate) {
        Message message = messageService.getMessageById(id);
        int counter = rateService.getRateCounter(user, message);
        if (counter == 0) {
            rateService.createRate(rate, user, message);
        }
        return "redirect:/main";

    }
}
