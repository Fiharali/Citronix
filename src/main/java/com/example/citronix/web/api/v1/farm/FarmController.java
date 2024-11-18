package com.example.citronix.web.api.v1.farm;


import com.example.citronix.model.Farm;
import com.example.citronix.services.FarmService;
import com.example.citronix.web.vm.farm.FarmResponseVM;
import com.example.citronix.web.vm.farm.FarmVM;
import com.example.citronix.web.vm.mapper.FarmMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/farms")
@RequiredArgsConstructor
public class FarmController {


    private final FarmService farmService;
    private final FarmMapper farmMapper;


    @PostMapping
    public ResponseEntity<FarmResponseVM> save(@RequestBody @Valid FarmVM farmVM){
        Farm farm = farmMapper.toEntity(farmVM);
        Farm savedFarm = farmService.save(farm);
        FarmResponseVM farmResponseVM = farmMapper.toResponseVM(savedFarm);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmResponseVM> update(@PathVariable UUID id, @RequestBody @Valid FarmVM farmVM){
        Farm farm = farmMapper.toEntity(farmVM);
        Farm updatedFarm = farmService.update(id, farm);
        FarmResponseVM farmResponseVM = farmMapper.toResponseVM(updatedFarm);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map> delete(@PathVariable UUID id){
        farmService.delete(id);
        Map map = new HashMap();
        map.put("message" , "the farm delete successfully");
        return new ResponseEntity<>(map , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmResponseVM> findById(@PathVariable UUID id){
        Farm farm = farmService.findById(id);
        FarmResponseVM farmResponseVM = farmMapper.toResponseVM(farm);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<FarmResponseVM>> findAll(Pageable pageable){
        Page<Farm> farms = farmService.findAll(pageable);
        Page<FarmResponseVM> farmResponseVM = farms.map(farmMapper::toResponseVM);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<FarmResponseVM>> search( @RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String location,
                                                        @RequestParam(required = false) String creationDate , Pageable pageable){
        Farm farm = new Farm();
        farm.setName(name);
        farm.setLocation(location);
        if (creationDate != null) {
            try {
                farm.setCreationDate(LocalDate.parse(creationDate));
            } catch (DateTimeParseException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        Page<Farm> farms = farmService.search(farm,pageable);
        Page<FarmResponseVM> farmResponseVM = farms.map(farmMapper::toResponseVM);
        return new ResponseEntity<>(farmResponseVM , HttpStatus.OK);
    }




}
