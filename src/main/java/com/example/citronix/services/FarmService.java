package com.example.citronix.services;

import com.example.citronix.model.Farm;
import com.example.citronix.services.dto.SearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface FarmService {


    Farm save(Farm farm);
    Farm update(UUID id, Farm farm);
    Farm findById(UUID id);
    Page<Farm> findAll(Pageable pageable);
    void delete(UUID id);

}
