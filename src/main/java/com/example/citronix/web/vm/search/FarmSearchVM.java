package com.example.citronix.web.vm.search;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmSearchVM {
    private String name;
    private String location;
    private String creationDate;
}
