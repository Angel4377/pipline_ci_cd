package com.example.priceservice.controller;

import com.example.priceservice.dto.PriceDTO;
import com.example.priceservice.service.impl.PriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping
    public ResponseEntity<List<PriceDTO>> getPrices() {
        return ResponseEntity.ok(priceService.getPrices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceDTO> getPrice(@PathVariable Long id) {
        return ResponseEntity.ok(priceService.getPrice(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PriceDTO> createPrice(@Valid @RequestBody PriceDTO priceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(priceService.createPrice(priceDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceDTO> updatePrice(@PathVariable Long id, @RequestBody PriceDTO priceDTO) {
        return ResponseEntity.ok(priceService.updatePrice(id, priceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        priceService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }
}



