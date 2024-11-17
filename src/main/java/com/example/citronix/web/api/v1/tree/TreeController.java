package com.example.citronix.web.api.v1.tree;


import com.example.citronix.model.Tree;
import com.example.citronix.services.TreeService;
import com.example.citronix.web.vm.mapper.TreeMapper;
import com.example.citronix.web.vm.tree.TreeResponseVM;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/trees")
public class TreeController {

    private final TreeService treeService;
    private final TreeMapper treeMapper;

    public TreeController(TreeService treeService, TreeMapper treeMapper) {
        this.treeService = treeService;
        this.treeMapper = treeMapper;
    }


    @PostMapping("/{fieldUuid}/save")
    public ResponseEntity<TreeResponseVM> saveTree(@PathVariable UUID fieldUuid, @RequestBody @Valid Tree tree) {
        Tree savedTree = treeService.save(fieldUuid, tree);
        TreeResponseVM response = treeMapper.toResponseVM(savedTree);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{fieldUuid}/all")
    public ResponseEntity<List<TreeResponseVM>> getAllTreesByField(@PathVariable UUID fieldUuid) {
        List<Tree> trees = treeService.findAllTreesByField(fieldUuid);
        List<TreeResponseVM> response = trees.stream()
            .map(treeMapper::toResponseVM)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<String> deleteTree(@PathVariable UUID uuid) {
        treeService.delete(uuid);
        return ResponseEntity.ok("Tree deleted successfully.");
    }
}
