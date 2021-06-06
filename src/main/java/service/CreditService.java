package service;

import domain.Client;
import domain.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CreditRepository;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditService {

    @Autowired
    private final CreditRepository creditRepository;

    public CreditService(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    public void createCredit(Credit credit) {
        creditRepository.save(credit);
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Credit> findAll() {
        List<Credit> result = creditRepository.findAll().stream().filter(it -> !it.isDeleted()).collect(Collectors.toList());
        return result;
    }

    public Credit findById(int id) {
        return creditRepository.getOne(id);
    }

    public List<Credit> findAllByMaxPeriod(int maxPeriod) {
        return creditRepository.findAllByMaxPeriod(maxPeriod);
    }

    public boolean update(Credit credit, int id) {
        if (creditRepository.existsById(id)) {
            credit.setId(id);
            creditRepository.save(credit);
            return true;
        }

        return false;
    }

    public boolean delete(int id) {
        if (creditRepository.existsById(id)) {
            Credit credit = creditRepository.getById(id);
            credit.setDeleted(true);
            return true;
        }
        return false;
    }

}
