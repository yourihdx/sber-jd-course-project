package ru.sberbank.coursework.demo.controllers.admin;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.coursework.demo.dao.CreditDao;
import ru.sberbank.coursework.demo.domain.Credit;

import java.util.List;

@Controller
public class CreditController {


    @GetMapping(value = "/credits")
    public String getAllCredits(Model model) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CreditDao creditDao = new CreditDao(sessionFactory);
        List<Credit> credits = creditDao.getAllCredits();
        model.addAttribute("creditList", credits);
        return "admin/credits";
    }

    @PostMapping(value = "/credits")
    public String createCredit(Model model, Credit credit) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CreditDao creditDao = new CreditDao(sessionFactory);
        creditDao.createCredit(credit);
        List<Credit> credits = creditDao.getAllCredits();
        model.addAttribute("creditList", credits);
        return "admin/credits";
    }

    @PostMapping("/credits/{id}")
    public String editCredit(Model model,
                             @PathVariable("id") int id,
                             Credit credit) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CreditDao creditDao = new CreditDao(sessionFactory);
        credit.setId(id);
        creditDao.updateCredit(credit);
        List<Credit> credits = creditDao.getAllCredits();
        model.addAttribute("creditList", credits);
        return "admin/credits";
    }

    @GetMapping("/deleteCredit/{id}")
    public String deleteCredit(Model model, @PathVariable("id") int id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CreditDao creditDao = new CreditDao(sessionFactory);
        creditDao.deleteCredit(id);
        List<Credit> credits = creditDao.getAllCredits();
        model.addAttribute("creditList", credits);
        return "admin/credits";
    }

    @GetMapping("/credits/{id}")
    public String getCredit(Model model, @PathVariable String id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        CreditDao creditDao = new CreditDao(sessionFactory);
        Integer ids = Integer.parseInt(id);
        Credit credit = creditDao.getCredit(ids);
        model.addAttribute("credit", credit);
        List<Credit> credits = creditDao.getAllCredits();
        model.addAttribute("creditList", credits);
        return "admin/credit";
    }
}
