package com.example.citronix.web.vm.FieldVm;

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

    private float area;
    private int maxTrees;
    private UUID farmId;

}
