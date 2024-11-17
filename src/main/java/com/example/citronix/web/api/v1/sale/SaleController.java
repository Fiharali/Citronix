package com.example.citronix.web.api.v1.sale;


import com.example.citronix.model.Sale;
import com.example.citronix.services.SaleService;
import com.example.citronix.services.dto.SaleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<SaleDTO> createSale(@RequestBody Sale sale) {
        SaleDTO createdSale = saleService.save(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSale);
    }
}
