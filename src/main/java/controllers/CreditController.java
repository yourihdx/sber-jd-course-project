package controllers;

import domain.Credit;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.CreditService;

import java.util.List;

@RequestMapping("credits")
public class CreditController {

    CreditService creditService;

    @GetMapping
    public String getAllCredits(Model model) {
        List<Credit> credits = creditService.findAll();
        model.addAttribute("creditList", credits);
        return "credits";
    }

    @PostMapping
    public String createCredit(@RequestBody Credit credit) {
        creditService.createCredit(credit);
        return "credits";
    }

    @PutMapping("{id}")
    public String editCredit(
            @PathVariable("id") int id,
            @RequestBody Credit credit) {
        creditService.update(credit,id);
        return "credits";
    }

    @DeleteMapping("{id}")
    public String deleteCredit(@PathVariable("id") int id) {
        creditService.delete(id);
        return "credits";
    }

    @GetMapping("{id}")
    public Credit getCredit(@PathVariable String id) {
        Integer ids = Integer.parseInt(id);
        return creditService.findById(ids);
    }
}
