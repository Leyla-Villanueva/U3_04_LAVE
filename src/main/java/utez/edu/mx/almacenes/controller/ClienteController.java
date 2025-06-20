package utez.edu.mx.almacenes.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.almacenes.model.Cliente;
import utez.edu.mx.almacenes.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClienteController {

    private final ClienteService clientService;

    @PostMapping
    public ResponseEntity<ClienteResponseDto> createClient(@Valid @RequestBody ClienteRequestDto request) {
        ClienteResponseDto response = clientService.createClient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> getAllClients() {
        List<ClienteResponseDto> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getClientById(@PathVariable @NotNull Long id) {
        ClienteResponseDto client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/{id}/almacenes")
    public ResponseEntity<List<AlmacenResponseDto>> getAlmacenesByCliente(@PathVariable @NotNull Long id) {
        List<AlmacenResponseDto> almacenes = clientService.getAlmacenesByCliente(id);
        return ResponseEntity.ok(almacenes);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteResponseDto> getClientByEmail(@PathVariable @Email @NotBlank String email) {
        ClienteResponseDto client = clientService.getClientByEmail(email);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> updateClient(
            @PathVariable @NotNull Long id,
            @Valid @RequestBody ClienteRequestDto request) {
        ClienteResponseDto response = clientService.updateClient(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable @NotNull Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<ClienteResponseDto>> searchClientsByName(@RequestParam @NotBlank String name) {
        List<ClienteResponseDto> clients = clientService.searchClientsByName(name);
        return ResponseEntity.ok(clients);
    }
}
