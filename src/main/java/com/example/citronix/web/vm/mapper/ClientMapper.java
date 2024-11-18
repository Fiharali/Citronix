package com.example.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.citronix.domain.Client;
import com.example.citronix.web.vm.ClientVm.ClientResponseVM;
import com.example.citronix.web.vm.ClientVm.ClientVM;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientResponseVM toClientResponseVM(Client client);

    @Mapping(target = "id", ignore = true)
    Client clientVMToClient(ClientVM clientVM);

    List<ClientResponseVM> toClientResponseVMs(List<Client> clients);

}
