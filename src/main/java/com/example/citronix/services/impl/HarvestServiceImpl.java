package com.example.citronix.services.impl;

import com.example.citronix.domain.Tree;
import com.example.citronix.exceptions.ResourceNotFoundException;
import com.example.citronix.services.TreeService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.citronix.domain.Field;
import com.example.citronix.domain.Harvest;
import com.example.citronix.domain.HarvestDetail;
import com.example.citronix.domain.enums.Season;
import com.example.citronix.repositories.FieldRepository;
import com.example.citronix.repositories.HarvestDetailRepository;
import com.example.citronix.repositories.HarvestRepository;
import com.example.citronix.services.HarvestService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestDetailRepository harvestDetailRepository;
    private final TreeService treeService;




    @Override
    public Harvest createHarvest( Harvest harvest) {

        double totalQuantityOfTrees = validateHarvestDetailsAndGetTotal(harvest.getHarvestDetails(), harvest.getSeason(), harvest.getHarvestDate());

        harvest.getHarvestDetails().forEach(hd -> hd.setHarvest(harvest));

        harvest.setTotalQuantity(totalQuantityOfTrees);
        return harvestRepository.save(harvest);
    }

    private double validateHarvestDetailsAndGetTotal(List<HarvestDetail> harvestDetails, Season season , LocalDate harvestDate) {
        double totalQuantityOfTrees = 0;
        for (HarvestDetail detail : harvestDetails) {

            Optional<Tree> tree = treeService.getTreeById(detail.getTree().getId());

            if (tree.isEmpty()) {
                throw new ResourceNotFoundException("Tree " + detail.getTree().getId() + " not found.");
            }

            if (tree.get().getPlantingDate().plusYears(20).isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Tree " + detail.getTree().getId() + " is older than 20 years and cannot be harvested.");
            }

            if (harvestDetailRepository.existsByTreeIdAndHarvestSeasonAndHarvestDate(tree.get().getId(), season,harvestDate)) {
                throw new IllegalArgumentException("Tree " + detail.getTree().getId() + " is already included in another harvest for the same season.");
            }

            detail.setQuantity(tree.get().getProductivity());
            totalQuantityOfTrees += tree.get().getProductivity();
        }
        return totalQuantityOfTrees;
    }

    @Override
    public List<Harvest> getAllHarvests() {
        return harvestRepository.findAll();
    }

    @Override
    public Harvest getHarvestById(UUID harvestId) {
        return harvestRepository.findById(harvestId)
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));
    }

    @Override
    public void deleteHarvest(UUID harvestId) {
        if (!harvestRepository.existsById(harvestId)) {
            throw new IllegalArgumentException("Harvest not found");
        }
        harvestRepository.deleteById(harvestId);
    }

    @Override
    public void deleteHarvestDetailsByTreeId(UUID treeId) {
        harvestDetailRepository.deleteHarvestDetailsByTreeId(treeId);
    }
}
