package com.mindhub.hombanking.Services.implementss;

import com.mindhub.hombanking.Services.ClientService;
import com.mindhub.hombanking.models.Client;
import com.mindhub.hombanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServicesIMPL  implements ClientService {
    @Autowired
    public ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    @Override
    public Client getClientById(long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveClients(Client client) {
        clientRepository.save(client);
    }
}
