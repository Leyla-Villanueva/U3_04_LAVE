package utez.edu.mx.almacenes.controller;

import mx.edu.utez.almacenes.dto.AlmacenRequestDto;
import mx.edu.utez.almacenes.dto.AlmacenResponseDto;
import mx.edu.utez.almacenes.models.AlmacenSize;
import mx.edu.utez.almacenes.service.AlmacenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
public class AlmacenController {

    private final AlmacenService warehouseService;

    @PostMapping
    public ResponseEntity<AlmacenResponseDto> createWarehouse(@Valid @RequestBody AlmacenRequestDto request) {
        AlmacenResponseDto response = warehouseService.createWarehouse(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AlmacenResponseDto>> getAllWarehouses() {
        List<AlmacenResponseDto> warehouses = warehouseService.getAllWarehouses();
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlmacenResponseDto> getWarehouseById(@PathVariable @NotNull Long id) {
        AlmacenResponseDto warehouse = warehouseService.getWarehouseById(id);
        return ResponseEntity.ok(warehouse);
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<AlmacenResponseDto> getWarehouseByKey(@PathVariable @NotBlank String key) {
        AlmacenResponseDto warehouse = warehouseService.getWarehouseByKey(key);
        return ResponseEntity.ok(warehouse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlmacenResponseDto> updateWarehouse(
            @PathVariable @NotNull Long id,
            @Valid @RequestBody AlmacenRequestDto request) {
        AlmacenResponseDto response = warehouseService.updateWarehouse(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable @NotNull Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cede/{cedeId}")
    public ResponseEntity<List<AlmacenResponseDto>> getWarehousesByCede(@PathVariable @NotNull Long cedeId) {
        List<AlmacenResponseDto> warehouses = warehouseService.getWarehousesByCede(cedeId);
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/size/{size}")
    public ResponseEntity<List<AlmacenResponseDto>> getWarehousesBySize(@PathVariable @NotNull AlmacenSize size) {
        List<AlmacenResponseDto> warehouses = warehouseService.getWarehousesBySize(size);
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<AlmacenResponseDto>> getWarehousesByPriceRange(
            @RequestParam @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal minPrice,
            @RequestParam @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal maxPrice) {
        List<AlmacenResponseDto> warehouses = warehouseService.getWarehousesBySalePriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(warehouses);
    }
}