package com.example.citronix.services;

import com.example.citronix.model.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FieldService {

    Field save(UUID farmUuid, Field field);
    Field update(UUID fieldUuid, Field field);
    Field findById(UUID fieldUuid);
    Page<Field> findAllByFarm(UUID farmUuid, Pageable pageable);
    void delete(UUID fieldUuid);
}
