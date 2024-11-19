package com.example.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import com.example.citronix.domain.Farm;
import com.example.citronix.services.dto.FarmSearchDTO;
import com.example.citronix.web.vm.farm.FarmResponseVM;
import com.example.citronix.web.vm.farm.FarmVM;
import com.example.citronix.web.vm.farm.FarmSearchVM;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    Farm toEntity(FarmVM farmVM);
    FarmVM toVM(Farm farm);
    FarmResponseVM toResponseVM(Farm farm);
    List<FarmSearchVM> toSearchVM(List<FarmSearchDTO> searchDTO);
}
