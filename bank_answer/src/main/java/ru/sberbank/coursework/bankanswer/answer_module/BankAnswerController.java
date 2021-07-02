package ru.sberbank.coursework.bankanswer.answer_module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sberbank.coursework.bankanswer.data.Loan_Answer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
public class BankAnswerController {

    @Autowired
    private final AnswerLogic answerLogic;

    private final Logger logger = LoggerFactory.getLogger(KafkaAnswerReciever.class);

    /**
     * Конструктор по умолчанию
     * @param answerLogic
     */

    public BankAnswerController(AnswerLogic answerLogic) {
        this.answerLogic = answerLogic;
    }

    /**
     * Контроллер GET для главной страницы
     * @param model
     * @return
     */
    @GetMapping("/data")
    public String printHello(ModelMap model) {
        String pattern = "dd/MM/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        String str = df.format(new Date());
        model.addAttribute("time", str);
        String capacitystr = "";
        AnswerLogic answerLogic = new AnswerLogic();
        List<Loan_Answer> data = answerLogic.getData(10);
        if (data.size() < 10) {
            capacitystr = data.size() + "/" + answerLogic.size();
        } else {
            capacitystr = "10/" + answerLogic.size();
        }
        model.addAttribute("capacitystr", capacitystr);
        model.addAttribute("data", data);
        return "bankanswer.html";
    }

    /**
     * Контроллер POST главной страницы.
     * Принимает в качетве параметров JSON строку в виде параметра request и предает ее методу send в бизнес-логику
     *
     * @param allRequestParams
     * @param model
     * @return
     */
    @PostMapping("/data")
    public String addHello(@RequestParam Map<String, String> allRequestParams, ModelMap model) {
        String jsonStr = allRequestParams.get("request");

        if (Objects.nonNull(jsonStr)) {
            logger.info(String.format("BA-Controller - message: %s", "Запрос HTTP GET " + jsonStr));
            answerLogic.send(jsonStr,"HTTP");
        }
        String pattern = "dd/MM/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        String str = df.format(new Date());
        model.addAttribute("time", str);
        String capacitystr = "";
        List<Loan_Answer> data = answerLogic.getData(10);
        if (data.size() < 10) {
            capacitystr = data.size() + "/" + data.size();
        } else {
            capacitystr = "10/" + data.size();
        }
        model.addAttribute("capacitystr", capacitystr);
        model.addAttribute("data", data);
        return "bankanswer.html";
    }

}
