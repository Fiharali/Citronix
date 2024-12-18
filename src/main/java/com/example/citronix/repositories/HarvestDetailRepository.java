package com.example.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.citronix.domain.HarvestDetail;
import com.example.citronix.domain.enums.Season;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, UUID> {

    List<HarvestDetail> findByHarvestId(UUID harvestId);
    HarvestDetail findByTreeIdAndHarvestId(UUID treeId, UUID harvestId);
    @Query("SELECT COUNT(hd) > 0 FROM HarvestDetail hd WHERE hd.tree.id = :treeId AND hd.harvest.season = :season")
    boolean existsByTreeIdAndHarvestSeason(@Param("treeId") UUID treeId, @Param("season") Season season);

    @Modifying
    @Query("DELETE FROM HarvestDetail hd WHERE hd.tree.id = :treeId")
    void deleteHarvestDetailsByTreeId(@Param("treeId") UUID treeId);

    @Query("SELECT COUNT(hd) > 0 FROM HarvestDetail hd WHERE hd.tree.id = :treeId AND hd.harvest.season = :season AND hd.harvest.harvestDate = :harvestDate")
    boolean existsByTreeIdAndHarvestSeasonAndHarvestDate(@Param("treeId") UUID treeId, @Param("season") Season season, @Param("harvestDate") LocalDate harvestDate);
}
