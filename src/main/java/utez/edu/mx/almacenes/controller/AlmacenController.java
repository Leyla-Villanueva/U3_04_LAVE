package utez.edu.mx.almacenes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.almacenes.model.Almacen;
import utez.edu.mx.almacenes.service.AlmacenService;

import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
@RequiredArgsConstructor
@Validated
public class AlmacenController {

    private final AlmacenService almacenService;

    @PostMapping
    public Almacen crear(@Valid @RequestBody Almacen almacen) {
        return almacenService.createAlmacen(almacen);
    }

    @GetMapping
    public List<Almacen> listar() {
        return almacenService.getAllAlmacenes();
    }

    @GetMapping("/{id}")
    public Almacen obtenerPorId(@PathVariable Long id) {
        return almacenService.getAlmacenById(id);
    }

    @PutMapping("/{id}")
    public Almacen actualizar(@PathVariable Long id, @Valid @RequestBody Almacen almacen) {
        return almacenService.updateAlmacen(id, almacen);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        almacenService.deleteAlmacen(id);
    }
}