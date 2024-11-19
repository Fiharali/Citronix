package com.example.citronix.services.impl;

import com.example.citronix.domain.Farm;
import com.example.citronix.exceptions.ResourceNotFoundException;
import com.example.citronix.repositories.FarmRepository;
import com.example.citronix.services.FarmSearchService;
import com.example.citronix.services.FarmService;
import com.example.citronix.services.dto.FarmSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("farmServiceImpl2")
@RequiredArgsConstructor
public class FarmServiceImpl2 implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmSearchService farmSearchService;


    @Override
    public Farm save(Farm farm) {
        Optional<Farm> farmOptional = farmRepository.findByName(farm.getName());
        if (farmOptional.isPresent()) {
            throw new RuntimeException("Farm already exists");
        }

        if (farm.getFields() == null || farm.getFields().isEmpty()) {
            throw new RuntimeException("Farm must have fields");
        }
        return farmRepository.save(farm);
    }


    @Override
    public List<Farm> findAll() {
        return farmRepository.findAll();
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
