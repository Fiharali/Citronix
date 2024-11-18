package com.example.citronix.repositories;


import com.example.citronix.services.dto.FarmSearchDTO;

import java.util.List;

public interface FarmSearchRepository {

    List<FarmSearchDTO> findByCriteria(FarmSearchDTO searchDTO);

}