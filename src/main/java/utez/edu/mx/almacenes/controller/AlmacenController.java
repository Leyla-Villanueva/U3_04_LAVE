package utez.edu.mx.almacenes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.almacenes.model.Almacen;
import utez.edu.mx.almacenes.service.AlmacenService;

import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
public class AlmacenController {

    @Autowired
    private AlmacenService almacenService;

    @GetMapping("/")
    public List<Almacen> listar() {
        return almacenService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Almacen> obtenerPorId(@PathVariable Long id) {
        return almacenService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Almacen crear(@RequestBody Almacen almacen) {
        return almacenService.crear(almacen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Almacen> actualizar(@PathVariable Long id, @RequestBody Almacen datos) {
        try {
            return ResponseEntity.ok(almacenService.actualizar(id, datos));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        almacenService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}