package com.example.citronix.repository;

import com.example.citronix.model.HarvestDetail;
import com.example.citronix.model.Tree;
import com.example.citronix.model.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HarvestDetailRepository extends JpaRepository<HarvestDetail , UUID> {
    boolean existsByTreeAndHarvest_Season(Tree tree, Season season);
}
