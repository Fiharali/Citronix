package com.example.citronix.services.impl;

import com.example.citronix.exceptions.FarmFullException;
import com.example.citronix.exceptions.ResourceNotFoundException;
import com.example.citronix.services.FarmService;
import com.example.citronix.services.TreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.citronix.domain.Field;
import com.example.citronix.domain.Farm;
import com.example.citronix.repositories.FieldRepository;
import com.example.citronix.repositories.FarmRepository;
import com.example.citronix.services.FieldService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;

    @Autowired
    @Lazy
    private  TreeService treeService;

    @Autowired
    @Lazy
    private  FarmService farmService;




    @Override
    public Field createField(UUID farmId, Field field) {

        Farm farm = farmService.getFarmById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found with ID: " + farmId));

        long fieldCount = fieldRepository.countByFarmId(farmId);
        if (fieldCount >= 10) {
            throw new FarmFullException("Farm cannot have more than 10 fields.");
        }

        if (field.getArea() > (farm.getArea() * 0.5)) {
            throw new FarmFullException("Field area exceeds 50% of the farm's total area.");
        }

        double totalFieldArea = fieldRepository.findByFarmId(farmId).stream()
                .mapToDouble(Field::getArea)
                .sum();

        if (totalFieldArea + field.getArea() >= farm.getArea()) {
            throw new FarmFullException("Total area of fields must be strictly less than the farm's total area.");
        }

        int maxTrees = (int) Math.floor(field.getArea() / 1000);

        field.setFarm(farm);
        field.setMaxTrees(maxTrees);
        return fieldRepository.save(field);
    }

    @Override
    public Field updateField(UUID fieldId, Field updatedField) {
        Field existingField = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + fieldId));


        existingField.setArea(updatedField.getArea());
        if (updatedField.getArea() > (existingField.getFarm().getArea() * 0.5)) {
            throw new IllegalArgumentException("Field area exceeds 50% of the farm's total area.");
        }
        double totalFieldArea = fieldRepository.findByFarmId(existingField.getFarm().getId()).stream()
                .mapToDouble(Field::getArea)
                .sum();
        if (totalFieldArea + updatedField.getArea() >= existingField.getFarm().getArea()) {
            throw new IllegalArgumentException("Total area of fields must be strictly less than the farm's total area.");
        }

        existingField.setMaxTrees((int) (updatedField.getArea() / 100.0));

        return fieldRepository.save(existingField);
    }


    @Override
    public List<Field> getFieldsByFarm(UUID farmId) {
        return fieldRepository.findByFarmId(farmId);
    }

    @Override
    @Transactional
    public void deleteField(UUID fieldId) {
        if (!fieldRepository.existsById(fieldId)) {
            throw new IllegalArgumentException("Field not found with ID: " + fieldId);
        }
        treeService.deleteByFieldId(fieldId);
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

    @Override
    @Transactional
    public void deleteFieldsByFarm(UUID farmId) {

        List<Field> fields = fieldRepository.findByFarmId(farmId);

        fields.stream()
                .map(Field::getId)
                .forEach(treeService::deleteByFieldId);

        fieldRepository.deleteByFarmId(farmId);
    }

    @Override
    public Field findById(UUID fieldId) {
        return fieldRepository.findById(fieldId)
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with ID: " + fieldId));

    }

}
