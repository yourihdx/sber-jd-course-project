package ru.sberbank.coursework.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.coursework.demo.pojo.Client;

import java.util.List;

@Repository
public interface ClientCrudRepository extends CrudRepository<Client, Integer> {

    Client findById(int id);

    List<Client> findAllByIsDeletedIsFalse();
    Client findClientByIdAndIsDeletedIsFalse(int clientId);
    List<Client> findClientByLoginAndPasswordAndIsDeletedIsFalse(String login, String password);
    List<Client> findClientByLoginAndIsDeletedIsFalse(String login);
    List<Client> findClientBypassportSeriesNumAndIsDeletedIsFalse(String passport);
    @Query(value = "select c from Client c where c.passportSeriesNum = ?1 and c.id != ?2")
    List<Client> findClientBypassportSeriesNumAndIdIsNotEqualAndIsDeletedIsFalse(String passport, int id);
    Client save(Client client);

    @Query(value = "select count(birth_date) from client " +
            "where extract(year from now()) - extract(year from birth_date) between ?1 and ?2 ;" , nativeQuery = true)
    Long getDateInterval(int begin, int end);

    @Query(value = "select avg(extract(year from now()) - " +
            "extract(year from birth_date)) from client", nativeQuery = true)
    int avgAge();
}
