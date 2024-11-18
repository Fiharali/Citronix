package com.example.citronix.services.interfaces;

import com.example.citronix.domain.Sale;

import java.util.List;
import java.util.UUID;

public interface SaleService {

    Sale createSale(UUID harvestId, UUID clientId, double unitPrice, double quantity);

    List<Sale> getAllSales();

    Sale getSaleById(UUID saleId);
}
