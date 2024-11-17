package com.example.citronix.web.vm.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeResponseVM {
    private String fieldName;
    private double fieldArea;
    private LocalDate plantingDate;
    private int age;
    private double annualProductivity;
}
