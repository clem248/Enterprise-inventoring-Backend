package kg.inai.inventoring.controller;


import kg.inai.inventoring.dto.ClientDTO;
import kg.inai.inventoring.entity.Client;
import kg.inai.inventoring.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/clients")
@Validated
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('client.read')")
    public ResponseEntity<Page<ClientDTO>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "fullName") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String fullNamePrefix,
            @RequestParam(required = false) String loginPrefix) {
        Page<ClientDTO> clientsPage;

        if ((fullNamePrefix != null && !fullNamePrefix.isEmpty()) || (loginPrefix != null && !loginPrefix.isEmpty())) {
            clientsPage = clientService.findByFilters(fullNamePrefix, loginPrefix, PageRequest.of(page, size, Sort.Direction.fromString(sortOrder), sortField));
        } else {
            clientsPage = clientService.getClients(page, size, sortField, sortOrder);
        }

        if (clientsPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientsPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('client.read')")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable("id") long id) {
        Optional<ClientDTO> clientData = clientService.getClientById(id);

        return clientData.map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('client.create')")
    public ResponseEntity<Client> createClient(@RequestBody ClientDTO client) {
        Client clientData = clientService.createClient(client);
        return new ResponseEntity<>(clientData, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('client.create')")
    public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody ClientDTO updatedClient) {
        Client clientData = clientService.updateClient(id, updatedClient);
        if (clientData != null) {
            return new ResponseEntity<>(clientData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('client.delete')")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") long id) {
        clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
