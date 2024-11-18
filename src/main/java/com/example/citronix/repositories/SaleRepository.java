package com.example.citronix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.citronix.domain.Sale;

import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
}
