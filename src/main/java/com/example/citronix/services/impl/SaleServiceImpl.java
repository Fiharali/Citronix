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
    public Sale createSale(Sale sale , UUID clientId, UUID harvestId) {

        Harvest harvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));


        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        double revenue = sale.getQuantity() * sale.getUnitPrice();


        sale.setSaleDate(LocalDate.now());
        sale.setRevenue(revenue);
        sale.setClient(client);
        sale.setHarvest(harvest);

        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }


}
