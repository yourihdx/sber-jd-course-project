package ru.sberbank.coursework.demo.request_module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.sberbank.coursework.demo.data.Loan_Offer;
import ru.sberbank.coursework.demo.data.Loan_request;
import ru.sberbank.coursework.demo.pojo.*;

@Controller
public class RequestBank {

    //Обратные адреса для получения ответа
    private final String KAFKA_ADDR = "credit_sender";

    private final RestFormSender restFormSender;

    private final KafkaSender kafkaSender;

    private final Logger logger = LoggerFactory.getLogger(RequestBank.class);

    /**
     * Конструктор по умолчанию
     *
     * @param restFormSender
     * @param kafkaSender
     */
    @Autowired
    RequestBank(RestFormSender restFormSender, KafkaSender kafkaSender) {
        this.restFormSender = restFormSender;
        this.kafkaSender = kafkaSender;
    }

    private void sendRequest(LoanOffer loanOffer, Client clientPojo,
                             Loan loanPojo, Bank bank, Payment loanType) {
        System.out.println(loanOffer.getId());
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
                //ПЕРЕПИСАТЬ
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
        } catch (JsonProcessingException e) {
            logger.error(String.format("CS-RequestBank - ERROR: %s", e.toString()));
        }
        System.out.println(json_string);
        kafkaSender.sendOrder(loan_request.getId().toString(), json_string);

    }
}
