package kg.inai.inventoring.service;

import kg.inai.inventoring.dto.ClientDTO;
import kg.inai.inventoring.dto.ClientDTOMapper;
import kg.inai.inventoring.entity.Client;
import kg.inai.inventoring.entity.Role;
import kg.inai.inventoring.repository.ClientRepository;
import kg.inai.inventoring.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;
    private final ClientDTOMapper clientDTOMapper;

    public Page<ClientDTO> getClients(int pageNumber, int pageSize, String sortField, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Client> clientsPage = clientRepository.findAll(pageRequest);
        return clientsPage.map(client -> clientDTOMapper.apply(client));
    }

    public Page<ClientDTO> findByFilters(String fullNamePrefix, String loginPrefix, PageRequest pageable) {
        Page<Client> clients = clientRepository.findByFilters(fullNamePrefix, loginPrefix, pageable);
        return clients.map(client -> clientDTOMapper.apply(client));
    }

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientDTOMapper)
                .collect(Collectors.toList());
    }

    public Optional<ClientDTO> getClientById(Long id) {
        return clientRepository.findById(id).map(clientDTOMapper);
    }

    @Transactional
    public Client createClient(ClientDTO clientDTO) {
        Client client = convertToEntity(clientDTO, new Client());
        boolean existsByLogin = clientRepository.existsByLogin(client.getLogin());
        if(existsByLogin){
            throw new DataIntegrityViolationException("Client with login '" + client.getLogin() + "' already exists.");
        }
        return clientRepository.save(client);
    }

    @Transactional
    public Client updateClient(Long id, ClientDTO clientDTO) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            Client client = convertToEntity(clientDTO, clientOptional.get());
            return clientRepository.save(client);
        }
        return null;
    }

    public void delete(Long id) {
        clientRepository.delete(clientRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Client not found")));
    }


    private Client convertToEntity(ClientDTO clientDTO, Client client) {
        client.setFullName(clientDTO.fullName());
        client.setLogin(clientDTO.login());
        if (clientDTO.password() != null){
            client.setPassword(encoder.encode(clientDTO.password()));
        }
        client.setIpAddresses(clientDTO.ipAddresses());

        return client;
    }
}
