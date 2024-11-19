package com.example.citronix.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.example.citronix.domain.Tree;
import com.example.citronix.web.vm.tree.TreeResponseVM;
import com.example.citronix.web.vm.tree.TreeVM;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TreeMapper {

    TreeMapper INSTANCE = Mappers.getMapper(TreeMapper.class);


    @Mapping(target = "fieldId", source = "field.id")
    TreeVM treeToTreeVM(Tree tree);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "field", ignore = true)
    Tree treeVMToTree(TreeVM treeVM);


    @Mapping(target = "age", source = "age")
    @Mapping(target = "productivity", source = "productivity")
    @Mapping(target = "fieldId", source = "field.id")
    TreeResponseVM treeToTreeResponseVM(Tree tree);





    List<TreeResponseVM> treesToTreeResponseVMs(List<Tree> trees);
}
