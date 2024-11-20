package com.example.citronix.web.vm.farm;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmResponseVM {

    private UUID id;
    private String name;
    private double area;
}
