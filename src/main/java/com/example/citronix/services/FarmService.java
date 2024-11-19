package com.example.citronix.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.citronix.domain.Farm;
import com.example.citronix.services.dto.FarmSearchDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FarmService {
    Farm save(Farm farm);

    List<Farm> findAll();

    Optional<Farm> getFarmById(UUID id);

    Farm updateFarm(UUID id, Farm updatedFarm);

    boolean deleteFarm(UUID id);

    List<FarmSearchDTO> findByCriteria(FarmSearchDTO searchDTO);
}
