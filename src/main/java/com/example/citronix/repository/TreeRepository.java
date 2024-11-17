package com.example.citronix.repository;

import com.example.citronix.model.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree , UUID> {
    long countByFieldUuid(UUID fieldUuid);
    List<Tree> findAllByFieldUuid(UUID fieldUuid);
}
