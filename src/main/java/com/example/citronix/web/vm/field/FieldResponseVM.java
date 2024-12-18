package com.example.citronix.web.vm.field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldResponseVM {

    private UUID id;
    private float area;
    private int maxTrees;
    private UUID farmId;

}
