package com.example.citronix.services;

import com.example.citronix.model.Harvest;
import com.example.citronix.model.HarvestDetail;

import java.util.UUID;

public interface HarvestService {

    Harvest save(Harvest harvest);
    HarvestDetail saveHarvestDetail(UUID harvestId, UUID treeId, double quantity);
}
