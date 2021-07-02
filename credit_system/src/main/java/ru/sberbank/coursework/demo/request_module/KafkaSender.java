package ru.sberbank.coursework.demo.request_module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaSender {


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
            System.out.println("Sender: " + msgId + " " + msg);
            kafkaTemplate.send("bankanswer", msgId, msg);
        } catch (Exception e) {
            System.out.println("Error sender: " + e.toString());
        }
    }
}