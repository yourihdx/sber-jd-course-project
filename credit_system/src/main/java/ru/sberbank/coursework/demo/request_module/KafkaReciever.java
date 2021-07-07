package ru.sberbank.coursework.demo.request_module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.sberbank.coursework.demo.data.AnswerData;
import ru.sberbank.coursework.demo.data.AnswerList;
import ru.sberbank.coursework.demo.mail.Send;
import ru.sberbank.coursework.demo.pojo.Client;
import ru.sberbank.coursework.demo.pojo.LoanOffer;
import ru.sberbank.coursework.demo.repositories.ClientCrudRepository;
import ru.sberbank.coursework.demo.repositories.LoanCrudRepository;
import ru.sberbank.coursework.demo.repositories.LoanOfferCrudRepository;

@EnableKafka
@Service
public class KafkaReciever {
    private final Logger logger = LoggerFactory.getLogger(KafkaReciever.class);
    private AnswerList answerList = AnswerList.getInstance();
    @Autowired
    LoanOfferCrudRepository loanOfferCrudRepository;

    @Autowired
    ClientCrudRepository clientCrudRepository;

    /**
     * Получение сообщений от сервиса KAFKA
     * Вывод информации о получении ответа в логер
     *
     * @param msg Строка сообщения
     */
    @KafkaListener(topics = "credit_sender", groupId = "app.1")
    public void msgListener(String msg) {
        ObjectMapper mapper = new ObjectMapper();
        AnswerData answerData = new AnswerData();
        try {
            answerData = mapper.readValue(msg, AnswerData.class);
        } catch (JsonProcessingException e) {
            logger.error(String.format("Kafka-CS-Reciever - error: %s", e.toString()));
        }
        answerList.add(answerData);
        logger.info(String.format("Kafka-CS-Reciever - message: %s", "Recieve KAFKA " + msg));

        LoanOffer loanOffer = loanOfferCrudRepository.findLoanById(Integer.parseInt(answerData.getId()));
        Client clientPojo = clientCrudRepository.findById(loanOffer.getClientId());
        Send.SendEmail(clientPojo.getEMail(), "Получен ответ по заявке", "Здравствуйте " + clientPojo.getFullName() + "!\nПолучен ответ из банка по Вашей заявке на кредит. Для ознакомления войдите в личный кабинет");
        logger.info(String.format("Kafka-CS-Reciever - send E-mail to %s", clientPojo.getEMail()));
    }
}
