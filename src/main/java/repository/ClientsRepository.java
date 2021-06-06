package repository;

import domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientsRepository  extends JpaRepository<Client,Integer> {


    List<Client>findAllByFull_name(String full_name);


}
