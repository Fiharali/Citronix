package com.example.citronix.services;

import jakarta.validation.constraints.NotEmpty;
import com.example.citronix.domain.Harvest;
import com.example.citronix.domain.HarvestDetail;
import com.example.citronix.domain.enums.Season;

import java.util.List;
import java.util.UUID;

public interface HarvestService {


    Harvest createHarvest( List<HarvestDetail> harvestDetails, Season season, double totalQuantity);

    List<Harvest> getAllHarvests();

    Harvest getHarvestById(UUID harvestId);

    void deleteHarvest(UUID harvestId);
}
