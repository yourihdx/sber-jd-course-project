package ru.sberbank.coursework.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.coursework.demo.pojo.LoanOffer;

import java.util.List;

@Repository
public interface LoanOfferCrudRepository extends CrudRepository <LoanOffer, Integer> {

    List<LoanOffer> findAllByClientId(int clientId);
    @Query("select l from LoanOffer l where l.id = ?1")
    LoanOffer findLoanById(int id);
    @Query("select l from LoanOffer l where l.status = ?1")
    List<LoanOffer> findLoansByStatus(int status);
    List<LoanOffer> findAllByStatus(int status);
    List<LoanOffer> findAll();

    LoanOffer save(LoanOffer offer);

}
