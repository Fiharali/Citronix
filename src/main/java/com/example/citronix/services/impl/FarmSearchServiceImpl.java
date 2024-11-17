package com.example.citronix.services.impl;

import com.example.citronix.repository.FarmSearchRepository;
import com.example.citronix.services.FarmSearchService;
import com.example.citronix.services.dto.SearchDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FarmSearchServiceImpl implements FarmSearchService {

    private final FarmSearchRepository farmSearchRepository;

    public FarmSearchServiceImpl(FarmSearchRepository farmSearchRepository) {
        this.farmSearchRepository = farmSearchRepository;
    }

    @Override
    public List<SearchDTO> findByCriteria(SearchDTO searchDTO) {
        return farmSearchRepository.findByCriteria(searchDTO);
    }
}
