package ru.sberbank.coursework.bankanswer.answer_module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BankAnswerRestController {
    private Object AnswerData;

    @Autowired
    private final AnswerLogic answerLogic;

    private final Logger logger = LoggerFactory.getLogger(KafkaAnswerReciever.class);

    /**
     * Контроллер по умолчанию
     *
     * @param answerLogic
     */
    public BankAnswerRestController(AnswerLogic answerLogic) {
        this.answerLogic = answerLogic;
    }

    /**
     * Контроллер POST для SPRING REST запросов.
     * Принимает в качетве параметров JSON строку в виде параметра loan_request_str и предает ее методу send в бизнес-логику
     *
     * @param loan_request_str -  объект запроса в виде JSON строки
     * @return
     */
    @PostMapping("/form_in")
    ResponseEntity<String> inputAnswer(@RequestBody String loan_request_str) {
        logger.info(String.format("BA-RESTController - message: %s", "Recieve REST " + loan_request_str));
        answerLogic.send(loan_request_str, "REST");
        return ResponseEntity.ok().build();
    }
}




