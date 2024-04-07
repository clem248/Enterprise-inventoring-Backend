package kg.inai.inventoring.service;

import kg.inai.inventoring.entity.Client;
import kg.inai.inventoring.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }
    public Optional<Client> getClientById(Long id){
        return clientRepository.findById(id);
    }
    public Client createClient(Client client){
        return clientRepository.save(client);
    }
    public Client updateClient(Long id, Client updatedClient){
        Optional<Client> existingClient = clientRepository.findById(id);
        if(existingClient.isPresent()){
            Client client = existingClient.get();
            client.setLogin(updatedClient.getLogin());
            client.setLogo(updatedClient.getLogo());
            client.setPassword(updatedClient.getPassword());

            return clientRepository.save(client);
        }else{
            return null;
        }
    }
    public void deleteClient(Long id){
        clientRepository.deleteById(id);
    }

}
