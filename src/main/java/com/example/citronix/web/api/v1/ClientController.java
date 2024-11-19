package com.example.citronix.web.api.v1;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.citronix.domain.Client;
import com.example.citronix.services.ClientService;
import com.example.citronix.web.vm.client.ClientVM;
import com.example.citronix.web.vm.client.ClientResponseVM;
import com.example.citronix.web.vm.mapper.ClientMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;



    @PostMapping
    public ResponseEntity<ClientResponseVM> createClient(@RequestBody @Valid ClientVM clientVM) {
        Client clientEntity = clientMapper.clientVMToClient(clientVM);
        Client savedClient = clientService.createClient(clientEntity);
        ClientResponseVM response = clientMapper.toClientResponseVM(savedClient);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseVM>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        List<ClientResponseVM> responses = clientMapper.toClientResponseVMs(clients);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseVM> getClientById(@PathVariable UUID id) {
        Client client = clientService.getClientById(id);
        ClientResponseVM response = clientMapper.toClientResponseVM(client);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseVM> updateClient(@PathVariable UUID id, @RequestBody @Valid ClientVM clientVM) {
        Client updatedClient = clientService.updateClient(id,clientMapper.clientVMToClient(clientVM));
        ClientResponseVM response = clientMapper.toClientResponseVM(updatedClient);
        //
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable UUID id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
    }
}
