package com.example.citronix.services.impl;

import com.example.citronix.exceptions.ResourceNotFoundException;
import com.example.citronix.services.FieldService;
import com.example.citronix.services.HarvestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.example.citronix.domain.Field;
import com.example.citronix.domain.Tree;
import com.example.citronix.repositories.TreeRepository;
import com.example.citronix.services.TreeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final FieldService fieldService;

    @Autowired
    @Lazy
    private  HarvestService harvestService;





    @Override
    public Tree createTree(UUID fieldId, Tree tree) {
        Field field = fieldService.findById(fieldId);

        int month = tree.getPlantingDate().getMonthValue();
        
        if (month < 3 || month > 5) {
            throw new IllegalArgumentException("Tree planting must be between March and May.");
        }
        double totalFieldArea = fieldService.findById(fieldId).getArea();


        long numberOfTrees = treeRepository.countByFieldId(fieldId);
        double treeDensity = numberOfTrees / totalFieldArea;

        if (treeDensity > 100) {
            throw new IllegalArgumentException("Field exceeds tree density limit of 100 trees per hectare.");
        }

        tree.setField(field);
        return treeRepository.save(tree);
    }


    @Override
    public Tree updateTree(UUID treeId, Tree tree) {
        Tree existingTree = treeRepository.findById(treeId)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found"));

        int month = tree.getPlantingDate().getMonthValue();
        if (month < 3 || month > 5) {
            throw new IllegalArgumentException("Tree planting must be between March and May.");
        }
        if (!tree.getPlantingDate().equals(existingTree.getPlantingDate())) {
            existingTree.setPlantingDate(tree.getPlantingDate());
        }

        if (tree.getField() != null && !tree.getField().equals(existingTree.getField())) {
            Field field = fieldService.findById(tree.getField().getId());
            existingTree.setField(field);
        }

        Field field = existingTree.getField();
        double totalFieldArea = field.getArea();
        long numberOfTrees = treeRepository.countByFieldId(field.getId());
        double treeDensity = numberOfTrees / totalFieldArea;

        if (treeDensity > 100) {
            throw new IllegalArgumentException("Field exceeds tree density limit of 100 trees per hectare after update.");
        }
        return treeRepository.save(existingTree);
    }

    @Override
    @Transactional
    public void deleteTree(UUID treeId) {
        if (!treeRepository.existsById(treeId)) {
            throw new ResourceNotFoundException("Tree not found");
        }
        harvestService.deleteHarvestDetailsByTreeId(treeId);
        treeRepository.deleteById(treeId);
    }

    @Override
    public Optional<Tree> getTreeById(UUID treeId) {
        return Optional.ofNullable(treeRepository.findById(treeId).orElseThrow(() ->
                new ResourceNotFoundException("Tree not found")));
    }

    @Override
    public List<Tree> getTreesByField(UUID fieldId) {
        return treeRepository.findByFieldId(fieldId);
    }

    @Override
    public List<Tree> getAllTrees() {
        return treeRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteByFieldId(UUID fieldId) {

        List<Tree> fields = treeRepository.findByFieldId(fieldId);

        fields.stream()
                .map(Tree::getId)
                .forEach(treeRepository::deleteByFieldId);

        fields.stream()
                .map(Tree::getId)
                .forEach(harvestService::deleteHarvestDetailsByTreeId);

    }
}
