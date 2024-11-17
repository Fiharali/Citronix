package com.example.citronix.services;

import com.example.citronix.model.Sale;
import com.example.citronix.services.dto.SaleDTO;

public interface SaleService {

    SaleDTO save(Sale sale);
}
