package ru.sberbank.coursework.bankanswer.answer_module;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BankAnswerRestController {
    private Object AnswerData;

    /**
     * Контроллер POST для SPRING REST запросов.
     * Принимает в качетве параметров JSON строку в виде параметра loan_request_str и предает ее методу send в бизнес-логику
     * @param loan_request_str -  объект запроса в виде JSON строки
     * @return
     */
    @PostMapping("/form_in")
    ResponseEntity<String> inputAnswer(@RequestBody String loan_request_str) {
        System.out.println("Запрос " + loan_request_str);
        AnswerLogic answerLogic = new AnswerLogic();
        answerLogic.send(loan_request_str);
        return ResponseEntity.ok().build();
    }
}




