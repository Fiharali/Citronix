package com.example.citronix.web.vm.farm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmResponseVM {

    private UUID id;
    private String name;
    private double totalArea;
}
