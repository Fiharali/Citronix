package com.example.citronix.web.vm.mapper;

import com.example.citronix.domain.Sale;
import com.example.citronix.web.vm.sale.SaleResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "harvestId", source = "harvest.id")
    SaleResponseVM saleToSaleResponseVM(Sale sale);

    List<SaleResponseVM> salesToSaleResponseVMs(List<Sale> sales);
}