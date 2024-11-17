package com.example.citronix.web.vm.mapper;

import com.example.citronix.model.Tree;
import com.example.citronix.web.vm.tree.TreeResponseVM;
import org.springframework.stereotype.Component;


@Component
public class TreeMapper {

    public TreeResponseVM toResponseVM(Tree tree) {
        return new TreeResponseVM(
            tree.getField().getFarm().getName(),
            tree.getField().getArea(),
            tree.getPlantingDate(),
            tree.getAge(),
            tree.getAnnualProductivity()
        );
    }
}
