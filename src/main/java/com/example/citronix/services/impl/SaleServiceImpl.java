package com.example.citronix.services.impl;

import com.example.citronix.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.citronix.domain.Client;
import com.example.citronix.domain.Harvest;
import com.example.citronix.domain.Sale;
import com.example.citronix.repositories.ClientRepository;
import com.example.citronix.repositories.HarvestRepository;
import com.example.citronix.repositories.SaleRepository;
import com.example.citronix.services.SaleService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;
    private final ClientRepository clientRepository;


    @Override
    public Sale createSale(Sale sale , UUID clientId, UUID harvestId) {

        Harvest harvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new ResourceNotFoundException("Harvest not found"));


        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        sale.setClient(client);
        sale.setHarvest(harvest);

        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }


    @Override
    public void delete(UUID id) {

        saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        saleRepository.deleteById(id);
    }
}
