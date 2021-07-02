package ru.sberbank.coursework.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sberbank.coursework.demo.pojo.LoanOffer;
import ru.sberbank.coursework.demo.pojo.LoanOfferList;

import java.util.List;

@Repository
public interface LoanOfferJpaRepository extends JpaSpecificationExecutor<LoanOffer>, JpaRepository<LoanOffer, Integer> {
@Query("select b.name as bankName, b.id as bankId, l.id as id, l.creditSum as limit, l.period as period, l.percent as percent, p.name as payment, l.status as statusId, s.name as status from Bank b, LoanOffer l, Payment p, StatusLoan s where l.bankId = b.id and l.productTypeId = p.id and l.status = s.id and l.clientId = ?1")
List<LoanOfferList> findAllByClientId(int clientId);

    @Query("select b.name as bankName, b.id as bankId, l.id as id, l.creditSum as limit, l.period as period, l.percent as percent, p.name as payment, l.status as statusId, s.name as status from Bank b, LoanOffer l, Payment p, StatusLoan s where l.bankId = b.id and l.productTypeId = p.id and l.status = s.id and l.id = ?1")
    LoanOfferList findLoanOfferById(int id);

    @Query("select b.name as bankName, b.id as bankId, l.id as id, l.creditSum as limit, l.period as period, l.percent as percent, p.name as payment, l.status as statusId, s.name as status from Bank b, LoanOffer l, Payment p, StatusLoan s where l.bankId = b.id and l.productTypeId = p.id and l.status = s.id")
    List<LoanOfferList> findAllLoans();
    @Modifying
    @Query("update LoanOffer l set l.status = ?2 where l.id = ?1")
    void updateStatus(int id, int status);
}
