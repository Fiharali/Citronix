package com.example.citronix.services.impl;

import com.example.citronix.model.Harvest;
import com.example.citronix.model.HarvestDetail;
import com.example.citronix.model.Tree;
import com.example.citronix.model.enums.Season;
import com.example.citronix.repository.HarvestDetailRepository;
import com.example.citronix.repository.HarvestRepository;
import com.example.citronix.repository.TreeRepository;
import com.example.citronix.services.HarvestService;
import com.example.citronix.services.TreeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;


@Service
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestDetailRepository harvestDetailRepository;
    private final TreeRepository treeRepository;

    public HarvestServiceImpl(HarvestRepository harvestRepository, HarvestDetailRepository harvestDetailRepository, TreeRepository treeRepository) {
        this.harvestRepository = harvestRepository;
        this.harvestDetailRepository = harvestDetailRepository;
        this.treeRepository = treeRepository;
    }


    @Override
    public Harvest save(Harvest harvest) {
        validateHarvestPeriod(harvest.getHarvestDate());
        return harvestRepository.save(harvest);
    }

    @Override
    public HarvestDetail saveHarvestDetail(UUID harvestId, UUID treeId, double quantity) {
        Harvest harvest = harvestRepository.findById(harvestId)
            .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));
        Tree tree = treeRepository.findById(treeId)
            .orElseThrow(() -> new IllegalArgumentException("Tree not found"));

        validateUniqueHarvestForSeason(harvest.getSeason(), tree.getField().getId());
        validateTreeNotAlreadyHarvested(tree, harvest.getSeason());
        HarvestDetail detail = new HarvestDetail();
        detail.setHarvest(harvest);
        detail.setTree(tree);
        detail.setQuantity(quantity);

        return harvestDetailRepository.save(detail);
    }

    private void validateUniqueHarvestForSeason(Season season, UUID fieldId) {
        boolean exists = harvestRepository.existsBySeasonAndHarvestDetails_Tree_Field_Id(season, fieldId);
        if (exists) {
            throw new IllegalArgumentException("A harvest already exists for this season and field");
        }
    }

    private void validateTreeNotAlreadyHarvested(Tree tree, Season season) {
        boolean exists = harvestDetailRepository.existsByTreeAndHarvest_Season(tree, season);
        if (exists) {
            throw new IllegalArgumentException("Tree already harvested for this season");
        }
    }

    private void validateHarvestPeriod(LocalDate date) {
        Month month = date.getMonth();
        if (!(month == Month.MARCH || month == Month.APRIL || month == Month.MAY)) {
            throw new IllegalArgumentException("Harvests can only occur in March, April, or May");
        }
    }
}
