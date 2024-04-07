package kg.inai.inventoring.controller;


import kg.inai.inventoring.entity.Client;
import kg.inai.inventoring.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/clients")
@Validated
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }
    @GetMapping
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id){
        Optional<Client> client = clientService.getClientById(id);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client){
        Client createdClient = clientService.createClient(client);
        return  new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient){
        Client client = clientService.updateClient(id, updatedClient);
        return (client != null) ? new ResponseEntity<>(client, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
