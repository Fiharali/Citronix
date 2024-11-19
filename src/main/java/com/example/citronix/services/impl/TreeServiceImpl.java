package com.example.citronix.services.impl;

import com.example.citronix.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.example.citronix.domain.Field;
import com.example.citronix.domain.Tree;
import com.example.citronix.repositories.FieldRepository;
import com.example.citronix.repositories.TreeRepository;
import com.example.citronix.services.TreeService;

import java.util.List;
import java.util.UUID;

@Service
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final FieldRepository fieldRepository;

    public TreeServiceImpl(TreeRepository treeRepository, FieldRepository fieldRepository) {
        this.treeRepository = treeRepository;
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Tree createTree(UUID fieldId, Tree tree) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        if (!tree.isPlantingSeason()) {
            throw new IllegalArgumentException("Tree planting must be between March and May.");
        }
        double totalFieldArea = fieldRepository.findById(fieldId)
                .map(Field::getArea)
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        long numberOfTrees = treeRepository.countByFieldId(fieldId);
        double treeDensity = numberOfTrees / totalFieldArea;

        if (treeDensity > 100) {
            throw new IllegalArgumentException("Field exceeds tree density limit of 100 trees per hectare.");
        }

        tree.setField(field);
        return treeRepository.save(tree);
    }


    @Override
    public Tree updateTree(UUID treeId, Tree updatedTree) {
        Tree existingTree = treeRepository.findById(treeId)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found"));

        if (!updatedTree.isPlantingSeason()) {
            throw new IllegalArgumentException("Tree planting must be between March and May.");
        }

        if (updatedTree.getPlantingDate() != null && !updatedTree.getPlantingDate().equals(existingTree.getPlantingDate())) {
            existingTree.setPlantingDate(updatedTree.getPlantingDate());
        }

        if (updatedTree.getField() != null && !updatedTree.getField().equals(existingTree.getField())) {
            Field field = fieldRepository.findById(updatedTree.getField().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Field not found"));
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
    public void deleteTree(UUID treeId) {
        if (!treeRepository.existsById(treeId)) {
            throw new ResourceNotFoundException("Tree not found");
        }
        treeRepository.deleteById(treeId);
    }

    @Override
    public Tree getTreeById(UUID treeId) {
        return treeRepository.findById(treeId).orElseThrow(()->
                new ResourceNotFoundException("Tree not found"));
    }

    @Override
    public List<Tree> getTreesByField(UUID fieldId) {
        return treeRepository.findByFieldId(fieldId);
    }

}
