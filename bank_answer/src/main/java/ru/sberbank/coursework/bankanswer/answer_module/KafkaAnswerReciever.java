package ru.sberbank.coursework.bankanswer.answer_module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@EnableKafka
@Service
public class KafkaAnswerReciever {

    private final Logger logger = LoggerFactory.getLogger(KafkaAnswerReciever.class);

    @Autowired
    private final AnswerLogic answerLogic;

    /**
     * Конструктор по умолчанию
     * @param answerLogic
     */
    public KafkaAnswerReciever(AnswerLogic answerLogic) {
        this.answerLogic = answerLogic;
    }

    /**
     * Получение сообщений от сервиса KAFKA
     * Получение строки и передача ее в обработку
     * @param loan_request_str Полученная строка
     */
    @KafkaListener(topics = "bankanswer", groupId = "app.1")
    public void msgListener(String loan_request_str) {
        logger.info(String.format("Kafka-BA-Reciever - message: %s", "Recieve REST " + loan_request_str));
        answerLogic.send(loan_request_str, "KAFKA");
    }
}
