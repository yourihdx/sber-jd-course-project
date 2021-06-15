package ru.sberbank.coursework.bankanswer.request_module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.sberbank.coursework.bankanswer.answer_module.KafkaAnswerReciever;

@EnableKafka
@Service
public class KafkaReciever {
    private final Logger logger = LoggerFactory.getLogger(KafkaAnswerReciever.class);

    /**
     * Получение сообщений от сервиса KAFKA
     * Вывод информации о получении ответа в логер
     * @param msg Строка сообщения
     */
    @KafkaListener(topics = "test_sender", groupId = "app.1")
    public void msgListener(String msg) {
        logger.info(String.format("Kafka-KF-Reciever - message: %s", "Recieve KAFKA " + msg));
    }
}
