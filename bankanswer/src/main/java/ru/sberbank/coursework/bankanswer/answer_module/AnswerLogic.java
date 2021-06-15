package ru.sberbank.coursework.bankanswer.answer_module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.coursework.bankanswer.data.AnswerData;
import ru.sberbank.coursework.bankanswer.data.AnswerList;
import ru.sberbank.coursework.bankanswer.data.Loan_Answer;
import ru.sberbank.coursework.bankanswer.data.Loan_request;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@NoArgsConstructor
public class AnswerLogic {
    private AnswerList answerList = AnswerList.getInstance();

    private final Logger logger = LoggerFactory.getLogger(KafkaAnswerReciever.class);

    @Autowired
    private KafkaAnswerSender kafkaAnswerSender;

    @Autowired
    private RestBankAnswerSender restBankAnswerSender;

    private String[] COMMENTS = new String[]{"",
            "Неверый документ",
            "Плохая история",
            "Кредит не выдается",
            "Запрос с таким ID уже был"};


    @Setter
    private class Answers implements Runnable {

        private String id;


        /**
         * Основной метод генерации ответа.
         * Ответ вормируется за 5-35 секунд.
         * Формируется ответ (0 - отказ/1 - одобрение)
         * К отказу случайно формируется комментарий
         * С помощью механизма Spring REST аправляется ответ
         */

        public void run() {
            long time = (long) (5000 + Math.random() * 30000);
            logger.info(String.format("AnswerLogic - message: %s", "Принят ID=" + id + " Начинаем обработку"));
            try {
                Thread.sleep(time);
            } catch (Exception e) {
                logger.error(String.format("AnswerLogic - error: %s", e.toString()));
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
            if (answerList.getRec(id).getSender().equals("REST")) {
                restBankAnswerSender.sendOrder(answerList.getRec(id).getLoan_request().getSender_addr(), answerData);
            }
            if (answerList.getRec(id).getSender().equals("KAFKA")) {
                kafkaAnswerSender.sendOrder(answerList.getRec(id).getLoan_request().getSender_addr(), id, answerData);
            }
        }
    }

    /**
     * Метод принимает на вход JSON запроса и формирует поток для расчета ответа
     *
     * @param json_str - строка с запросом
     */
    public void send(String json_str, String sender) {

        ObjectMapper mapper = new ObjectMapper();
        Loan_request loan_request = null;
        try {
            loan_request = mapper.readValue(json_str, Loan_request.class);
        } catch (JsonProcessingException e) {
            logger.error(String.format("AnswerLogic - ERROR: %s", e.toString()));
        }
        if (Objects.nonNull(loan_request)) {
            if (Objects.nonNull(answerList.getRec(loan_request.getId()))) {
                logger.info(String.format("AnswerLogic - message: %s", "запрос с ID=" + loan_request.getId() + " уже  был"));
                AnswerData answerData = new AnswerData(loan_request.getId(), 2, COMMENTS[4]);
                if (sender.equals("REST")) {
                    restBankAnswerSender.sendOrder(loan_request.getSender_addr(),answerData);
                }
                if (sender.equals("KAFKA")) {
                    kafkaAnswerSender.sendOrder(loan_request.getSender_addr(),answerData.getId(),answerData);
                }
            } else {

                Loan_Answer loan_answer = Loan_Answer
                        .builder()
                        .loan_request(loan_request)
                        .status(2)
                        .dateStart(new Date())
                        .sender(sender)
                        .build();

                answerList.add(loan_answer);
                Answers answers = new Answers();
                answers.setId(loan_request.getId());
                Thread answer = new Thread(answers);
                answer.start();
            }
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