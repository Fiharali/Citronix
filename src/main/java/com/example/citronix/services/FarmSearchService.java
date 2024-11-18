package com.example.citronix.services;

import com.example.citronix.services.dto.FarmSearchDTO;

import java.util.List;

public interface FarmSearchService {
    List<FarmSearchDTO> findByCriteria(FarmSearchDTO searchDTO);
}
