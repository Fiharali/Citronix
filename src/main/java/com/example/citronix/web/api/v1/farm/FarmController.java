package com.example.citronix.web.api.v1.farm;


import com.example.citronix.model.Farm;
import com.example.citronix.services.FarmService;
import com.example.citronix.services.dto.SearchDTO;
import com.example.citronix.web.vm.farm.FarmResponseVM;
import com.example.citronix.web.vm.farm.FarmVM;
import com.example.citronix.web.vm.mapper.FarmMapper;
import com.example.citronix.web.vm.search.FarmSearchVM;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/farms")
public class FarmController {


    private final FarmService farmService;
    private final FarmMapper farmMapper;

    public FarmController(FarmService farmService, FarmMapper farmMapper) {
        this.farmService = farmService;
        this.farmMapper = farmMapper;
    }


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
    public ResponseEntity<String> delete(@PathVariable UUID id){
        farmService.delete(id);
        return new ResponseEntity<>("the farm delete successfully" , HttpStatus.OK);
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
    public ResponseEntity<List<SearchDTO>> searchFarm(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String location,
        @RequestParam(required = false) String creationDate){

        LocalDate creationDateParsed = null;
        if (StringUtils.hasText(creationDate)) {
            try {
                creationDateParsed = LocalDate.parse(creationDate);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(null);
            }
        }
        SearchDTO searchDTO = new SearchDTO(name, location, creationDateParsed);
        List<SearchDTO> farms = farmService.findByCriteria(searchDTO);
        return ResponseEntity.ok(farms);
    }
}
