package ru.sberbank.coursework.demo.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.coursework.demo.data.Schedule;

import java.util.Iterator;
import java.util.Map;

@Service
public class RestService {
    private final RestTemplate restTemplate;
    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Schedule getApi(Map<String, String> param){
        String url="http://schedule:8087/api/?";
        Iterator it = param.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry item = (Map.Entry)it.next();
            url = url + item.getKey() + "=" + item.getValue() + "&";
        }
        return this.restTemplate.getForObject(url, Schedule.class);
    }

}
