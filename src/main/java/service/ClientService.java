package service;

import domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ClientsRepository;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private final ClientsRepository clientsRepository;

    public ClientService(ClientsRepository clientsRepository){
        this.clientsRepository = clientsRepository;
    }

    public void createClient(Client client){
        clientsRepository.save(client);
    }
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Client> findAll(){
        return clientsRepository.findAll();
    }

    public Client findById(int id){
        return clientsRepository.getOne(id);
    }

    public List<Client> findAllByName(String name){
        return clientsRepository.findAllByFull_name(name);
    }

    public boolean update(Client client, int id) {
        if (clientsRepository.existsById(id)) {
            client.setId(id);
            clientsRepository.save(client);
            return true;
        }

        return false;
    }

    public boolean delete(int id) {
        if (clientsRepository.existsById(id)) {
            clientsRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
