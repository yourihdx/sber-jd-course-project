package ru.sberbank.coursework.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.coursework.demo.pojo.Agreement;

import java.util.List;

@Repository
public interface AgreementCrudRepository extends CrudRepository <Agreement, Integer> {

    List<Agreement> findAllByIsDeletedIsFalseAndClientId(int clientId);
Agreement save(Agreement agreement);
}
