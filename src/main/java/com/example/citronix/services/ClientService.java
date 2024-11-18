package com.example.citronix.services;

import com.example.citronix.domain.Client;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    Client createClient(Client client);

    List<Client> getAllClients();

    Client getClientById(UUID clientId);

    Client updateClient(UUID id ,Client client);

    void deleteClient(UUID clientId);
}
