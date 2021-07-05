package ru.sberbank.coursework.schedule.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.coursework.schedule.model.CreditMainParam;
import ru.sberbank.coursework.schedule.model.Schedule;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/api")
public class ApiRestController {

    private Schedule schedule;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Schedule getApiSchedule(@RequestParam HashMap<String, String> param){
        String amount = param.get("creditAmount");
        String rate = param.get("percentRate");
        String term = param.get("creditTerm");
        String payment = param.get("annuityPayment");
        BigDecimal creditAmount = new BigDecimal(amount);
        BigDecimal percentRate = new BigDecimal(rate);
        int creditTerm = Integer.valueOf(term);
        boolean annuityPayment = Boolean.valueOf(payment);
        this.schedule = new Schedule(creditAmount, creditTerm, percentRate, annuityPayment);

        return this.schedule;
    }

    @GetMapping(value = "/param", produces = MediaType.APPLICATION_JSON_VALUE)
    public CreditMainParam getMainParameters(@RequestParam HashMap<String, String> param){
        String amount = param.get("creditAmount");
        String rate = param.get("percentRate");
        String term = param.get("creditTerm");
        String payment = param.get("annuityPayment");
        BigDecimal creditAmount = new BigDecimal(amount);
        BigDecimal percentRate = new BigDecimal(rate);
        int creditTerm = Integer.valueOf(term);
        boolean annuityPayment = Boolean.valueOf(payment);
        this.schedule = new Schedule(creditAmount, creditTerm, percentRate, annuityPayment);
        CreditMainParam creditMainParam = new CreditMainParam(
                schedule.calculateTotalPercent(), schedule.getPayments().get(0).getPayment());
        return creditMainParam;
    }

}
