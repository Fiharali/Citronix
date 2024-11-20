package com.example.citronix.services;

import com.example.citronix.domain.Sale;

import java.util.List;
import java.util.UUID;

public interface SaleService {

    Sale createSale(Sale sale , UUID clientId, UUID harvestId);

    List<Sale> getAllSales();

    void delete(UUID id);
}
