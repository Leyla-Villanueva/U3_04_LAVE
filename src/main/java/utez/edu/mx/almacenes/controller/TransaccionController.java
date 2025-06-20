package utez.edu.mx.almacenes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import utez.edu.mx.almacenes.dto.AlmacenResponseDto;
import utez.edu.mx.almacenes.dto.TransaccionRequestDto;
import utez.edu.mx.almacenes.dto.TransaccionResponseDto;
import utez.edu.mx.almacenes.service.TransaccionService;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
public class TransaccionController {

    private final TransaccionService transaccionService;

    @PostMapping("/procesar")
    public ResponseEntity<TransaccionResponseDto> procesarTransaccion(@Valid @RequestBody TransaccionRequestDto request) {
        TransaccionResponseDto response = transaccionService.procesarTransaccion(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/almacenes/disponibles")
    public ResponseEntity<List<AlmacenResponseDto>> getAlmacenesDisponibles() {
        List<AlmacenResponseDto> almacenes = transaccionService.getAlmacenesDisponibles();
        return ResponseEntity.ok(almacenes);
    }

    @GetMapping("/almacenes/vendidos")
    public ResponseEntity<List<AlmacenResponseDto>> getAlmacenesVendidos() {
        List<AlmacenResponseDto> almacenes = transaccionService.getAlmacenesVendidos();
        return ResponseEntity.ok(almacenes);
    }

    @GetMapping("/almacenes/rentados")
    public ResponseEntity<List<AlmacenResponseDto>> getAlmacenesRentados() {
        List<AlmacenResponseDto> almacenes = transaccionService.getAlmacenesRentados();
        return ResponseEntity.ok(almacenes);
    }

    @GetMapping("/cliente/{clienteId}/almacenes")
    public ResponseEntity<List<AlmacenResponseDto>> getAlmacenesByCliente(@PathVariable @NotNull Long clienteId) {
        List<AlmacenResponseDto> almacenes = transaccionService.getAlmacenesByCliente(clienteId);
        return ResponseEntity.ok(almacenes);
    }

    @PostMapping("/liberar/{almacenId}")
    public ResponseEntity<TransaccionResponseDto> liberarAlmacen(@PathVariable @NotNull Long almacenId) {
        TransaccionResponseDto response = transaccionService.liberarAlmacen(almacenId);
        return ResponseEntity.ok(response);
    }
}
