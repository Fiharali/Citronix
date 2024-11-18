package com.example.citronix.web.api.v1;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.citronix.domain.Tree;
import com.example.citronix.services.interfaces.TreeService;
import com.example.citronix.web.vm.TreeVm.TreeVM;
import com.example.citronix.web.vm.TreeVm.TreeResponseVM;
import com.example.citronix.web.vm.mapper.TreeMapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/trees")
public class TreeController {

    private final TreeService treeService;
    private final TreeMapper treeMapper;

    public TreeController(TreeService treeService, TreeMapper treeMapper) {
        this.treeService = treeService;
        this.treeMapper = treeMapper;
    }


    @PostMapping("/save")

    public ResponseEntity<TreeResponseVM> save(@RequestBody @Valid TreeVM treeVM) {
        Tree tree = treeMapper.treeVMToTree(treeVM);
        Tree savedTree = treeService.createTree(treeVM.getFieldId(), tree);
        int age = savedTree.getAge();
        double productivity = savedTree.getProductivity();
        TreeResponseVM response = treeMapper.treeToTreeResponseVM(savedTree);
        response.setAge(age);
        response.setProductivity(productivity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/update/{treeId}")

    public ResponseEntity<TreeResponseVM> update(@PathVariable UUID treeId, @RequestBody @Valid TreeVM treeVM) {
        Tree updatedTree = treeMapper.treeVMToTree(treeVM);
        Tree savedTree = treeService.updateTree(treeId, updatedTree);
        int age = savedTree.getAge();
        double productivity = savedTree.getProductivity();
        TreeResponseVM response = treeMapper.treeToTreeResponseVM(savedTree);
        response.setAge(age);
        response.setProductivity(productivity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{treeId}")

    public ResponseEntity<String> delete(@PathVariable UUID treeId) {
        treeService.deleteTree(treeId);
        return new ResponseEntity<>("Tree deleted successfully.", HttpStatus.OK);
    }


    @GetMapping("/find/{treeId}")

    public ResponseEntity<TreeResponseVM> findById(@PathVariable UUID treeId) {
        Tree tree = treeService.getTreeById(treeId)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found"));
        TreeResponseVM response = treeMapper.treeToTreeResponseVM(tree);
        response.setAge(tree.getAge());
        response.setProductivity(tree.getProductivity());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/field/{fieldId}")

    public ResponseEntity<Iterable<TreeResponseVM>> findByField(@PathVariable UUID fieldId) {
        Iterable<Tree> trees = treeService.getTreesByField(fieldId);
        Iterable<TreeResponseVM> response = treeMapper.treesToTreeResponseVMs(trees);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
