package com.mindhub.hombanking.Services;

import com.mindhub.hombanking.models.Client;

import java.util.List;

public interface ClientService {
    public List<Client> getAllClients();
    public Client getClientById(long id);
    public Client getClientByEmail(String email);
    public void saveClients(Client client);
}
