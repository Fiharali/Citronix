package com.example.citronix.web.vm.harvest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import com.example.citronix.domain.enums.Season;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class HarvestVM {


    @NotNull(message = "Season is required.")
    private Season season;

    @NotNull(message = "Harvest date is required.")
    @PastOrPresent(message = "Harvest date must be in the past or present.")
    private LocalDate harvestDate;

    @NotEmpty(message = "Harvest details cannot be empty.")
    private List<@Valid HarvestDetailVM> harvestDetails;
}
