package ru.sberbank.coursework.demo.controllers.admin;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.coursework.demo.dao.ClientDao;
import ru.sberbank.coursework.demo.domain.Client;

import java.util.List;

@Controller
public class AdminClientController {

    @GetMapping(value = "/clients")
    public String getAllClients(Model model) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        ClientDao clientDao = new ClientDao(sessionFactory);
        List<Client> clients = clientDao.getAllClients();
        model.addAttribute("clientList", clients);
        return "admin/clients";
    }

    @PostMapping(value = "/clients")
    public String createClient(Model model,Client client) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        ClientDao clientDao = new ClientDao(sessionFactory);
        clientDao.create(client);
        List<Client> clients = clientDao.getAllClients();
        model.addAttribute("clientList", clients);
        return "admin/clients";
    }

    @PostMapping("/clients/{id}")
    public String editClient(Model model,
            @PathVariable("id") int id,
            Client client) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        ClientDao clientDao = new ClientDao(sessionFactory);
        model.addAttribute("id", id);
        client.setId(id);
        clientDao.updateClient(client);
        List<Client> clients = clientDao.getAllClients();
        model.addAttribute("clientList", clients);
        return "admin/clients";
    }

    @GetMapping("/clients/delete/{id}")
    public String deleteClient(Model model, @PathVariable("id") int id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        ClientDao clientDao = new ClientDao(sessionFactory);
        clientDao.deleteClient(id);
        List<Client> clients = clientDao.getAllClients();
        model.addAttribute("clientList", clients);
        return "admin/clients";
    }

    @GetMapping("/clients/{id}")
    public String getClient(Model model, @PathVariable String id) {
        Integer ids = Integer.parseInt(id);
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        ClientDao clientDao = new ClientDao(sessionFactory);
        Client client = clientDao.getClient(ids);
        model.addAttribute("client",client);
        return "admin/client";
    }
}
