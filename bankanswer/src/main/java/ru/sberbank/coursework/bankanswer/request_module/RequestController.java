package ru.sberbank.coursework.bankanswer.request_module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.net.URLEncoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.coursework.bankanswer.data.Client;
import ru.sberbank.coursework.bankanswer.data.Loan;
import ru.sberbank.coursework.bankanswer.data.Loan_Offer;
import ru.sberbank.coursework.bankanswer.data.Loan_request;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Controller
public class RequestController {
    private String POST_URL = "http://localhost:8086/bankanswer/form_in";
    /**
     * Контроллер GET для формирователя запросов
     * @param model
     * @return
     */
    @GetMapping("/form")
    public String printForm(ModelMap model) {
         return "requestform.html";
    }

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
    @PostMapping(value = "/form",produces = "text/html;charset=UTF-8")
    public String inputdat(@RequestParam Map<String, String> allRequestParams, ModelMap model) {

        if (Objects.nonNull(allRequestParams.get("id"))) {
            System.out.println(allRequestParams.get("bank"));
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
                    client(client).
                    loan(loan).
                    loan_offer(loan_offer).
                    build();
            ObjectMapper mapper = new ObjectMapper();
            String json_string = null;
            try {
                json_string = mapper.writeValueAsString(loan_request);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Loan_request> entity = new HttpEntity<>(loan_request, headers);
            Loan_request answ = restTemplate.postForObject(POST_URL, loan_request, Loan_request.class);
            System.out.println("Запрос подтвержден "+answ);
        }

        model.addAttribute("message","Ждем ответ");
        return "requestform.html";
    }
}
