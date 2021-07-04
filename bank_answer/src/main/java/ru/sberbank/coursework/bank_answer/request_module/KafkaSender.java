package ru.sberbank.coursework.bank_answer.request_module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {

    private final Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Отправка сообщений через сервис KAFKA
     * Получает на входе адресата, ID и строку  и отправляет
     * @param msgId
     * @param msg
     */
    public void sendOrder(String msgId, String msg) {
        try {
            logger.info(String.format("Kafka-CS-Sender - message: %s", "Send KAFKA " + msg + " to kafka: bankanswer"));
            kafkaTemplate.send("bankanswer", msgId, msg);
        } catch (Exception e) {
            logger.error(String.format("Kafka-CS-Sender - ERROR: %s", e.toString()));
        }
    }
}