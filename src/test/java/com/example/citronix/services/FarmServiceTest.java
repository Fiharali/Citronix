package com.example.citronix.services;

import com.example.citronix.domain.Farm;
import com.example.citronix.exceptions.ResourceDublicatedException;
import com.example.citronix.exceptions.ResourceNotFoundException;
import com.example.citronix.repositories.FarmRepository;
import com.example.citronix.repositories.FarmSearchRepository;
import com.example.citronix.services.dto.FarmSearchDTO;
import com.example.citronix.services.impl.FarmServiceImpl;
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

class FarmServiceImplTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmSearchRepository farmSearchRepository;

    @InjectMocks
    private FarmServiceImpl farmService;

    private Farm farm;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        farm = new Farm();
        farm.setId(UUID.randomUUID());
        farm.setName("Test Farm");
        farm.setLocation("Test Location");
        farm.setArea(5000);
    }

    @Test
    void testSaveFarmSuccess() {
        when(farmRepository.findByName(farm.getName())).thenReturn(Optional.empty());
        when(farmRepository.save(ArgumentMatchers.any(Farm.class))).thenReturn(farm);

        Farm savedFarm = farmService.save(farm);

        assertNotNull(savedFarm);
        assertEquals("Test Farm", savedFarm.getName());
        verify(farmRepository, times(1)).save(farm);
    }

    @Test
    void testSaveFarmDuplicate() {
        when(farmRepository.findByName(farm.getName())).thenReturn(Optional.of(farm));

        assertThrows(ResourceDublicatedException.class, () -> farmService.save(farm));
        verify(farmRepository, never()).save(any(Farm.class));
    }

    @Test
    void testFindAllFarms() {
        Page<Farm> farmPage = new PageImpl<>(List.of(farm));
        when(farmRepository.findAll(any(Pageable.class))).thenReturn(farmPage);

        Page<Farm> result = farmService.findAll(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(farmRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testGetFarmByIdSuccess() {
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));

        Optional<Farm> foundFarm = farmService.getFarmById(farm.getId());

        assertTrue(foundFarm.isPresent());
        assertEquals(farm.getName(), foundFarm.get().getName());
        verify(farmRepository, times(1)).findById(farm.getId());
    }

    @Test
    void testGetFarmByIdNotFound() {
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> farmService.getFarmById(farm.getId()));
    }

    @Test
    void testUpdateFarmSuccess() {
        Farm updatedFarm = new Farm();
        updatedFarm.setName("Updated Farm");
        updatedFarm.setLocation("Updated Location");
        updatedFarm.setArea(6000);

        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));
        when(farmRepository.save(any(Farm.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Farm result = farmService.updateFarm(farm.getId(), updatedFarm);

        assertNotNull(result);
        assertEquals("Updated Farm", result.getName());
        assertEquals("Updated Location", result.getLocation());
        assertEquals(6000, result.getArea());
        verify(farmRepository, times(1)).save(farm);
    }

    @Test
    void testUpdateFarmNotFound() {
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.empty());

        Farm updatedFarm = new Farm();
        updatedFarm.setName("Updated Farm");

        assertThrows(ResourceNotFoundException.class, () -> farmService.updateFarm(farm.getId(), updatedFarm));
    }

    @Test
    void testDeleteFarmSuccess() {
        when(farmRepository.existsById(farm.getId())).thenReturn(true);

        boolean result = farmService.deleteFarm(farm.getId());

        assertTrue(result);
        verify(farmRepository, times(1)).deleteById(farm.getId());
    }

    @Test
    void testDeleteFarmNotFound() {
        when(farmRepository.existsById(farm.getId())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> farmService.deleteFarm(farm.getId()));
    }


}
