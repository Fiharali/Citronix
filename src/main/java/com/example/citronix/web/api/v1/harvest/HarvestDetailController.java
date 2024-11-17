package com.example.citronix.web.api.v1.harvest;


import com.example.citronix.model.HarvestDetail;
import com.example.citronix.services.HarvestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/harvest-details")
public class HarvestDetailController {


    private final HarvestService harvestService;

    public HarvestDetailController(HarvestService harvestService) {
        this.harvestService = harvestService;
    }

    @PostMapping("/save")
    public ResponseEntity<HarvestDetail> save(
        @RequestParam UUID harvestId,
        @RequestParam UUID treeId,
        @RequestBody double quantity) {
        HarvestDetail detail = harvestService.saveHarvestDetail(harvestId, treeId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(detail);
    }
}
