package com.example.citronix.repository;

import com.example.citronix.model.Field;
import com.example.citronix.model.Harvest;
import com.example.citronix.model.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest , UUID> {
    boolean existsBySeasonAndHarvestDetails_Tree_Field_Id(Season season, UUID fieldId);
}
