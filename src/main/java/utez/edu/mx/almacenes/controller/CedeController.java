package utez.edu.mx.almacenes.controller;


import mx.edu.utez.almacenes.dto.CedeRequestDto;
import mx.edu.utez.almacenes.dto.CedeResponseDto;
import mx.edu.utez.almacenes.service.CedeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/cedes")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
public class CedeController {

    private final CedeService cedeService;

    @PostMapping
    public ResponseEntity<CedeResponseDto> createCede(@Valid @RequestBody CedeRequestDto request) {
        CedeResponseDto response = cedeService.createCede(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CedeResponseDto>> getAllCedes() {
        List<CedeResponseDto> cedes = cedeService.getAllCedes();
        return ResponseEntity.ok(cedes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CedeResponseDto> getCedeById(@PathVariable @NotNull Long id) {
        CedeResponseDto cede = cedeService.getCedeById(id);
        return ResponseEntity.ok(cede);
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<CedeResponseDto> getCedeByKey(@PathVariable @NotBlank String key) {
        CedeResponseDto cede = cedeService.getCedeByKey(key);
        return ResponseEntity.ok(cede);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CedeResponseDto> updateCede(
            @PathVariable @NotNull Long id,
            @Valid @RequestBody CedeRequestDto request) {
        CedeResponseDto response = cedeService.updateCede(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCede(@PathVariable @NotNull Long id) {
        cedeService.deleteCede(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/state")
    public ResponseEntity<List<CedeResponseDto>> searchCedesByState(@RequestParam @NotBlank String state) {
        List<CedeResponseDto> cedes = cedeService.searchCedesByState(state);
        return ResponseEntity.ok(cedes);
    }

    @GetMapping("/search/city")
    public ResponseEntity<List<CedeResponseDto>> searchCedesByCity(@RequestParam @NotBlank String city) {
        List<CedeResponseDto> cedes = cedeService.searchCedesByCity(city);
        return ResponseEntity.ok(cedes);
    }
}