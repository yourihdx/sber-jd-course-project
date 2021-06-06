package service;

import domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ClientsRepository;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private final ClientsRepository clientsRepository;

    public ClientService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public void createClient(Client client) {
        client.setDeleted(false);
        clientsRepository.save(client);
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Client> findAll() {
        List<Client> result = clientsRepository.findAll().stream().filter(it -> !it.isDeleted()).collect(Collectors.toList());
        return result;
    }

    public Client findById(int id) {
        return clientsRepository.getById(id);
    }

    public List<Client> findAllByName(String name) {
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
            Client client = clientsRepository.getById(id);
            client.setDeleted(true);
            return true;
        }
        return false;
    }


}
