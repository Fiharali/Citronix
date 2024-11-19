package com.example.citronix.services.impl;

import com.example.citronix.domain.Field;
import com.example.citronix.exceptions.ResourceNotFoundException;
import com.example.citronix.services.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.citronix.domain.Farm;
import com.example.citronix.repositories.FarmRepository;
import com.example.citronix.services.dto.FarmSearchDTO;
import com.example.citronix.services.FarmSearchService;
import com.example.citronix.services.FarmService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("farmServiceImpl")
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmSearchService farmSearchService;
    private final FieldService fieldService;




    @Override
    public Farm save(Farm farm) {
        Optional<Farm> farmOptional = farmRepository.findByName(farm.getName());
        if (farmOptional.isPresent()) {
            throw new RuntimeException("Farm already exists");
        }


        if (farm.getFields() != null && !farm.getFields().isEmpty()) {
            throw new RuntimeException("Farm cannot have fields.");
        }


        return farmRepository.save(farm);
    }


    @Override
    public List<Farm> findAll() {
        List<Farm> farms = farmRepository.findAll();
        List<Farm> farmsWith4000 = new ArrayList<>();


        farms.forEach(farm -> {
            List <Field> fields = fieldService.getFieldsByFarm(farm.getId());
            double totalFieldArea = fields.stream()
                    .mapToDouble(Field::getArea)
                    .sum();
            if(totalFieldArea > 4000){
                farmsWith4000.add(farm);
            }
        });

        return farmsWith4000;

    }

    @Override
    public Optional<Farm> getFarmById(UUID id) {

        Optional<Farm> farm =  farmRepository.findById(id);
        if (farm.isEmpty()) {
            throw new ResourceNotFoundException("Farm not found with id: " + id);
        }
        return farm;
    }



    @Override
    public Farm updateFarm(UUID id, Farm updatedFarm) {
        Farm existingFarm = farmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with id: " + id));

        existingFarm.setName(updatedFarm.getName());
        existingFarm.setLocation(updatedFarm.getLocation());
        existingFarm.setArea(updatedFarm.getArea());
        existingFarm.setCreationDate(updatedFarm.getCreationDate());
        return farmRepository.save(existingFarm);
    }

    @Override
    public boolean deleteFarm(UUID id) {
        if (farmRepository.existsById(id)) {
            fieldService.deleteFieldsByFarm(id);
            farmRepository.deleteById(id);
            return true;
        } else {
            throw  new ResourceNotFoundException("Farm not found with id: " + id);
        }
    }

    @Override
    public List<FarmSearchDTO> findByCriteria(FarmSearchDTO searchDTO) {
        return farmSearchService.findByCriteria(searchDTO);
    }





}
