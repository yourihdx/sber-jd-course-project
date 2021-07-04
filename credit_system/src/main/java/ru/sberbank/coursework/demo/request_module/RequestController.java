package ru.sberbank.coursework.demo.request_module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sberbank.coursework.demo.data.Client;
import ru.sberbank.coursework.demo.data.Loan;
import ru.sberbank.coursework.demo.data.Loan_Offer;
import ru.sberbank.coursework.demo.data.Loan_request;

import java.util.Map;
import java.util.Objects;

@Controller
public class RequestController {
    //Обратные адреса для получения ответа
    private String POST_URL1 = "http://localhost:8080/json_in";
    private String KAFKA_ADDR = "credit_sender";
    private static Boolean restchecked = true;

    @Autowired
    private final RestFormSender restFormSender;

    private final KafkaSender kafkaSender;

    private final Logger logger = LoggerFactory.getLogger(RequestController.class);

    /**
     * Конструктор по умолчанию
     * @param restFormSender
     * @param kafkaSender
     */
    @Autowired
    RequestController(RestFormSender restFormSender, KafkaSender kafkaSender) {
        this.restFormSender = restFormSender;
        this.kafkaSender = kafkaSender;
    }

    /**
     * Контроллер GET для формирователя запросов
     *
     * @param model
     * @return
     */
//    @GetMapping("/form")
//    public String printForm(ModelMap model) {
//        return "requestform.html";
//    }

    /**
     * Контроллер POST для формирователя запросов
     * Принимает в качетве параметров значения из экранной формы (id - ID запроса и bank - произвольная строка).
     * Проверяем наличие параметра id и формируем фиктивный запрос
     * Генерирует объект запроса, направляем его в JSON по адресу REST конроллера основного модуля.
     *
     * @param allRequestParams
     * @param model
     * @return
     */
//    @PostMapping(value = "/form", produces = "text/html;charset=UTF-8")
    public String inputdat(@RequestParam Map<String, String> allRequestParams, ModelMap model) {

        if (Objects.nonNull(allRequestParams.get("id"))) {
            if (Objects.nonNull(allRequestParams.get("rest"))) {
                restchecked = true;
            } else {
                restchecked = false;
            }
            String addr_str;
            if (restchecked) {
                addr_str = POST_URL1;
            } else {
                addr_str = KAFKA_ADDR;
            }

            Client client = Client.
                    builder().
                    full_name("111").
                    birth_date("").
                    passport("").
                    phone_Number("").
                    email("").
                    build();

            Loan loan = Loan.
                    builder().
                    max_period(60).
                    max_sum(10000000).
                    product_type("ann").
                    percent_rate(5.5).
                    build();

            Loan_Offer loan_offer = Loan_Offer.
                    builder().
                    credit_sum(200000).
                    paymant_day(300).
                    credit_sum(100000).
                    build();

            Loan_request loan_request = Loan_request.
                    builder().
                    id(allRequestParams.get("id")).
                    bank(allRequestParams.get("bank")).
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
                logger.error(String.format("СЫ-Controller - ERROR: %s", e.toString()));
            }
            if (restchecked) {
                restFormSender.sendOrder(loan_request);
            } else {
                kafkaSender.sendOrder(loan_request.getId().toString(), json_string);
            }
        }
        model.addAttribute("message", "Ждем ответ");
        return "requestform.html";
    }
}
