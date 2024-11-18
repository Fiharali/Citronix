package com.example.citronix.services.impl;

import com.example.citronix.model.Farm;
import com.example.citronix.model.Field;
import com.example.citronix.repository.FarmRepository;
import com.example.citronix.repository.FieldRepository;
import com.example.citronix.services.FieldService;
import com.example.citronix.web.errors.exceptions.ResourceNotFoundException;
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
    public Field save(UUID farmId, Field field) {
        Farm farm = farmRepository.findById(farmId).orElseThrow(() ->
            new ResourceNotFoundException("Farm not found"));

        validateFieldConstraints(farm, field);

        field.setFarm(farm);
        return fieldRepository.save(field);
    }

    @Override
    public Field update(UUID fieldId, Field field) {
        Field existingField = fieldRepository.findById(fieldId).orElseThrow(() ->
            new RuntimeException("Field not found"));

        Farm farm = existingField.getFarm();

        // Validation
        validateFieldConstraints(farm, field);

        existingField.setArea(field.getArea());
        return fieldRepository.save(existingField);
    }

    @Override
    public Field findById(UUID fieldId) {
        return fieldRepository.findById(fieldId).orElseThrow(() ->
            new RuntimeException("Field not found"));
    }

    @Override
    public Page<Field> findAllByFarm(UUID farmId, Pageable pageable) {
        return fieldRepository.findAllByFarmId(farmId, pageable);
    }

    @Override
    public void delete(UUID fieldId) {
        Field field = fieldRepository.findById(fieldId).orElseThrow(() ->
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
