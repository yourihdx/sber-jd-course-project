package ru.sberbank.coursework.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.coursework.demo.pojo.Loan;

import java.util.List;

    @Repository
    public interface LoanCrudRepository extends CrudRepository<Loan, Integer> {

//        @Query("select b.name as bankName, l.id as id, l.limit as limit, l.period as period, l.percent as percent, p.name as payment from Bank b, Loan l, Payment p where l.period >= ?1 and l.creditSum >= ?2 and l.percent <= ?3 and l.bankId = b.id and l.productTypeId = p.id")
//        List<LoanList> findAllByClientCriteria(int period, double creditSum, double percent);


//        @Query("select b.name as bankName, l.id as id, l.limit as limit, l.period as period, l.percent as percent, p.name as payment from Bank b, Loan l, Payment p where l.bankId = b.id and l.productTypeId = p.id and l.period >= ?1 and l.creditSum >= ?2 and l.percent <= ?3")
//        List<LoanList> findAllByClientCriteria(int period, double creditSum, double percent);
        @Query("select l from Loan l where l.period >= ?1 and l.creditSum >= ?2 and l.percent <= ?3")
        List<Loan> findAllForClient(int period, double creditSum, double percent);
        List<Loan> findAll();
        Loan findById(int id);
    }
