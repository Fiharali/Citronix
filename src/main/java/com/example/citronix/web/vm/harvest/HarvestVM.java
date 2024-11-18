package com.example.citronix.web.vm.harvest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import com.example.citronix.domain.enums.Season;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class HarvestVM {

    @NotNull(message = "Field ID is required.")
    private UUID fieldId;

    @NotNull(message = "Season is required.")
    private Season season;

    @NotEmpty(message = "Harvest details cannot be empty.")
    private List<@Valid HarvestDetailVM> harvestDetails;
}
