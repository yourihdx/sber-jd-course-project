package repository;

import domain.Bank;
import domain.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank,Integer> {

    List<Bank> findAllByBank_name(String bankName);

}
