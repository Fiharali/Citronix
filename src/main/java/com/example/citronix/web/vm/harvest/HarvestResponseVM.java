package com.example.citronix.web.vm.harvest;

import jakarta.validation.constraints.*;
import lombok.*;
import com.example.citronix.domain.enums.Season;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class HarvestResponseVM {

    @NotNull(message = "Field ID is required.")
    private UUID fieldId;

    @NotNull(message = "Season is required.")
    private Season season;

    @Positive(message = "Total quantity must be positive.")
    private double totalQuantity;

    @NotEmpty(message = "Harvest details cannot be empty.")
    private List<HarvestDetailResponseVM> harvestDetails;
}