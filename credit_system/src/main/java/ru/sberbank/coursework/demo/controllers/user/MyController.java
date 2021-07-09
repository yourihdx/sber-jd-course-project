package ru.sberbank.coursework.demo.controllers.user;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import liquibase.pro.packaged.A;
import lombok.var;
import org.codehaus.plexus.util.IOUtil;
import org.hibernate.internal.build.AllowPrintStacktrace;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.coursework.demo.*;
import ru.sberbank.coursework.demo.data.MonthPay;
import ru.sberbank.coursework.demo.data.Schedule;
import ru.sberbank.coursework.demo.data.*;
import ru.sberbank.coursework.demo.pojo.Client;
import ru.sberbank.coursework.demo.pojo.Loan;
import ru.sberbank.coursework.demo.request_module.KafkaSender;
import ru.sberbank.coursework.demo.request_module.RestFormSender;

import ru.sberbank.coursework.demo.request_module.RequestBank;
import ru.sberbank.coursework.demo.pojo.*;
import ru.sberbank.coursework.demo.repositories.*;
import ru.sberbank.coursework.demo.service.RestService;
import sun.misc.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import static com.itextpdf.text.FontFactory.getFont;
import static com.itextpdf.text.pdf.BidiOrder.B;

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
    @Autowired
    RestService restService;

    private String POST_URL1 = "http://localhost:8080/json_in";
    private String KAFKA_ADDR = "credit_sender";

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
        ClientData clientData = new ClientData(pojoClient.getLogin(), pojoClient.getPassword(),
                pojoClient.getId(), pojoClient.getFullName(), pojoClient.getBirthDate().toString(), pojoClient.getEMail(),
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

    @GetMapping(value = "/file/{id}")
    public ResponseEntity showFile(@PathVariable("id") long creditId, HttpServletResponse res) {
        Schedule schedule = getSchedule((int) creditId);

        Document document = new Document();
        res.setContentType("application/pdf");

        try {
            PdfWriter.getInstance(document, res.getOutputStream());
        } catch (Exception e) {
            e.getStackTrace();
        }
        Boolean isLatin = false;
        document.open();
        Font font = null;
        try {
            String str1 = "C:\\Windows\\Fonts\\CALIBRI.TTF";
            String str2 = "CALIBRI.TTF";
            Path path1 = Paths.get(str1);
            Path path2 = Paths.get(str2);

            if (Files.exists(path1)) {
                BaseFont bf = BaseFont.createFont(str1, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                font = new Font(bf);
            } else if (Files.exists(path2)) {
                BaseFont bf = BaseFont.createFont(str2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                font = new Font(bf);
            } else {
                font = FontFactory.getFont(FontFactory.HELVETICA);
                isLatin = true;
            }
            font.setSize(16);

            Paragraph title = new Paragraph();
            Chunk chunk = new Chunk(isLatin ? "Payment Schedule" : "График платежей");
            chunk.setFont(font);
            title.add(chunk);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setFont(font);

            document.add(title);
            font.setSize(12);

//            Paragraph ph = new Paragraph(new Chunk(isLatin?"Loan Amount ":"Сумма " + schedule.getCreditAmount().toString(), font));
            Paragraph ph = new Paragraph(new Chunk(isLatin ? "Loan Amount " : "Сумма ", font));
            ph.add(new Chunk(schedule.getCreditAmount().toString(), font));
            document.add(ph);
            ph = new Paragraph(new Chunk(isLatin ? "Period " : "Срок ", font));
            ph.add(new Chunk(String.valueOf(schedule.getCreditTerm()), font));
            ph.add(new Chunk(isLatin ? " months" : " месяцев", font));
            document.add(ph);
            ph = new Paragraph(new Chunk(isLatin ? "Percent Rate " : "Процентная ставка ", font));
            ph.add(new Chunk(String.format("%5.2f", schedule.getPercentRate()), font));
            document.add(ph);
            if (schedule.isAnnuityPayment()) {
                ph = new Paragraph(new Chunk(isLatin ? "Type of payment, annuity" : "Вид платежей, аннуитетный ", font));
            } else {
                ph = new Paragraph(new Chunk(isLatin ? "Type of payment, differentiated" : "Вид платежей, дифференцированный ", font));

            }
            document.add(ph);
            ph = new Paragraph(new Chunk(isLatin ? "Overpayment " : "Переплата по процентам ", font));
            ph.add(new Chunk(schedule.calculateTotalPercent().toString(), font));
            document.add(ph);
            ph = new Paragraph(" ");
            document.add(ph);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PdfPTable table = new PdfPTable(5);
        addTableHeader(table, font, isLatin);
        for (MonthPay payment : schedule.getPayments()) {

            table.addCell(payment.getPaymentDate());
            table.addCell(payment.getPrincipal());
            table.addCell(payment.getPercent());
            table.addCell(payment.getPayment());
            table.addCell(payment.getBalance());
        }
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        String headerStr = "inline";
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                headerStr).build();
    }

    private void addTableHeader(PdfPTable table, Font font, Boolean islatin) {
        String[] headerStr = null;
        if (islatin) {
            headerStr = new String[]{"Payment Date", "Principal Amount", "Percent Amount",
                    "Total Amount", "Balance"};
        } else {
            headerStr = new String[]{"Дата платежа", "Основной долг", "Проценты",
                    "Сумма платежа", "Остаток"};
        }
        Stream.of(headerStr)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.addElement(new Chunk(columnTitle, font));
                    table.addCell(header);
                });
    }

    private Schedule getSchedule(int creditId) {
        Map<String, String> param = new HashMap<>();
        LoanOffer loan = loanOfferJpaRepository.getById((int) creditId);

        param.put("creditAmount", loan.getCreditSum().toString());
        param.put("percentRate", String.valueOf(loan.getPercent() * 100));
        param.put("creditTerm", String.valueOf(loan.getPeriod()));
        param.put("annuityPayment", (loan.getProductTypeId() == 1) ? "true" : "false");
        Schedule schedule = restService.getApi(param);

        return schedule;
    }

    @GetMapping(value = "/showPayment/{id}")
    public String showPayment(@PathVariable("id") long creditId, ModelMap map) {
        // метод для отрисовки графика платежей. Заглушка.
        // Ссылается на такую же фейковую страницу.
        Schedule schedule = getSchedule((int) creditId);
        map.addAttribute("schedule", schedule);
        map.addAttribute("creditId", creditId);
//        map.addAttribute("creditData", new OfferForm());

        return "user/showPayments";
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
        addr_str = KAFKA_ADDR;


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
                min_percent_rate(loanPojo.getPercent()).
                //ПОПРАВИТЬ!!!!!!!!
                        max_percent_rate(loanPojo.getPercent() + 1).
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
        } catch (
                JsonProcessingException e) {
//                logger.error(String.format("BF-Controller - ERROR: %s", e.toString()));
            System.out.println(e.toString());
        }
        kafkaSender.sendOrder(loan_request.getId().toString(), json_string);
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
