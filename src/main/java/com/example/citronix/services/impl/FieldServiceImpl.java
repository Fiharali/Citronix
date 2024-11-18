package com.example.citronix.services.impl;

import com.example.citronix.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.citronix.domain.Field;
import com.example.citronix.domain.Farm;
import com.example.citronix.repositories.FieldRepository;
import com.example.citronix.repositories.FarmRepository;
import com.example.citronix.services.interfaces.FieldService;

import java.util.List;
import java.util.Optional;
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
    public Field createField(UUID farmId, Field field) {

        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with ID: " + farmId));


        long fieldCount = fieldRepository.countByFarmId(farmId);
        if (fieldCount >= 10) {
            throw new IllegalArgumentException("Farm cannot have more than 10 fields.");
        }


        if (field.getArea() > (farm.getArea() * 0.5)) {
            throw new IllegalArgumentException("Field area exceeds 50% of the farm's total area.");
        }

        double totalFieldArea = fieldRepository.findByFarmId(farmId).stream()
                .mapToDouble(Field::getArea)
                .sum();
        if (totalFieldArea + field.getArea() >= farm.getArea()) {
            throw new IllegalArgumentException("Total area of fields must be strictly less than the farm's total area.");
        }

        field.setMaxTrees((int) (field.getArea() / 100.0));

        field.setFarm(farm);

        return fieldRepository.save(field);
    }

    @Override
    public Field updateField(UUID fieldId, Field updatedField) {
        Field existingField = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + fieldId));

        // Update properties
        existingField.setArea(updatedField.getArea());

        // Step 1: Check if the updated field area exceeds 50% of the farm's area
        if (updatedField.getArea() > (existingField.getFarm().getArea() * 0.5)) {
            throw new IllegalArgumentException("Field area exceeds 50% of the farm's total area.");
        }

        // Step 2: Ensure the sum of all field areas is less than the farm's area
        double totalFieldArea = fieldRepository.findByFarmId(existingField.getFarm().getId()).stream()
                .mapToDouble(Field::getArea)
                .sum();
        if (totalFieldArea + updatedField.getArea() >= existingField.getFarm().getArea()) {
            throw new IllegalArgumentException("Total area of fields must be strictly less than the farm's total area.");
        }

        // Recalculate maxTrees
        existingField.setMaxTrees((int) (updatedField.getArea() / 100.0));

        return fieldRepository.save(existingField);
    }


    @Override
    public List<Field> getFieldsByFarm(UUID farmId) {
        return fieldRepository.findByFarmId(farmId);
    }
    @Override
    public void deleteField(UUID fieldId) {
        if (!fieldRepository.existsById(fieldId)) {
            throw new IllegalArgumentException("Field not found with ID: " + fieldId);
        }
        fieldRepository.deleteById(fieldId);
    }

    @Override
    public Page<Field> findAll(Pageable pageable) {
        return fieldRepository.findAll(pageable);
    }
    @Override
    public Optional<Field> getFieldById(UUID fieldId) {
        Optional<Field> field = fieldRepository.findById(fieldId);
        if (field.isEmpty()) {
            throw new ResourceNotFoundException("Field not found with ID: " + fieldId);
        }
        return field;
    }

}
