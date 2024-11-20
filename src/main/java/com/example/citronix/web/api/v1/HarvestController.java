package com.example.citronix.web.api.v1;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.citronix.domain.Harvest;
import com.example.citronix.domain.HarvestDetail;
import com.example.citronix.services.HarvestService;
import com.example.citronix.web.vm.harvest.HarvestVM;
import com.example.citronix.web.vm.harvest.HarvestResponseVM;
import com.example.citronix.web.vm.mapper.HarvestMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/harvests")
@RequiredArgsConstructor
public class HarvestController {

    private final HarvestService harvestService;
    private final HarvestMapper harvestMapper;



    @PostMapping
    public ResponseEntity<HarvestResponseVM> save(@RequestBody @Valid HarvestVM harvestVM) {

        List<HarvestDetail> harvestDetails = harvestMapper.harvestDetailVMsToHarvestDetails(harvestVM.getHarvestDetails());

        Harvest savedHarvest = harvestService.createHarvest(
                harvestDetails,
                harvestVM.getSeason(),
                harvestVM.getQuantity()
        );

        HarvestResponseVM response = harvestMapper.harvestToHarvestResponseVM(savedHarvest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<HarvestResponseVM>> getAllHarvests() {
        List<Harvest> harvests = harvestService.getAllHarvests();
        List<HarvestResponseVM> responses = harvestMapper.harvestsToHarvestResponseVMs(harvests);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")

    public ResponseEntity<HarvestResponseVM> getHarvestById(@PathVariable UUID id) {
        Harvest harvest = harvestService.getHarvestById(id);
        HarvestResponseVM response = harvestMapper.harvestToHarvestResponseVM(harvest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHarvest(@PathVariable UUID id) {
        harvestService.deleteHarvest(id);
        return new ResponseEntity<>("Harvest deleted successfully.", HttpStatus.OK);
    }
}
