package ru.sberbank.coursework.bank_answer.answer_module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.sberbank.coursework.bank_answer.data.AnswerData;

@Service
public class KafkaAnswerSender {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final Logger logger = LoggerFactory.getLogger(KafkaAnswerSender.class);

    /**
     * Отправка сообщений через сервис KAFKA
     * Получает на входе адресата, ID и объект, производит преобразование в JSON  и отправляет
     * @param reciever - адрес получателя
     * @param msgId  - ID сообщения
     * @param answerData - Объект с ответом
     */

    public void sendOrder(String reciever, String msgId, AnswerData answerData) {
        ObjectMapper mapper = new ObjectMapper();

        String json_str = null;
        try {
            json_str = mapper.writeValueAsString(answerData);
        } catch (JsonProcessingException e) {
            logger.error(String.format("Kafka-KA-Sender - ERROR: %s",e.toString()));
        }
        logger.info(String.format("Kafka-KA-Sender - message: %s","Send KAFKA " +json_str+" to kafka: "+reciever));
        kafkaTemplate.send(reciever, msgId, json_str);
    }
}