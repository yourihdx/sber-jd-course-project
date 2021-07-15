    package ru.sberbank.coursework.demo.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sberbank.coursework.demo.repositories.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class StatisticController {

    @Autowired
    private ClientCrudRepository clientCrudRepository;

    @Autowired
    private LoanCrudRepository loanCrudRepository;

    @Autowired
    private LoanOfferCrudRepository loanOfferCrudRepository;

    @Autowired
    private StatusLoanCrudRepository statusLoanCrudRepository;

    @Autowired
    private BankCrudRepository bankCrudRepository;

    @GetMapping("/statistic")
    public String showStatistic(Model model){

        long clientCount = clientCrudRepository.count();
        Map<String, Long> ageStatistic = ageStatistic();
        int avgAge = clientCrudRepository.avgAge();

        Long loanAskCount = loanOfferCrudRepository.count();
        Map<String, Integer> countLoanStatus = loanStatusStatistic();
        int agreementPercent = 0;
        if(loanAskCount > 0){
            agreementPercent = (int) ((100 * loanOfferCrudRepository.countLoanStatus(1)) / loanAskCount);
        }
        Long totalAmount = loanOfferCrudRepository.sumAllAmount();

        Long bankCount = bankCrudRepository.count();
        Long loanCount = loanCrudRepository.count();

        model.addAttribute("clientCount", clientCount);
        model.addAttribute("countAge", ageStatistic.entrySet());
        model.addAttribute("avgAge", avgAge);
        model.addAttribute("loanAskCount", loanAskCount);
        model.addAttribute("countLoanStatus", countLoanStatus.entrySet());
        model.addAttribute("agreementPercent", agreementPercent);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("bankCount", bankCount);
        model.addAttribute("loanCount", loanCount);

        return "admin/statistic";
    }

    Map<String, Long> ageStatistic(){
        Map<String, Long> ageStatistic = new TreeMap<>();
        ageStatistic.put("18 - 25", clientCrudRepository.getDateInterval(18, 25));
        ageStatistic.put("26 - 35", clientCrudRepository.getDateInterval(26, 35));
        ageStatistic.put("36 - 45", clientCrudRepository.getDateInterval(36, 45));
        ageStatistic.put("46 - 55", clientCrudRepository.getDateInterval(46, 55));
        ageStatistic.put("56 - 75", clientCrudRepository.getDateInterval(56, 75));
        return ageStatistic;
    }

    Map<String, Integer> loanStatusStatistic(){
        Map<String, Integer> ageStatistic = new TreeMap<>();
        Long statusNumber = statusLoanCrudRepository.count();
        for(int i = 0; i < statusNumber; i++){
            ageStatistic.put(statusLoanCrudRepository.findById(i).getName(), loanOfferCrudRepository.countLoanStatus(i));

        }
        return ageStatistic;
    }
}
