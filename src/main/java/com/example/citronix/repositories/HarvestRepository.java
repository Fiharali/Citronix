package com.example.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.citronix.domain.Harvest;
import com.example.citronix.domain.enums.Season;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {

    Optional<Harvest> findById(UUID harvestId);

    List<Harvest> findByFieldId(UUID fieldId);

    boolean existsByFieldIdAndSeason(UUID fieldId, Season season);
}
