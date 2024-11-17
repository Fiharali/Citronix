package com.example.citronix.services.impl;

import com.example.citronix.model.Sale;
import com.example.citronix.repository.SaleRepository;
import com.example.citronix.services.SaleService;
import com.example.citronix.services.dto.SaleDTO;
import org.springframework.stereotype.Service;


@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public SaleDTO save(Sale sale) {
        double totalQuantity = sale.getHarvest().getTotalQuantity();
        double revenue = totalQuantity * sale.getUnitPrice();

        SaleDTO saleDTO = new SaleDTO(
            sale.getSaleDate(),
            sale.getUnitPrice(),
            sale.getClient(),
            revenue
        );

        saleRepository.save(sale);

        return saleDTO;
    }
}
