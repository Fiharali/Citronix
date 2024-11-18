// File: org/youcode/citronix/web/api/v1/FieldController.java

package com.example.citronix.web.api.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.citronix.domain.Field;
import com.example.citronix.exceptions.ResourceNotFoundException;
import com.example.citronix.services.interfaces.FieldService;
import com.example.citronix.web.vm.FieldVm.FieldVM;
import com.example.citronix.web.vm.FieldVm.FieldResponseVM;
import com.example.citronix.web.vm.mapper.FieldMapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;
    private final FieldMapper fieldMapper;



    @PostMapping("/save")
    public ResponseEntity<FieldResponseVM> save(@RequestBody @Valid FieldVM fieldVM) {
        Field field = fieldMapper.toEntity(fieldVM);
        Field savedField = fieldService.createField(fieldVM.getFarmId(), field);
        FieldResponseVM response = fieldMapper.toResponse(savedField);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{field_uuid}")
    public ResponseEntity<FieldResponseVM> update(@PathVariable UUID field_uuid, @RequestBody @Valid FieldVM fieldVM) {
        Field updatedField = fieldService.updateField(field_uuid, fieldMapper.toEntity(fieldVM));
        FieldResponseVM response = fieldMapper.toResponse(updatedField);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{field_uuid}")
    public ResponseEntity<String> delete(@PathVariable UUID field_uuid) {
        fieldService.deleteField(field_uuid);
        return new ResponseEntity<>("Field deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/find/{field_uuid}")
    public ResponseEntity<FieldResponseVM> findById(@PathVariable UUID field_uuid) {
        Field field = fieldService.getFieldById(field_uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Field not found with ID: " + field_uuid));
        FieldResponseVM response = fieldMapper.toResponse(field);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<Page<FieldResponseVM>> findAll(Pageable pageable) {
        Page<Field> fields = fieldService.findAll(pageable);
        Page<FieldResponseVM> response = fields.map(fieldMapper::toResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
