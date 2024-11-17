package com.example.citronix.web.api.v1.field;


import com.example.citronix.model.Field;
import com.example.citronix.services.FieldService;
import com.example.citronix.web.vm.field.FieldResponseVM;
import com.example.citronix.web.vm.field.FieldVM;
import com.example.citronix.web.vm.mapper.FieldMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fields")
public class FieldController {

    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    public FieldController(FieldService fieldService, FieldMapper fieldMapper) {
        this.fieldService = fieldService;
        this.fieldMapper = fieldMapper;
    }

    @PostMapping("{farmUuid}/save")
    public ResponseEntity<FieldResponseVM> save(@PathVariable UUID farmUuid, @RequestBody @Valid FieldVM fieldVM) {
        Field field = fieldMapper.toEntity(fieldVM);
        Field savedField = fieldService.save(farmUuid, field);
        FieldResponseVM response = fieldMapper.toResponseVM(savedField);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{fieldUuid}")
    public ResponseEntity<FieldResponseVM> update(@PathVariable UUID fieldUuid,
                                        @RequestBody @Valid FieldVM fieldVM) {
        Field field = fieldMapper.toEntity(fieldVM);
        field.setId(fieldUuid);
        Field updatedField = fieldService.update(fieldUuid, field);
        FieldResponseVM fieldResponseVM = fieldMapper.toResponseVM(updatedField);
        return new ResponseEntity<>(fieldResponseVM, HttpStatus.OK);
    }

    @GetMapping("/{fieldUuid}")
    public ResponseEntity<FieldResponseVM> findById(@PathVariable UUID fieldUuid) {
        Field field = fieldService.findById(fieldUuid);
        FieldResponseVM fieldResponseVM = fieldMapper.toResponseVM(field);
        return new ResponseEntity<>(fieldResponseVM, HttpStatus.OK);
    }

    @GetMapping("/all/{farmUuid}")
    public ResponseEntity<Page<FieldResponseVM>> findAllByFarm(@PathVariable UUID farmUuid, Pageable pageable) {
        Page<Field> fields = fieldService.findAllByFarm(farmUuid, pageable);
        Page<FieldResponseVM> fieldResponseVMS = fields.map(fieldMapper::toResponseVM);
        return new ResponseEntity<>(fieldResponseVMS, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{fieldUuid}")
    public ResponseEntity<String> delete(@PathVariable UUID fieldUuid) {
        fieldService.delete(fieldUuid);
        return new ResponseEntity<>("Field deleted successfully", HttpStatus.OK);
    }
}
