package ru.sberbank.coursework.bankanswer.request_module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.coursework.bankanswer.answer_module.KafkaAnswerReciever;

@RestController
public class RequestRestController {

    private final Logger logger = LoggerFactory.getLogger(KafkaAnswerReciever.class);

    private Object AnswerData;

    /**
     * Контроллер POST для SPRING REST запросов.
     * Принимает в качетве параметров JSON строку в виде параметра answerData
     *
     * @param answerData Объект ответа в виде строки
     * @return
     */
    @PostMapping("/json_in")
    ResponseEntity<String> createAnsw(@RequestBody String answerData) {
        logger.info(String.format("BF-RESTController - message: %s", "Recieve REST " + answerData));
        return ResponseEntity.ok().build();
    }
}
