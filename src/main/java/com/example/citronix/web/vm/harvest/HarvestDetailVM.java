package com.example.citronix.web.vm.harvest;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class HarvestDetailVM {

    @NotNull(message = "Tree ID is required.")
    private UUID treeId;

    @Positive(message = "Quantity must be positive.")
    private double quantity;
}
