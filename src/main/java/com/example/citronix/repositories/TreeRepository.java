package com.example.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.citronix.domain.Tree;

import java.util.List;
import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree, UUID> {

    long countByFieldId(UUID fieldId);


    List<Tree> findByFieldId(UUID fieldId);

}
