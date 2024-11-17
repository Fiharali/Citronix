package com.example.citronix.web.vm.mapper;


import com.example.citronix.model.Farm;
import com.example.citronix.services.dto.SearchDTO;
import com.example.citronix.web.vm.farm.FarmResponseVM;
import com.example.citronix.web.vm.farm.FarmVM;
import com.example.citronix.web.vm.search.FarmSearchVM;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    Farm toEntity(FarmVM farmVM);
    FarmVM toVM(Farm farm);
    FarmResponseVM toResponseVM(Farm farm);
    List<FarmSearchVM> toSearchVM(List<SearchDTO> searchDTO);
}
