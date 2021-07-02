package ru.sberbank.coursework.demo.controllers.admin;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.coursework.demo.dao.BankDao;
import ru.sberbank.coursework.demo.domain.Bank;

import java.util.List;

@Controller
public class BankController {

    @GetMapping(value = "/bankss")
    public String getAllBanks(Model model) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        BankDao bankDao = new BankDao(sessionFactory);
        List banks = bankDao.getAllBanks();
        model.addAttribute("bankList", banks);
        return "admin/bankss";
    }

    @PostMapping(value = "/bankss")
    public String createBank(Model model, Bank bank) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        BankDao bankDao = new BankDao(sessionFactory);
        bankDao.createBank(bank);
        List banks = bankDao.getAllBanks();
        model.addAttribute("bankList", banks);
        return "admin/bankss";
    }

    @PostMapping("/bankss/{id}")
    public String editBank(Model model,
                           @PathVariable("id") int id,
                           Bank bank) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        BankDao bankDao = new BankDao(sessionFactory);
        bank.setId(id);
        bankDao.updateBank(bank);
        List banks = bankDao.getAllBanks();
        model.addAttribute("bankList", banks);
        return "admin/bankss";
    }

    @GetMapping("/deleteBank/{id}")
    public String deleteBank(Model model, @PathVariable("id") int id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        BankDao bankDao = new BankDao(sessionFactory);
        bankDao.deleteBank(id);
        List banks = bankDao.getAllBanks();
        model.addAttribute("bankList", banks);
        return "admin/bankss";
    }

    @GetMapping("/bankss/{id}")
    public String getBank(Model model, @PathVariable String id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        BankDao bankDao = new BankDao(sessionFactory);
        Integer ids = Integer.parseInt(id);
        Bank bank = bankDao.getBank(ids);
        model.addAttribute("bank", bank);
        List banks = bankDao.getAllBanks();
        model.addAttribute("bankList", banks);
        return "admin/bank";
    }
}
