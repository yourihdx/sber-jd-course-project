package ru.sberbank.coursework.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.coursework.demo.pojo.Payment;

public interface LoanTypeCrudRepository extends CrudRepository<Payment, Integer> {
    Payment findById(int id);
}
