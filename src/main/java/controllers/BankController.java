package controllers;

import domain.Bank;
import domain.Credit;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.BankService;

import java.util.List;

@RequestMapping("banks")
public class BankController {

    BankService bankService;

    @GetMapping
    public String getAllBanks(Model model) {
        List<Bank> banks = bankService.findAll();
        model.addAttribute("creditList", banks);
        return "credits";
    }

    @PostMapping
    public String createBank(@RequestBody Bank bank) {
        bankService.createCredit(bank);
        return "credits";
    }

    @PutMapping("{id}")
    public String editBank(
            @PathVariable("id") int id,
            @RequestBody Bank bank) {
        bankService.update(bank,id);
        return "credits";
    }

    @DeleteMapping("{id}")
    public String deleteBank(@PathVariable("id") int id) {
        bankService.delete(id);
        return "credits";
    }

    @GetMapping("{id}")
    public Bank getBank(@PathVariable String id) {
        Integer ids = Integer.parseInt(id);
        return bankService.findById(ids);
    }
}
