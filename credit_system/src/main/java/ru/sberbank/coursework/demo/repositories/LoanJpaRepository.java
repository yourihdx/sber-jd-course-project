package ru.sberbank.coursework.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sberbank.coursework.demo.pojo.Loan;
import ru.sberbank.coursework.demo.pojo.LoanList;

import java.util.List;

@Repository
public interface LoanJpaRepository extends JpaSpecificationExecutor<Loan>, JpaRepository<Loan, Integer> {

    @Query("select b.name as bankName, b.id as bankId, l.id as id, l.creditSum as limit, l.period as period, l.percent as percent, p.name as payment, p.id as paymentId from Bank b, Loan l, Payment p where l.bankId = b.id and l.productTypeId = p.id and l.period >= ?1 and l.creditSum >= ?2 and l.percent <= ?3")
    List<LoanList> findAllByClientCriteria(int period, double creditSum, double percent);

}
