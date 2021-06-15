package ru.sberbank.coursework.bankanswer.answer_module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.coursework.bankanswer.data.AnswerList;
import ru.sberbank.coursework.bankanswer.data.Loan_Answer;
import ru.sberbank.coursework.bankanswer.data.Loan_request;
import ru.sberbank.coursework.bankanswer.data.AnswerData;

import java.util.*;

@NoArgsConstructor
public class AnswerLogic {
    private AnswerList answerList = AnswerList.getInstance();


    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    private class Answers implements Runnable {

        private String id;

        /**
         * Основной метод генерации ответа.
         * Ответ вормируется за 5-12 секунд.
         * Формируется ответ (0 - отказ/1 - одобрение)
         * К отказу случайно формируется комментарий
         * С помощью механизма Spring REST аправляется ответ
         */

        public void run() {
            String[] COMMENTS = new String[]{"",
                    "Неверый документ",
                    "Плохая история",
                    "Кредит не выдается"};
            String POST_URL = "http://localhost:8086/bankanswer/json_in";

            long time = (long) (5000 + Math.random() * 30000);
            System.out.println("Принят ID=" + id + " Начинаем обработку");
            try {
                Thread.sleep(time);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            int res = Math.toIntExact(Math.round(Math.random()));
            String comment = "";

            if (res == 1) {
                comment = COMMENTS[0];
            } else {
                Integer rnd = (int) (Math.random() * 3 + 0.1);
                comment = COMMENTS[rnd];
            }
            answerList.getRec(id).setStatus(res);
            answerList.getRec(id).setComment(comment);
            answerList.getRec(id).setDateEnd(new Date());
            AnswerData answerData = new AnswerData(id, res, comment);
            ObjectMapper mapper = new ObjectMapper();
            String json_str = null;
            try {
                json_str = mapper.writeValueAsString(answerData);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<AnswerData> entity = new HttpEntity<>(answerData, headers);
            AnswerData answ = restTemplate.postForObject(POST_URL, answerData, AnswerData.class);
            System.out.println("Ура! Получено подтверждение: " + answ);
        }
    }

    /**
     * Метод принимает на вход JSON запроса и формирует поток для расчета ответа
     *
     * @param json_str - строка с запросом
     */
    public void send(String json_str) {

        ObjectMapper mapper = new ObjectMapper();
        Loan_request loan_request = null;
        try {
            loan_request = mapper.readValue(json_str, Loan_request.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (Objects.nonNull(loan_request)) {
            Loan_Answer loan_answer = Loan_Answer
                    .builder().
                            loan_request(loan_request).
                            status(2).
                            dateStart(new Date())
                    .build();

            answerList.add(loan_answer);
            Thread answer = new Thread(new Answers(loan_request.getId()));
            answer.start();
        }
    }

    /**
     * Формирование массива с запросами с сотояниями для передачи в интерфейс.
     * Формирует из MAP БД список записей
     *
     * @return - массив запросов
     */
    public List<Loan_Answer> getData() {
        return answerList.list();
    }

    /**
     * Формирование массива с запросами с сотояниями для передачи в интерфейс.
     * Формирует из MAP БД список записей, ограниченный по длине
     *
     * @param size - количество записей
     * @return
     */

    public List<Loan_Answer> getData(int size) {
        return answerList.list(size);
    }


    /**
     * Возвращаем количество записей
     *
     * @return
     */
    public int size() {
        return answerList.size();
    }
}