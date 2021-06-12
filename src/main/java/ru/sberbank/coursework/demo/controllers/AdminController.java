package ru.sberbank.coursework.demo.controllers;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sberbank.coursework.demo.dao.BankDao;

import java.util.List;

@Controller
public class AdminController {

    @GetMapping(value = "/admin")
    public String getAllBanks(Model model) {

        return "admin";
    }
}
