package ru.sberbank.coursework.bankanswer.request_module;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestRestController {

    private Object AnswerData;

    /**
     * Контроллер POST для SPRING REST запросов.
     * Принимает в качетве параметров JSON строку в виде параметра answerData
     * @param answerData Объект ответа в виде строки
     * @return
     */
    @PostMapping("/json_in")
    ResponseEntity<String> createAnsw(@RequestBody String answerData) {
        System.out.println("Ответ "+answerData);
        return ResponseEntity.ok().build();
    }
}
