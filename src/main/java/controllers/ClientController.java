package controllers;

import domain.Client;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.ClientService;

import java.util.List;


@RequestMapping("clients")
public class ClientController {

    ClientService clientService;

    @GetMapping
    public String getAllClients(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clientList", clients);
        return "clients";
    }

    @PostMapping
    public String createClient(@RequestBody Client client) {
        clientService.createClient(client);
        return "clients";
    }

    @PutMapping("{id}")
    public String editClient(
            @PathVariable("id") int id,
            @RequestBody Client client) {
        clientService.update(client,id);
        return "clients";
    }

    @DeleteMapping("{id}")
    public String deleteClient(@PathVariable("id") int id) {
        clientService.delete(id);
        return "clients";
    }

    @GetMapping("{id}")
    public Client getClient(@PathVariable String id) {
        Integer ids = Integer.parseInt(id);
        return clientService.findById(ids);
    }
}
