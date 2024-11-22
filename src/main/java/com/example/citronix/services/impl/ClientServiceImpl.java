package com.example.citronix.services.impl;

import com.example.citronix.exceptions.ResourceDublicatedException;
import com.example.citronix.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.example.citronix.domain.Client;
import com.example.citronix.repositories.ClientRepository;
import com.example.citronix.services.ClientService;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client client) {

        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new ResourceDublicatedException("A client with this email already exists.");
        }

        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(UUID clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    @Override
    public Client updateClient(UUID id , Client client ) {

        Client clientExist = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        if (!client.getEmail().equals(client.getEmail()) && clientRepository.existsByEmail(client.getEmail())) {
            throw new ResourceDublicatedException("A client with this email already exists.");
        }
        clientExist.setEmail(client.getEmail());
        clientExist.setName(client.getName());
        clientExist.setPhoneNumber(client.getName());

        return clientRepository.save(clientExist);
    }

    @Override
    public void deleteClient(UUID clientId) {
        //
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Client not found");
        }
        clientRepository.deleteById(clientId);
    }
}
