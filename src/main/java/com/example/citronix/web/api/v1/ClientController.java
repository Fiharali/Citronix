package com.example.citronix.web.api.v1;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.citronix.domain.Client;
import com.example.citronix.services.interfaces.ClientService;
import com.example.citronix.web.vm.ClientVm.ClientVM;
import com.example.citronix.web.vm.ClientVm.ClientResponseVM;
import com.example.citronix.web.vm.mapper.ClientMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;



    @PostMapping("/create")
    public ResponseEntity<ClientResponseVM> createClient(@RequestBody @Valid ClientVM clientVM) {
        Client clientEntity = clientMapper.clientVMToClient(clientVM);
        Client savedClient = clientService.createClient(clientEntity.getName(), clientEntity.getEmail(), clientEntity.getPhoneNumber());
        ClientResponseVM response = clientMapper.clientToClientResponseVM(savedClient);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientResponseVM>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        List<ClientResponseVM> responses = clientMapper.clientsToClientResponseVMs(clients);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponseVM> getClientById(@PathVariable UUID clientId) {
        Client client = clientService.getClientById(clientId);
        ClientResponseVM response = clientMapper.clientToClientResponseVM(client);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{clientId}")
    public ResponseEntity<ClientResponseVM> updateClient(@PathVariable UUID clientId, @RequestBody @Valid ClientVM clientVM) {
        Client updatedClient = clientService.updateClient(clientId, clientVM.getName(), clientVM.getEmail(), clientVM.getPhoneNumber());
        ClientResponseVM response = clientMapper.clientToClientResponseVM(updatedClient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<String> deleteClient(@PathVariable UUID clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
    }
}
