package com.example.citronix.web.vm.farm;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmVM {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Location is required.")
    private String location;

    @Positive(message = "Total area must be positive.")
    private double totalArea;

    @NotNull(message = "Creation date is required.")
    private LocalDate creationDate;

}
