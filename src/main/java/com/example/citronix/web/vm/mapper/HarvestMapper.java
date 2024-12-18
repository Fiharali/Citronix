package com.example.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.example.citronix.domain.Harvest;
import com.example.citronix.domain.HarvestDetail;
import com.example.citronix.web.vm.harvest.HarvestResponseVM;
import com.example.citronix.web.vm.harvest.HarvestVM;
import com.example.citronix.web.vm.harvest.HarvestDetailResponseVM;
import com.example.citronix.web.vm.harvest.HarvestDetailVM;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HarvestMapper {

    HarvestMapper INSTANCE = Mappers.getMapper(HarvestMapper.class);

    @Mapping(target = "harvestDetails", source = "harvestDetails")
    HarvestResponseVM harvestToHarvestResponseVM(Harvest harvest);

    List<HarvestResponseVM> harvestsToHarvestResponseVMs(List<Harvest> harvests);


    @Mapping(target = "harvestDetails", source = "harvestDetails")
    Harvest harvestVMToHarvest(HarvestVM harvestVM);

    @Mapping(target = "tree.id", source = "treeId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "harvest", ignore = true)
    HarvestDetail harvestDetailVMToHarvestDetail(HarvestDetailVM harvestDetailVM);

    List<HarvestDetail> harvestDetailVMsToHarvestDetails(List<HarvestDetailVM> harvestDetailVMs);

    @Mapping(target = "treeId", source = "tree.id")
    @Mapping(target = "productivity", source = "tree.productivity")
    HarvestDetailResponseVM harvestDetailToHarvestDetailResponseVM(HarvestDetail harvestDetail);

    List<HarvestDetailResponseVM> harvestDetailsToHarvestDetailResponseVMs(List<HarvestDetail> harvestDetails);
}
