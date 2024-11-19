package com.example.citronix.web.vm.sale;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class SaleResponseVM {
    private UUID id;
    private LocalDate saleDate;
    private double unitPrice;
    private double quantity;
    private double revenue;
    private UUID clientId;
    private UUID harvestId;
}