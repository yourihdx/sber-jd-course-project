package ru.sberbank.coursework.schedule.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sberbank.coursework.schedule.model.GetSchedule;
import ru.sberbank.coursework.schedule.model.PayVar;
import ru.sberbank.coursework.schedule.model.Schedule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/show")
public class WebController {

    private Schedule schedule;

    public WebController(){

    }
    @GetMapping("/")
    public String show(Model model){

        model.addAttribute("getSchedule", new GetSchedule());
        List<PayVar> payVars = new ArrayList<>();
        payVars.add(new PayVar(true, "Аннуитетные"));
        payVars.add(new PayVar(false, "Дифференцированные"));
        boolean nullShedule = Objects.isNull(schedule);
        model.addAttribute("schedule", schedule);
        model.addAttribute("nullSchedule", nullShedule);
        model.addAttribute("payVars", payVars);
        return "show";
    }

    @PostMapping("/")
    public String getSchedule(@ModelAttribute("getSchedule") GetSchedule getSchedule){

        this.schedule = new Schedule(getSchedule.getCreditAmount(), getSchedule.getCreditTerm(),
                getSchedule.getPercentRate(), getSchedule.isAnnuityPayment());

        return "redirect:/show/";
    }


}
