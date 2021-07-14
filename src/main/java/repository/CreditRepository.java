package repository;

import domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import domain.Credit;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit,Integer> {

    List<Credit> findAllByMaxPeriod(int maxPeriod);
}
