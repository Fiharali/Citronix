package com.example.citronix.web.vm.harvest;

import jakarta.validation.constraints.*;
import lombok.*;
import com.example.citronix.domain.enums.Season;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class HarvestResponseVM {


    private UUID fieldId;

    private Season season;

    private double totalQuantity;

    private List<HarvestDetailResponseVM> harvestDetails;
}
