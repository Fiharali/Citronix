package com.example.citronix.services.impl;

import com.example.citronix.model.Field;
import com.example.citronix.model.Tree;
import com.example.citronix.repository.FieldRepository;
import com.example.citronix.repository.TreeRepository;
import com.example.citronix.services.TreeService;
import org.springframework.stereotype.Service;

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
    public Tree save(UUID fieldId, Tree tree) {
        Field field = fieldRepository.findById(fieldId)
            .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        if (!tree.isPlantingSeason()) {
            throw new IllegalArgumentException("Trees can only be planted between March and May.");
        }

        double maxTrees = field.getArea() * 100;
        long currentTreeCount = treeRepository.countByFieldId(fieldId);
        if (currentTreeCount >= maxTrees) {
            throw new IllegalArgumentException("Field has reached the maximum tree density.");
        }

        tree.setField(field);

        return treeRepository.save(tree);
    }

    @Override
    public List<Tree> findAllTreesByField(UUID fieldId) {
        return treeRepository.findAllByFieldId(fieldId);
    }

    @Override
    public void delete(UUID id) {
        treeRepository.deleteById(id);
    }
}
