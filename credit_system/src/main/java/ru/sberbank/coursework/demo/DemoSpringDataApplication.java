package ru.sberbank.coursework.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.sberbank.coursework.demo.pojo.Agreement;
import ru.sberbank.coursework.demo.pojo.Client;
import ru.sberbank.coursework.demo.repositories.AgreementCrudRepository;
import ru.sberbank.coursework.demo.repositories.ClientCrudRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class DemoSpringDataApplication implements CommandLineRunner {

//    @Autowired
//    private ClientCrudRepository clientCrudRepository;

    @Autowired
    private AgreementCrudRepository agreementCrudRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringDataApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Agreement agreement = new Agreement(1,1,1, new BigDecimal(50_000), LocalDate.parse("2021-06-20"), 60);
        this.agreementCrudRepository.save(agreement);
        List<Agreement> result = this.agreementCrudRepository.findAllByIsDeletedIsFalseAndClientId(1);
        System.out.println(result);

//        Client client = new Client("1",LocalDate.parse("2020-12-12"),"88007070070",
//                "1111@111.111", "111-11111", "1", "1");
//        this.clientCrudRepository.save(client);
//        List<Client> clients = this.clientCrudRepository.findAllByIsDeletedIsFalse();
//        System.out.println(clients);

    }

}
