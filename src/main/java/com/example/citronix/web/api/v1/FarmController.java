package com.example.citronix.web.api.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.example.citronix.domain.Farm;
import com.example.citronix.services.dto.FarmSearchDTO;
import com.example.citronix.services.FarmService;
import com.example.citronix.web.vm.farm.FarmResponseVM;
import com.example.citronix.web.vm.farm.FarmVM;
import com.example.citronix.web.vm.mapper.FarmMapper;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/farms")
@RequiredArgsConstructor
public class FarmController {

    @Autowired
    @Qualifier("farmServiceImpl")
    private  FarmService farmService;
    private final FarmMapper farmMapper;



    @PostMapping
    public ResponseEntity<FarmResponseVM> save(@RequestBody @Valid FarmVM farmVM) {
        Farm farm = farmMapper.toEntity(farmVM);
        Farm savedFarm = farmService.save(farm);
        FarmResponseVM farmResponseVM = farmMapper.toResponseVM(savedFarm);
        return new ResponseEntity<>(farmResponseVM, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmResponseVM> update(@PathVariable UUID id, @RequestBody @Valid FarmVM farmVM) {
        Farm farm = farmMapper.toEntity(farmVM);
        Farm updatedFarm = farmService.updateFarm(id, farm);
        FarmResponseVM farmResponseVM = farmMapper.toResponseVM(updatedFarm);
        return new ResponseEntity<>(farmResponseVM, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map> delete(@PathVariable UUID id) {
        boolean isDeleted = farmService.deleteFarm(id);
        Map<String, String> response = new HashMap<>();
        if (isDeleted) {
            response.put("message", "Farm deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Farm not deleted with ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmResponseVM> findById(@PathVariable UUID id) {
        Optional<Farm> farm = farmService.getFarmById(id);
        FarmResponseVM farmResponseVM = farmMapper.toResponseVM(farm.get());
        return new ResponseEntity<>(farmResponseVM, HttpStatus.OK);
    }

    @GetMapping

    public ResponseEntity<List<FarmResponseVM>> findAll(Pageable pageable) {
        List<Farm> farms = farmService.findAll();
        List<FarmResponseVM> farmResponseVM = new ArrayList<>();
        farms.forEach(farm -> farmResponseVM.add(farmMapper.toResponseVM(farm)));
        return new ResponseEntity<>(farmResponseVM, HttpStatus.OK);
    }

    @GetMapping("/search")

    public ResponseEntity<List<FarmSearchDTO>> searchFarm(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String creationDate) {

        LocalDate creationDateParsed = null;
        if (StringUtils.hasText(creationDate)) {
            try {
                creationDateParsed = LocalDate.parse(creationDate);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(null);
            }
        }

        FarmSearchDTO searchDTO = new FarmSearchDTO(name, location, creationDateParsed);
        List<FarmSearchDTO> farms = farmService.findByCriteria(searchDTO);
        return ResponseEntity.ok(farms);
    }
}
