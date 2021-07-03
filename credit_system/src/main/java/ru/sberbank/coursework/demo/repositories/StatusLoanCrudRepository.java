package ru.sberbank.coursework.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.coursework.demo.pojo.StatusLoan;

import java.util.List;

@Repository
public interface StatusLoanCrudRepository extends CrudRepository<StatusLoan, Integer> {
StatusLoan findById(int id);
List<StatusLoan> findAll();
}
