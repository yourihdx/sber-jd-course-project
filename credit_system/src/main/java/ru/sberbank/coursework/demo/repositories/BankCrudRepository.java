package ru.sberbank.coursework.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.coursework.demo.pojo.Bank;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankCrudRepository extends CrudRepository<Bank, Integer> {
    Bank findById(int id);
    List<Bank> findAll();
}
