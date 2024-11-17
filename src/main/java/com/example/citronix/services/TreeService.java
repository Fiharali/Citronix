package com.example.citronix.services;

import com.example.citronix.model.Tree;

import java.util.List;
import java.util.UUID;

public interface TreeService {

    Tree save(UUID fieldUuid, Tree tree);
    List<Tree> findAllTreesByField(UUID fieldUuid);
    void delete(UUID uuid);
}
