package com.example.citronix.services;

import com.example.citronix.services.dto.SearchDTO;

import java.util.List;

public interface FarmSearchService {

    List<SearchDTO> findByCriteria(SearchDTO searchDTO);
}
