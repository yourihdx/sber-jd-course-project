package ru.sberbank.coursework.demo.controllers.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/index")
    public String getIndex() {

        return "/index";
    }


}
