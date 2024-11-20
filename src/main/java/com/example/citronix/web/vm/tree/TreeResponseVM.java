package com.example.citronix.web.vm.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeResponseVM {
    private UUID id;
    private int age;
    private double productivity;
    private UUID fieldId;
}
