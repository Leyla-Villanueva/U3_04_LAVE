package utez.edu.mx.almacenes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.almacenes.model.Cede;
import utez.edu.mx.almacenes.service.CedeService;

import java.util.List;

@RestController
@RequestMapping("/api/cedes")
@RequiredArgsConstructor
@Validated
public class CedeController {

    private final CedeService cedeService;

    @PostMapping
    public Cede crear(@Valid @RequestBody Cede cede) {
        return cedeService.createCede(cede);
    }

    @GetMapping
    public List<Cede> listar() {
        return cedeService.getAllCedes();
    }

    @GetMapping("/{id}")
    public Cede obtenerPorId(@PathVariable Long id) {
        return cedeService.getCedeById(id);
    }

    @PutMapping("/{id}")
    public Cede actualizar(@PathVariable Long id, @Valid @RequestBody Cede cede) {
        return cedeService.updateCede(id, cede);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        cedeService.deleteCede(id);
    }
}
