package com.example.citronix.web.api.v1;


import com.example.citronix.domain.Sale;
import com.example.citronix.services.SaleService;
import com.example.citronix.web.vm.mapper.SaleMapper;
import com.example.citronix.web.vm.sale.SaleResponseVM;
import com.example.citronix.web.vm.sale.SaleVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;
    private final SaleMapper saleMapper;



    @PostMapping
    public ResponseEntity<SaleResponseVM> createSale(@RequestBody @Valid SaleVM saleVM) {
        Sale saleEntity = saleMapper.saleVMToSale(saleVM);
        Sale sale = saleService.createSale(saleEntity, saleVM.getClientId(), saleVM.getHarvestId());
        SaleResponseVM response = saleMapper.saleToSaleResponseVM(sale);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SaleResponseVM>> getAllSales() {
        List<Sale> sales = saleService.getAllSales();
        List<SaleResponseVM> responses = saleMapper.salesToSaleResponseVMs(sales);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map> deleteClient(@PathVariable UUID id) {
        saleService.delete(id);
        Map response = new HashMap<>();
        response.put("message", "sale deleted successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}