package com.example.citronix.web.vm.field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldResponseVM {

    private String farmName;
    private double farmTotalArea;
    private List<FieldVM> fields;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldVM {
        private String uuid;
        private double area;
    }
}
