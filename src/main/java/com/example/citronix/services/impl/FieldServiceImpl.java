package com.example.citronix.services.impl;

import com.example.citronix.model.Farm;
import com.example.citronix.model.Field;
import com.example.citronix.repository.FarmRepository;
import com.example.citronix.repository.FieldRepository;
import com.example.citronix.services.FarmService;
import com.example.citronix.services.FieldService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;

    public FieldServiceImpl(FieldRepository fieldRepository, FarmRepository farmRepository) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
    }

    @Override
    public Field save(UUID farmUuid, Field field) {
        Farm farm = farmRepository.findById(farmUuid).orElseThrow(() ->
            new RuntimeException("Farm not found"));

        validateFieldConstraints(farm, field);

        field.setFarm(farm);
        return fieldRepository.save(field);
    }

    @Override
    public Field update(UUID fieldUuid, Field field) {
        Field existingField = fieldRepository.findById(fieldUuid).orElseThrow(() ->
            new RuntimeException("Field not found"));

        Farm farm = existingField.getFarm();

        // Validation
        validateFieldConstraints(farm, field);

        existingField.setArea(field.getArea());
        return fieldRepository.save(existingField);
    }

    @Override
    public Field findById(UUID fieldUuid) {
        return fieldRepository.findById(fieldUuid).orElseThrow(() ->
            new RuntimeException("Field not found"));
    }

    @Override
    public Page<Field> findAllByFarm(UUID farmUuid, Pageable pageable) {
        return fieldRepository.findAllByFarmUuid(farmUuid, pageable);
    }

    @Override
    public void delete(UUID fieldUuid) {
        Field field = fieldRepository.findById(fieldUuid).orElseThrow(() ->
            new RuntimeException("Field not found"));
        fieldRepository.delete(field);
    }

    private void validateFieldConstraints(Farm farm, Field newField) {
        double totalFieldArea = farm.getFields().stream()
            .filter(field -> !field.getId().equals(newField.getId()))
            .mapToDouble(Field::getArea)
            .sum() + newField.getArea();

        if (newField.getArea() < 0.1) {
            throw new RuntimeException("Field area must be at least 0.1 hectares (1,000 m²)");
        }

        if (newField.getArea() > (farm.getTotalArea() * 0.5)) {
            throw new RuntimeException("Field area cannot exceed 50% of the farm's total area");
        }

        if (totalFieldArea >= farm.getTotalArea()) {
            throw new RuntimeException("Total field area must be less than the farm's total area");
        }

        if (farm.getFields().size() >= 10) {
            throw new RuntimeException("A farm cannot have more than 10 fields");
        }
    }
}
