package com.example.citronix.web.api.v1;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.citronix.domain.Tree;
import com.example.citronix.services.TreeService;
import com.example.citronix.web.vm.tree.TreeVM;
import com.example.citronix.web.vm.tree.TreeResponseVM;
import com.example.citronix.web.vm.mapper.TreeMapper;

import java.util.*;

@RestController
@RequestMapping("/api/v1/trees")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;
    private final TreeMapper treeMapper;


    @PostMapping
    public ResponseEntity<TreeResponseVM> save(@RequestBody @Valid TreeVM treeVM) {
        Tree tree = treeMapper.treeVMToTree(treeVM);
        Tree savedTree = treeService.createTree(treeVM.getFieldId(), tree);
        TreeResponseVM response = treeMapper.treeToTreeResponseVM(savedTree);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")

    public ResponseEntity<TreeResponseVM> update(@PathVariable UUID id, @RequestBody @Valid TreeVM treeVM) {
        Tree updatedTree = treeMapper.treeVMToTree(treeVM);
        Tree savedTree = treeService.updateTree(id, updatedTree);
        TreeResponseVM response = treeMapper.treeToTreeResponseVM(savedTree);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map> delete(@PathVariable UUID id) {
        treeService.deleteTree(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Tree deleted successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TreeResponseVM> findById(@PathVariable UUID id) {
        Optional<Tree> tree = treeService.getTreeById(id);
        TreeResponseVM response = treeMapper.treeToTreeResponseVM(tree.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TreeResponseVM>> getAll() {
        List<Tree> trees = treeService.getAllTrees();
        List<TreeResponseVM> response = treeMapper.treesToTreeResponseVMs(trees);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
