package ru.sberbank.coursework.demo.controllers.user;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.coursework.demo.data.ClientData;
import ru.sberbank.coursework.demo.data.CreditsList;
import ru.sberbank.coursework.demo.data.OfferForm;
import ru.sberbank.coursework.demo.data.AnswerData;
import ru.sberbank.coursework.demo.data.AnswerList;
import ru.sberbank.coursework.demo.data.Loan_Offer;
import ru.sberbank.coursework.demo.data.Loan_request;
import ru.sberbank.coursework.demo.pojo.*;
import ru.sberbank.coursework.demo.repositories.*;
import ru.sberbank.coursework.demo.request_module.KafkaSender;
import ru.sberbank.coursework.demo.request_module.RestFormSender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/demo")
public class MyController {
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

    private String POST_URL1 = "http://localhost:8080/json_in";
    private String KAFKA_ADDR = "credit_sender";
    private static Boolean restchecked = false;


    private final RestFormSender restFormSender;

    private final KafkaSender kafkaSender;

    @Autowired
    MyController(RestFormSender restFormSender, KafkaSender kafkaSender) {
        this.restFormSender = restFormSender;
        this.kafkaSender = kafkaSender;
    }

    @GetMapping(value = "/")
    public String startPage(ModelMap map) {
        map.addAttribute("clientData", new ClientData());
        map.addAttribute("message", "Введите логин и пароль");
        return "user/login";
    }

    /*
    Регистрация нового пользователя client
     */
    @GetMapping(value = "/reg/")
    public String reg(@ModelAttribute ClientData client, ModelMap map) {
        // Предоставление клиенту формы для регистрации

        map.addAttribute("clientData", new ClientData());
        return "user/reg";
    }

    @GetMapping(value = "/update/{id}")
    public String update(@PathVariable("id") long clientId, ModelMap map) {
        // Get client Data from base by id
        // Предоставление формы для изменения регистрационной информации
        Client pojoClient = clientCrudRepository.findClientByIdAndIsDeletedIsFalse((int) clientId);
        ClientData clientData = new ClientData(pojoClient.getLogin(), pojoClient.getPassword(), pojoClient.getId(),
                pojoClient.getFullName(), pojoClient.getBirthDate().toString(), pojoClient.getEMail(),
                pojoClient.getPhoneNumber(), pojoClient.getPassportSeriesNum());
        map.addAttribute("clientData", clientData);
        return "user/update";
    }

    @PostMapping(value = "/login/")
    // Get Client by Login/Password
    // Проверка пользователя по login/password
    public String login(@ModelAttribute ClientData client, ModelMap map) {


        List<Client> clients = clientCrudRepository.findClientByLoginAndPasswordAndIsDeletedIsFalse(client.getLogin(), client.getPassword());
        if (clients.size() > 0) {
            map.addAttribute("id", clients.get(0).getId());
            return "user/client";
        }
        map.addAttribute("message", "Клиент с такими регистрационными данными не найден.");
        return "user/login";
    }

    @PostMapping(value = "/saveReg/")
    public String saveReq(@ModelAttribute ClientData client, ModelMap map) {
        // Saving ClientData
        // Сохранение регистрационной информации, переход в личный кабинет

        ru.sberbank.coursework.demo.pojo.Client pojoClient = new Client(client.getFio(),
                LocalDate.parse(client.getBirthDate()), client.getPhone(), client.getEmail(),
                client.getPassport(), client.getLogin(), client.getPassword());

        List<Client> clientsByLogin = clientCrudRepository.findClientByLoginAndIsDeletedIsFalse(pojoClient.getLogin());
        if (clientsByLogin.size() > 0) {
            map.addAttribute("message", "Клиент с таким Login уже существует");
            return "user/login";
        }
        List<Client> clientsByPassport = clientCrudRepository.findClientBypassportSeriesNumAndIsDeletedIsFalse(pojoClient.getPassportSeriesNum());
        if (clientsByPassport.size() > 0) {
            map.addAttribute("message", "Клиент с такими паспортными данными уже зарегистрирован");
            return "user/login";
        }
        Client clients = clientCrudRepository.save(pojoClient);
        map.addAttribute("id", clients.getId());
        return "user/client";
    }

    @PostMapping(value = "/updateReg/")
    public String updateReq(@ModelAttribute ClientData client, ModelMap map) {
        // Update ClientData
        // Сохранение регистрационной информации, переход в личный кабинет
        ru.sberbank.coursework.demo.pojo.Client pojoClient = new Client(client.getFio(),
                LocalDate.parse(client.getBirthDate()), client.getPhone(), client.getEmail(),
                client.getPassport(), client.getLogin(), client.getPassword());
        pojoClient.setId((int) client.getId());
        List<Client> clientsByPassport = clientCrudRepository.findClientBypassportSeriesNumAndIdIsNotEqualAndIsDeletedIsFalse(pojoClient.getPassportSeriesNum(), pojoClient.getId());
        if (clientsByPassport.size() > 0) {
            map.addAttribute("clientData", client);
            return "user/update";
        }
        clientCrudRepository.save(pojoClient);
        map.addAttribute("id", client.getId());
        return "user/client";
    }

    @GetMapping(value = "/myCredits/{id}")
    public String myCredits(@PathVariable("id") long clientId, ModelMap map) {
// Одобрение банков по запросам клиента из списка предложенных
        AnswerList answerList = AnswerList.getInstance();
        while (answerList.size() > 0) {
            AnswerData answerData = answerList.getRec();
            LoanOffer loanOffer = loanOfferCrudRepository.findLoanById(Integer.parseInt(answerData.getId()));
            loanOffer.setStatus(answerData.getRes());
            loanOffer = loanOfferCrudRepository.save(loanOffer);
        }

        List<LoanOfferList> loans = loanOfferJpaRepository.findAllByClientId((int) clientId);

        ArrayList<OfferForm> credits = new ArrayList<>();
        OfferForm offerForm;
        for (LoanOfferList loan : loans) {
            offerForm = new OfferForm();
            offerForm.setBank(loan.getBankName());
            offerForm.setLimit((long) loan.getLimit());
            offerForm.setId(loan.getId());
            offerForm.setPayment(loan.getPayment());
            offerForm.setPeriod(loan.getPeriod());
            offerForm.setPercent(loan.getPercent());
            offerForm.setStatus(loan.getStatus());
            offerForm.setStatusId(loan.getStatusId());

            credits.add(offerForm);
        }

        CreditsList creditsList = new CreditsList();
        creditsList.setCredits(credits);
        map.addAttribute("id", clientId);
        map.addAttribute("creditsList", creditsList);
        return "user/myCredits";
    }

    @GetMapping(value = "/newOffer/{id}")
    public String newOffer(@PathVariable("id") long clientId, ModelMap map) {
        // Форма для поиска новых предложений банков
        map.addAttribute("id", clientId);
        map.addAttribute("creditData", new OfferForm());
        return "user/newOffer";
    }

    @GetMapping(value = "/showPayment/{id}")
    public String showPayment(@PathVariable("id") long creditId, ModelMap map) {
        // метод для отрисовки графика платежей. Заглушка.
        // Ссылается на такую же фейковую страницу.
        map.addAttribute("creditId", creditId);
        map.addAttribute("creditData", new OfferForm());
        return "user/paymentList";
    }

    @PostMapping("/saveOffer/{id}")
    public String saveOffer(@PathVariable("id") long clientId, @ModelAttribute OfferForm credit, ModelMap map) {
        // Save New Credit Info
        // Поиск и сохранение предложений банков по введенным параметрам
        // Сумма, срок, процент
        List<LoanList> loans = loanJpaRepository.findAllByClientCriteria(credit.getPeriod(), credit.getLimit(), credit.getPercent());

        ArrayList<OfferForm> credits = new ArrayList<>();
        for (LoanList loan : loans) {
            credits.add(new OfferForm(loan.getBankName(), loan.getBankId(), (long) loan.getLimit(),
                    loan.getPeriod(), loan.getPercent(), credit.getLimit(), credit.getPeriod(), credit.getPercent(),
                    loan.getId(), loan.getPayment(), loan.getPaymentId(), false, 3, "", false));
        }
        CreditsList creditsList = new CreditsList();
        creditsList.setCredits(credits);
        map.addAttribute("id", clientId);
        map.addAttribute("creditsList", creditsList);
        return "user/offerForm";

    }

    @PostMapping("/saveCredits/{id}")
    public String saveOffer(@PathVariable("id") long clientId, @ModelAttribute CreditsList creditsList, ModelMap map) {
        // Send selected offers to banks to approve
        // Выбор клиентом предложений банков, выбор страховки и отправка их на одобрение
        double addPercent = 0.03;
        long addInsurance = 100000L;
        ArrayList<LoanOffer> loanOffers = new ArrayList<>();
        OfferForm offer;
        LoanOffer loan;
        for (int i = 0; i < creditsList.getCredits().size(); i++) {
            offer = creditsList.getCredits().get(i);
            if (offer.isSelected()) {
                loan = new LoanOffer((int) clientId, (int) offer.getId(), offer.getPaymentId(), offer.getBankId(),
                        BigDecimal.valueOf(offer.isInsurance() ? offer.getReqLimit() + addInsurance : offer.getReqLimit()), offer.getReqPeriod(),
                        offer.isInsurance() ? offer.getPercent() : offer.getPercent() + addPercent,
                        0, BigDecimal.valueOf(0),
                        BigDecimal.valueOf(offer.isInsurance() ? offer.getReqLimit() + addInsurance : offer.getReqLimit()),
                        BigDecimal.valueOf(0), 2);
                loanOffers.add(loan);

                loan = loanOfferCrudRepository.save(loan);
                Client clientPojo = clientCrudRepository.findById(loan.getClientId());
                Loan loanPojo = loanCrudRepository.findById(loan.getLoanId());
                Bank bank = bankCrudRepository.findById(loanPojo.getBankId());
                Payment loanType = loanTypeCrudRepository.findById(loanPojo.getProductTypeId());

                sendRequest(loan, clientPojo,
                        loanPojo, bank, loanType);

            }
        }

        map.addAttribute("id", clientId);
        return "user/client";
    }

    private void sendRequest(LoanOffer loanOffer, Client clientPojo,
                             Loan loanPojo, Bank bank, Payment loanType) {
        String addr_str;
        if (restchecked) {
            addr_str = POST_URL1;
        } else {
            addr_str = KAFKA_ADDR;
        }

        ru.sberbank.coursework.demo.data.Client client = ru.sberbank.coursework.demo.data.Client.
                builder().
                full_name(clientPojo.getFullName()).
                birth_date(clientPojo.getBirthDate().toString()).
                passport(clientPojo.getPassportSeriesNum()).
                phone_Number(clientPojo.getPhoneNumber()).
                email(clientPojo.getEMail()).
                build();

        ru.sberbank.coursework.demo.data.Loan loan = ru.sberbank.coursework.demo.data.Loan.
                builder().
                max_period(loanPojo.getPeriod()).
                max_sum(loanPojo.getCreditSum()).
                product_type(loanType.getName()).
                percent_rate(loanPojo.getPercent()).
                build();

        Loan_Offer loan_offer = Loan_Offer.
                builder().
                credit_sum(loanOffer.getCreditSum().doubleValue()).
                paymant_day(loanOffer.getPaymentDay()).
                payment(loanOffer.getPrincipalPaymentAmount().doubleValue()).
                build();

        Loan_request loan_request = Loan_request.
                builder().
                id(String.valueOf(loanOffer.getId())).
                bank(bank.getName()).
                sender_addr(addr_str).
                client(client).
                loan(loan).
                loan_offer(loan_offer).
                build();
        ObjectMapper mapper = new ObjectMapper();
        String json_string = null;
        try {
            json_string = mapper.writeValueAsString(loan_request);
        } catch (JsonProcessingException e) {
//                logger.error(String.format("BF-Controller - ERROR: %s", e.toString()));
            System.out.println(e.toString());
        }
        if (restchecked) {
            restFormSender.sendOrder(loan_request);
        } else {
            kafkaSender.sendOrder(loan_request.getId().toString(), json_string);
        }
    }


    @GetMapping(value = "/checkCredit/{id}/{creditId}")
    public String checkCredit(@PathVariable("id") long clientId, @PathVariable("creditId") long creditId, ModelMap map) {
        LoanOfferList loan = loanOfferJpaRepository.findLoanOfferById((int) creditId);
        OfferForm credit = new OfferForm();
        credit.setPercent(loan.getPercent());
        credit.setPeriod(loan.getPeriod());
        credit.setBank(loan.getBankName());
        credit.setBankId(loan.getBankId());
        credit.setPayment(loan.getPayment());
        credit.setLimit((long) loan.getLimit());

        // get Credit by id
        // Поиск кредитов, выбранных пользователем с отметкой об одобрении approove=true
        map.addAttribute("id", clientId);
        map.addAttribute("creditData", credit);
        return "user/checkCredit";
    }

    @PostMapping(value = "/approveOffer/{id}")
    public String approveCredit(@PathVariable("id") long clientId, @ModelAttribute("creditData") OfferForm creditData, ModelMap map) {
        // Find credit by credit.id
        // форма для окончательной отправки заявки на оформление кредита
        Agreement agreement = new Agreement(creditData.getBankId(), (int) clientId, (int) creditData.getId(),
                BigDecimal.valueOf(creditData.getLimit()), LocalDate.parse("1970-01-01"), creditData.getPeriod());
        agreement = agreementCrudRepository.save(agreement);
        map.addAttribute("id", clientId);
        map.addAttribute("creditData", creditData);
        return "user/requestCredit";
    }
}
