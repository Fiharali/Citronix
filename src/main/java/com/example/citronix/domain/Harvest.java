package com.example.citronix.domain;


import jakarta.persistence.*;
import lombok.*;
import com.example.citronix.domain.enums.Season;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDate harvestDate;
    @Enumerated(EnumType.STRING)
    private Season season;
    private double totalQuantity;
    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL)
    private List<HarvestDetail> harvestDetails;

}
