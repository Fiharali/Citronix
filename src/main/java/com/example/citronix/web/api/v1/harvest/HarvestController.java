package com.example.citronix.web.api.v1.harvest;


import com.example.citronix.model.Harvest;
import com.example.citronix.services.HarvestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/harvests")
public class HarvestController {

    private final HarvestService harvestService;

    public HarvestController(HarvestService harvestService) {
        this.harvestService = harvestService;
    }


    @PostMapping("/save")
    public ResponseEntity<Harvest> save(@RequestBody Harvest harvest) {
        Harvest createdHarvest = harvestService.save(harvest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHarvest);
    }
}
