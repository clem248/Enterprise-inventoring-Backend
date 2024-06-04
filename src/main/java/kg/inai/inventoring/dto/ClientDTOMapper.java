package kg.inai.inventoring.dto;

import kg.inai.inventoring.entity.Client;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ClientDTOMapper implements Function<Client, ClientDTO> {

    @Override
    public ClientDTO apply(Client client) {

        return new ClientDTO(
                client.getId(),
                client.getFullName(),
                client.getLogin(),
                client.getPassword(),
                client.getIpAddresses()
                );
    }
}
