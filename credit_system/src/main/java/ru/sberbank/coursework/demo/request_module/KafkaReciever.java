package ru.sberbank.coursework.demo.request_module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.sberbank.coursework.demo.data.AnswerData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sberbank.coursework.demo.data.AnswerList;
import ru.sberbank.coursework.demo.pojo.LoanOffer;
import ru.sberbank.coursework.demo.repositories.LoanOfferCrudRepository;

@EnableKafka
@Service
public class KafkaReciever {
//    private final Logger logger = LoggerFactory.getLogger(KafkaAnswerReciever.class);
private AnswerList answerList = AnswerList.getInstance();
//    LoanOfferCrudRepository loanOfferCrudRepository;
    /**
     * Получение сообщений от сервиса KAFKA
     * Вывод информации о получении ответа в логер
     * @param msg Строка сообщения
     */
    @KafkaListener(topics = "test_sender", groupId = "app.1")
    public void msgListener(String msg) {
        ObjectMapper mapper = new ObjectMapper();
        AnswerData answerData = new AnswerData();
        try {
        answerData  = mapper.readValue(msg, AnswerData.class);
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
        answerList.add(answerData);

   }
}
