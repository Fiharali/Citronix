package com.example.citronix.web.vm.sale;

import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class SaleVM {

    @NotNull(message = "Harvest ID is required")
    private UUID harvestId;

    @NotNull(message = "Client ID is required")
    private UUID clientId;

    @NotNull(message = "date of sale is required")
    @PastOrPresent(message = "Sale date must be in the past or present")
    private LocalDate saleDate;

    @Positive(message = "Unit price must be greater than zero")
    private double unitPrice;

    @Positive(message = "Quantity must be greater than zero")
    private double quantity;


}