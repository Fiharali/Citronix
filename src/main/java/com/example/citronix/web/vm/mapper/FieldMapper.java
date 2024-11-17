package com.example.citronix.web.vm.mapper;


import com.example.citronix.model.Field;
import com.example.citronix.web.vm.field.FieldResponseVM;
import com.example.citronix.web.vm.field.FieldVM;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Component
public class FieldMapper {

    public Field toEntity(FieldVM fieldVM) {
        Field field = new Field();
        field.setArea(fieldVM.getArea());
        return field;
    }

    public FieldResponseVM toResponseVM(Field field) {
        if (field == null || field.getFarm() == null) {
            return null;
        }

        FieldResponseVM.FieldVM fieldVM = new FieldResponseVM.FieldVM(
            field.getId().toString(),
            field.getArea()
        );

        return new FieldResponseVM(
            field.getFarm().getName(),
            field.getFarm().getTotalArea(),
            List.of(fieldVM)
        );
    }

    public FieldResponseVM toResponseVMForAll(List<Field> fields) {
        if (fields.isEmpty()) {
            return null;
        }

        var farm = fields.get(0).getFarm();
        List<FieldResponseVM.FieldVM> fieldVMs = fields.stream()
            .map(f -> new FieldResponseVM.FieldVM(f.getId().toString(), f.getArea()))
            .collect(Collectors.toList());

        return new FieldResponseVM(
            farm.getName(),
            farm.getTotalArea(),
            fieldVMs
        );
    }
}
