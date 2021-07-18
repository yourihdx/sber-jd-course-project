package ru.sberbank.coursework.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sberbank.coursework.demo.data.Schedule;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RestServiceTest {

    private Logger logger = LoggerFactory.getLogger(RestServiceTest.class);

    @Autowired
    RestService service;

    @Test
    public void getApi_Test() {

        Map<String, String> param = new HashMap<>();

        param.put("creditAmount", String.valueOf(200_000));
        param.put("percentRate", String.valueOf(10));
        param.put("creditTerm", String.valueOf(60));
        param.put("annuityPayment", "true");

        Schedule schedule = service.getApi(param);
        logger.info("RestServiceTest: " + String.valueOf(schedule));

        // TODO: assert service return values
    }
}
