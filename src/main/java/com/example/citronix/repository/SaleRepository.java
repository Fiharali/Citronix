package com.example.citronix.repository;

import com.example.citronix.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale , UUID> {
}
