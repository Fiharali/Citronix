package com.example.citronix.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.citronix.domain.Farm;
import com.example.citronix.repositories.FarmRepository;
import com.example.citronix.services.dto.FarmSearchDTO;
import com.example.citronix.services.interfaces.FarmSearchService;
import com.example.citronix.services.interfaces.FarmService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmSearchService farmSearchService;

    public FarmServiceImpl(FarmRepository farmRepository, FarmSearchService farmSearchService) {
        this.farmRepository = farmRepository;
        this.farmSearchService = farmSearchService;
    }


    @Override
    public Farm save(Farm farm) {
        Optional<Farm> farmOptional = farmRepository.findByName(farm.getName());
        if (farmOptional.isPresent()) {
            throw new RuntimeException("Farm already exists");
        }
        // Log the farm details before saving
        System.out.println("Saving Farm: " + farm);
        Farm savedFarm = farmRepository.save(farm);
        // Log the saved farm details
        System.out.println("Saved Farm: " + savedFarm);
        return savedFarm;
    }


    @Override
    public Page<Farm> findAll(Pageable pageable) {
        return farmRepository.findAll(pageable);
    }

    @Override
    public Optional<Farm> getFarmById(UUID id) {
        return farmRepository.findById(id);
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
            return false;
        }
    }

    @Override
    public List<FarmSearchDTO> findByCriteria(FarmSearchDTO searchDTO) {
        return farmSearchService.findByCriteria(searchDTO);
    }





}
