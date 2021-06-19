package ru.sberbank.coursework.bank_answer.answer_module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.coursework.bank_answer.data.AnswerData;

@Service
public class RestBankAnswerSender {

    private final Logger logger = LoggerFactory.getLogger(RestBankAnswerSender.class);

    /**
     * Отправка сообщения через REST
     * Получает на входе адрес и объект для отправки
     * @param reciever
     * @param answerData
     */
    public void sendOrder(String reciever, AnswerData answerData) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AnswerData> entity = new HttpEntity<>(answerData, headers);
        AnswerData answ = restTemplate.postForObject(reciever, answerData, AnswerData.class);
        logger.info(String.format("REST-BA-SENDER - message: %s", "Send REST " + answerData.toString() + "to HTTP: " + reciever));
    }
}
