package com.example.citronix.services.impl;

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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestDetailRepository harvestDetailRepository;
    private final FieldRepository fieldRepository;



    @Override
    public Harvest createHarvest(  List<HarvestDetail> harvestDetails, Season season, double totalQuantity) {

        validateHarvestDetails(harvestDetails, season);


        double calculatedTotalQuantity = harvestDetails.stream()
                .mapToDouble(HarvestDetail::getQuantity)
                .sum();


        Harvest harvest = Harvest.builder()
                .season(season)
                .totalQuantity(calculatedTotalQuantity)
                .harvestDate(LocalDate.now())
                .build();


        harvestDetails.forEach(hd -> hd.setHarvest(harvest));


        harvest.setHarvestDetails(harvestDetails);

        harvestRepository.save(harvest);

        return harvest;
    }

    private void validateHarvestDetails(List<HarvestDetail> harvestDetails, Season season) {
        for (HarvestDetail detail : harvestDetails) {

            if (detail.getTree().getPlantingDate().plusYears(20).isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Tree " + detail.getTree().getId() + " is older than 20 years and cannot be harvested.");
            }

            int plantingMonth = detail.getTree().getPlantingDate().getMonthValue();
            if (plantingMonth < 3 || plantingMonth > 5) {
                throw new IllegalArgumentException("Tree " + detail.getTree().getId() + " was not planted during the valid period (March to May).");
            }

            if (harvestDetailRepository.existsByTreeIdAndHarvestSeason(detail.getTree().getId(), season)) {
                throw new IllegalArgumentException("Tree " + detail.getTree().getId() + " is already included in another harvest for the same season.");
            }
        }
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
}
