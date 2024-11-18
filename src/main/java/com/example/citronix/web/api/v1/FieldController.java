
package com.example.citronix.web.api.v1;

import com.example.citronix.services.FieldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.citronix.domain.Field;
import com.example.citronix.web.vm.field.FieldVM;
import com.example.citronix.web.vm.field.FieldResponseVM;
import com.example.citronix.web.vm.mapper.FieldMapper;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;
    private final FieldMapper fieldMapper;



    @PostMapping
    public ResponseEntity<FieldResponseVM> save(@RequestBody @Valid FieldVM fieldVM) {
        Field field = fieldMapper.toEntity(fieldVM);
        Field savedField = fieldService.createField(fieldVM.getFarmId(), field);
        FieldResponseVM response = fieldMapper.toResponse(savedField);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldResponseVM> update(@PathVariable UUID id, @RequestBody @Valid FieldVM fieldVM) {
        Field updatedField = fieldService.updateField(id, fieldMapper.toEntity(fieldVM));
        FieldResponseVM response = fieldMapper.toResponse(updatedField);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        fieldService.deleteField(id);
        return new ResponseEntity<>("Field deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldResponseVM> findById(@PathVariable UUID id) {
        Optional<Field> field = fieldService.getFieldById(id);
        FieldResponseVM response = fieldMapper.toResponse(field.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Page<FieldResponseVM>> findAll(Pageable pageable) {
        Page<Field> fields = fieldService.findAll(pageable);
        Page<FieldResponseVM> response = fields.map(fieldMapper::toResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
