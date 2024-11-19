package com.example.citronix.services;

import com.example.citronix.domain.Tree;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TreeService {

    // Method to create a new tree and associate it with a field
    Tree createTree(UUID fieldId, Tree tree);

    // Method to update an existing tree
    Tree updateTree(UUID treeId, Tree updatedTree);

    void deleteTree(UUID treeId);

    // Method to retrieve a tree by its ID
    Optional<Tree> getTreeById(UUID treeId);

    // Method to retrieve all trees associated with a specific field
    List<Tree> getTreesByField(UUID fieldId);
}
