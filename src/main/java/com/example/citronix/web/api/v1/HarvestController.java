package com.example.citronix.web.api.v1;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.citronix.domain.Harvest;
import com.example.citronix.domain.HarvestDetail;
import com.example.citronix.services.interfaces.HarvestService;
import com.example.citronix.web.vm.HarvestVm.HarvestVM;
import com.example.citronix.web.vm.HarvestVm.HarvestResponseVM;
import com.example.citronix.web.vm.mapper.HarvestMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/harvests")
@RequiredArgsConstructor
public class HarvestController {

    private final HarvestService harvestService;
    private final HarvestMapper harvestMapper;



    @PostMapping("/save")
    public ResponseEntity<HarvestResponseVM> save(@RequestBody @Valid HarvestVM harvestVM) {

        List<HarvestDetail> harvestDetails = harvestMapper.harvestDetailVMsToHarvestDetails(harvestVM.getHarvestDetails());


        Harvest savedHarvest = harvestService.createHarvest(
                harvestVM.getFieldId(),
                harvestDetails,
                harvestVM.getSeason(),
                0
        );

        HarvestResponseVM response = harvestMapper.harvestToHarvestResponseVM(savedHarvest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/all")

    public ResponseEntity<List<HarvestResponseVM>> getAllHarvests() {
        List<Harvest> harvests = harvestService.getAllHarvests();
        List<HarvestResponseVM> responses = harvestMapper.harvestsToHarvestResponseVMs(harvests);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/find/{harvestId}")

    public ResponseEntity<HarvestResponseVM> getHarvestById(@PathVariable UUID harvestId) {
        Harvest harvest = harvestService.getHarvestById(harvestId);
        HarvestResponseVM response = harvestMapper.harvestToHarvestResponseVM(harvest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{harvestId}")
    public ResponseEntity<String> deleteHarvest(@PathVariable UUID harvestId) {
        harvestService.deleteHarvest(harvestId);
        return new ResponseEntity<>("Harvest deleted successfully.", HttpStatus.OK);
    }
}