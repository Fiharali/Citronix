package com.example.citronix.services.interfaces;

import com.example.citronix.domain.Client;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    Client createClient(String name, String email, String phoneNumber);

    List<Client> getAllClients();

    Client getClientById(UUID clientId);

    Client updateClient(UUID clientId, String name, String email, String phoneNumber);

    void deleteClient(UUID clientId);
}
