package ru.sberbank.coursework.schedule.controller;

import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.coursework.schedule.ReportCreator;
import ru.sberbank.coursework.schedule.model.GetSchedule;
import ru.sberbank.coursework.schedule.model.PayVar;
import ru.sberbank.coursework.schedule.model.Schedule;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/show")
public class ViewController {

    private GetSchedule getSchedule = new GetSchedule(new BigDecimal(10000),  new BigDecimal(10), 10, true);
    private Schedule schedule = new Schedule(getSchedule);

    public ViewController(){

    }
    @GetMapping("/")
    public String show(Model model, @RequestParam HashMap<String, String> param){

        String amount = param.get("creditAmount");
        String rate = param.get("percentRate");
        String term = param.get("creditTerm");
        String payment = param.get("annuityPayment");
        if(Objects.nonNull(amount) && Objects.nonNull(rate) && Objects.nonNull(term)) {
            BigDecimal creditAmount = new BigDecimal(amount);
            BigDecimal percentRate = new BigDecimal(rate);
            int creditTerm = Integer.valueOf(term);
            boolean annuityPayment = Boolean.valueOf(payment);
            getSchedule = new GetSchedule(creditAmount, percentRate, creditTerm, annuityPayment);
            this.schedule = new Schedule(getSchedule);
        }

        model.addAttribute("getSchedule", getSchedule);
        List<PayVar> payVars = new ArrayList<>();
        payVars.add(new PayVar(true, "Аннуитетные"));
        payVars.add(new PayVar(false, "Дифференцированные"));
        boolean nullSchedule = Objects.isNull(this.schedule);

        model.addAttribute("schedule", schedule);
        model.addAttribute("nullSchedule", nullSchedule);
        model.addAttribute("payVars", payVars);
        return "show";
    }

    @PostMapping("/")
    public String getSchedule(@ModelAttribute("getSchedule") GetSchedule getSchedule){

        this.schedule = new Schedule(getSchedule.getCreditAmount(), getSchedule.getCreditTerm(),
                getSchedule.getPercentRate(), getSchedule.isAnnuityPayment());

        this.getSchedule = getSchedule;

        return "redirect:/show/";
    }

    @GetMapping(value = "/file")
    @ResponseBody
    public ResponseEntity<Resource> getPdf(@RequestParam HashMap<String, String> param){


        String fileName = "webcontrollerreport";

        ReportCreator reportCreator = new ReportCreator();

        Path path = null;
        try {
            path = reportCreator.createReport(this.schedule, fileName);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        Path path = Paths.get("src//main//resources//templates//pdf//report.pdf");
        Resource file = null;
        try {
            file = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


}
