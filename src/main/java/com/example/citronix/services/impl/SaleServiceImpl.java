package com.example.citronix.services.impl;

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
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;
    private final ClientRepository clientRepository;

    public SaleServiceImpl(SaleRepository saleRepository, HarvestRepository harvestRepository, ClientRepository clientRepository) {
        this.saleRepository = saleRepository;
        this.harvestRepository = harvestRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Sale createSale(UUID harvestId, UUID clientId, double unitPrice, double quantity) {
        // Retrieve the harvest
        Harvest harvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));

        // Retrieve the client
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        // Calculate revenue
        double revenue = unitPrice * quantity;

        // Create and save the sale
        Sale sale = Sale.builder()
                .harvest(harvest)
                .client(client)
                .unitPrice(unitPrice)
                .quantity(quantity)
                .revenue(revenue)
                .saleDate(LocalDate.now())
                .build();

        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public Sale getSaleById(UUID saleId) {
        return saleRepository.findById(saleId)
                .orElseThrow(() -> new IllegalArgumentException("Sale not found"));
    }
}
