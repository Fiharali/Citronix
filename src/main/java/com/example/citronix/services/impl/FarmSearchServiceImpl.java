package com.example.citronix.services.impl;

import org.springframework.stereotype.Service;
import com.example.citronix.repositories.FarmSearchRepository;
import com.example.citronix.services.dto.FarmSearchDTO;
import com.example.citronix.services.FarmSearchService;

import java.util.List;

@Service
public class FarmSearchServiceImpl implements FarmSearchService {

    private final FarmSearchRepository farmSearchRepository;

    public FarmSearchServiceImpl(FarmSearchRepository farmSearchRepository) {
        this.farmSearchRepository = farmSearchRepository;
    }

    @Override
    public List<FarmSearchDTO> findByCriteria(FarmSearchDTO searchDTO) {
        return farmSearchRepository.findByCriteria(searchDTO);
    }
}
