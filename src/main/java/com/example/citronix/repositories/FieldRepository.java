package com.example.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.citronix.domain.Field;

import java.util.List;
import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {

    List<Field> findByFarmId(UUID farmId);

    long countByFarmId(UUID farmId);

    void deleteByFarmId(UUID id);
}
