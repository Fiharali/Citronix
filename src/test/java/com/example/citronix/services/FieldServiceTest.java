package com.example.citronix.services;

import com.example.citronix.domain.Field;
import com.example.citronix.domain.Farm;
import com.example.citronix.exceptions.FarmFullException;
import com.example.citronix.exceptions.ResourceNotFoundException;
import com.example.citronix.repositories.FieldRepository;
import com.example.citronix.services.impl.FieldServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FieldServiceTest {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmService farmService;

    @InjectMocks
    private FieldServiceImpl fieldService;

    private Farm farm;
    private Field field;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        farm = new Farm();
        farm.setId(UUID.randomUUID());
        farm.setArea(5000);

        field = new Field();
        field.setArea(1000);
    }

    @Test
    public void testCreateFieldSuccess() {
        when(farmService.getFarmById(farm.getId())).thenReturn(Optional.of(farm));
        when(fieldRepository.countByFarmId(farm.getId())).thenReturn(0L);
        when(fieldRepository.save(ArgumentMatchers.any(Field.class))).thenReturn(field);

        Field createdField = fieldService.createField(farm.getId(), field);

        assertNotNull(createdField);
        verify(fieldRepository, times(1)).save(ArgumentMatchers.any(Field.class));
    }

    @Test
    public void testCreateFieldFarmNotFound() {
        when(farmService.getFarmById(farm.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> fieldService.createField(farm.getId(), field));
    }

    @Test
    public void testCreateFieldFarmFull() {
        when(farmService.getFarmById(farm.getId())).thenReturn(Optional.of(farm));
        when(fieldRepository.countByFarmId(farm.getId())).thenReturn(10L);

        assertThrows(FarmFullException.class, () -> fieldService.createField(farm.getId(), field));
    }

    @Test
    public void testCreateFieldAreaExceeds50Percent() {
        when(farmService.getFarmById(farm.getId())).thenReturn(Optional.of(farm));
        when(fieldRepository.countByFarmId(farm.getId())).thenReturn(0L);
        when(fieldRepository.findByFarmId(farm.getId())).thenReturn(List.of());

        field.setArea(3000);

        assertThrows(FarmFullException.class, () -> fieldService.createField(farm.getId(), field));
    }

    @Test
    public void testCreateFieldTotalAreaExceedsFarmArea() {
        when(farmService.getFarmById(farm.getId())).thenReturn(Optional.of(farm));
        when(fieldRepository.countByFarmId(farm.getId())).thenReturn(0L);
        when(fieldRepository.findByFarmId(farm.getId())).thenReturn(List.of(new Field()));

        Field existingField = new Field();
        existingField.setArea(4500);
        when(fieldRepository.findByFarmId(farm.getId())).thenReturn(List.of(existingField));

        field.setArea(1000);

        assertThrows(FarmFullException.class, () -> fieldService.createField(farm.getId(), field));
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testUpdateFieldSuccess() {
        Farm farm = new Farm();
        farm.setId(UUID.randomUUID());
        farm.setArea(5000);


        Field field = new Field();
        field.setId(UUID.randomUUID());
        field.setArea(1000);
        field.setFarm(farm);


        when(fieldRepository.findById(field.getId())).thenReturn(Optional.of(field));
        when(fieldRepository.save(ArgumentMatchers.any(Field.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Field updatedField = new Field();
        updatedField.setArea(1500);


        Field updated = fieldService.updateField(field.getId(), updatedField);

        assertNotNull(updated);
        assertEquals(1500, updated.getArea());
        verify(fieldRepository, times(1)).save(ArgumentMatchers.any(Field.class));
    }


    @Test
    public void testUpdateFieldAreaExceedsFarmLimit() {

        Farm farm = new Farm();
        farm.setId(UUID.randomUUID());
        farm.setArea(5000);


        Field field = new Field();
        field.setId(UUID.randomUUID());
        field.setArea(2000);
        field.setFarm(farm);


        when(fieldRepository.findById(field.getId())).thenReturn(Optional.of(field));


        Field updatedField = new Field();
        updatedField.setArea(3000);

        assertThrows(IllegalArgumentException.class, () -> fieldService.updateField(field.getId(), updatedField));
    }


    @Test
    public void testGetFieldsByFarm() {
        when(fieldRepository.findByFarmId(farm.getId())).thenReturn(List.of(field));

        List<Field> fields = fieldService.getFieldsByFarm(farm.getId());

        assertEquals(1, fields.size());
        assertEquals(field, fields.get(0));
    }

    @Test
    public void testDeleteFieldSuccess() {
        when(fieldRepository.existsById(field.getId())).thenReturn(true);

        fieldService.deleteField(field.getId());

        verify(fieldRepository, times(1)).deleteById(field.getId());
    }

    @Test
    public void testDeleteFieldNotFound() {
        when(fieldRepository.existsById(field.getId())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> fieldService.deleteField(field.getId()));
    }


    @Test
    public void testGetFieldByIdNotFound() {
        when(fieldRepository.findById(field.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> fieldService.getFieldById(field.getId()));
    }

    @Test
    public void testDeleteFieldsByFarm() {
        fieldService.deleteFieldsByFarm(farm.getId());
        verify(fieldRepository, times(1)).deleteByFarmId(farm.getId());
    }
}
