package service;

import domain.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BankRepository;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.List;

@Service
public class BankService {

    @Autowired
    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public void createCredit(Bank bank){
        bankRepository.save(bank);
    }
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Bank> findAll(){
        return bankRepository.findAll();
    }

    public Bank findById(int id){
        return bankRepository.getOne(id);
    }

    public List<Bank> findAllByMaxPeriod(String bankName){
        return bankRepository.findAllByBank_name(bankName);
    }

    public boolean update(Bank bank, int id) {
        if (bankRepository.existsById(id)) {
            bank.setId(id);
            bankRepository.save(bank);
            return true;
        }

        return false;
    }

    public boolean delete(int id) {
        if (bankRepository.existsById(id)) {
            bankRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
