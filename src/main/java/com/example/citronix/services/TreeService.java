package com.example.citronix.services;

import com.example.citronix.domain.Tree;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TreeService {


    Tree createTree(UUID fieldId, Tree tree);


    Tree updateTree(UUID treeId, Tree updatedTree);

    void deleteTree(UUID treeId);


    Optional<Tree> getTreeById(UUID treeId);


    List<Tree> getTreesByField(UUID fieldId);

   List<Tree> getAllTrees();

    void deleteByFieldId(UUID fieldId);
}
