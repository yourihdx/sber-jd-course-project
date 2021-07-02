package ru.sberbank.coursework.bankanswer.request_module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.coursework.bankanswer.answer_module.KafkaAnswerReciever;
import ru.sberbank.coursework.bankanswer.data.Loan_request;

@Service
public class RestFormSender {

    private String POST_URL = "http://localhost:8086/bankanswer/form_in";

    private final Logger logger = LoggerFactory.getLogger(KafkaAnswerReciever.class);

    /**
     * Отправка сообщения через REST
     * На входе - объет для отправки
     * @param loan_request
     */
    public void sendOrder(Loan_request loan_request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Loan_request> entity = new HttpEntity<>(loan_request, headers);
        Loan_request answ = restTemplate.postForObject(POST_URL, loan_request, Loan_request.class);
        logger.info(String.format("REST-BF-SENDER - message: %s", "Send REST " + loan_request.toString() + "to HTTP: " + POST_URL));
    }
}
