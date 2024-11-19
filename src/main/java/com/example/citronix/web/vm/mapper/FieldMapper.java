package com.example.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.example.citronix.domain.Field;
import com.example.citronix.web.vm.field.FieldResponseVM;
import com.example.citronix.web.vm.field.FieldVM;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface FieldMapper {

    FieldMapper INSTANCE = Mappers.getMapper(FieldMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "farm", ignore = true)
    @Mapping(target = "maxTrees", ignore = true)
    Field toEntity(FieldVM fieldVM);

    @Mapping(source = "farm.id", target = "farmId") // Removed qualifiedByName
    FieldResponseVM toResponse(Field field);

    // Custom method directly used in the mapper
    default UUID mapFarmId(Field field) {
        return field.getFarm() != null ? field.getFarm().getId() : null;
    }
}
