package com.example.citronix.repository;

import com.example.citronix.services.dto.SearchDTO;

import java.util.List;

public interface FarmSearchRepository {
    List<SearchDTO> findByCriteria(SearchDTO searchDTO);
}
