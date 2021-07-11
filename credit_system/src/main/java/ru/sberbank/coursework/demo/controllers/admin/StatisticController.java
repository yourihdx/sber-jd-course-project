package ru.sberbank.coursework.demo.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sberbank.coursework.demo.repositories.*;
import ru.sberbank.coursework.demo.service.RestService;

@Controller
public class StatisticController {

    @Autowired
    ClientCrudRepository clientCrudRepository;
    @Autowired
    LoanJpaRepository loanJpaRepository;
    @Autowired
    LoanOfferJpaRepository loanOfferJpaRepository;
    @Autowired
    LoanOfferCrudRepository loanOfferCrudRepository;
    @Autowired
    StatusLoanCrudRepository statusLoanCrudRepository;
    @Autowired
    AgreementCrudRepository agreementCrudRepository;
    @Autowired
    LoanCrudRepository loanCrudRepository;
    @Autowired
    BankCrudRepository bankCrudRepository;
    @Autowired
    LoanTypeCrudRepository loanTypeCrudRepository;
    @Autowired
    RestService restService;

    @GetMapping("/statistic")
    public String showStatistic(){

        Long clients = clientCrudRepository.count();
        System.out.println(clients);

        return "admin/statistic";
    }
}
