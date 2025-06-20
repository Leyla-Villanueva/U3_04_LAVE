package utez.edu.mx.almacenes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.almacenes.model.Cede;
import utez.edu.mx.almacenes.service.CedeService;

import java.util.List;

@RestController
@RequestMapping("/api/cedes")
public class CedeController {

    @Autowired
    private CedeService cedeService;

    @GetMapping("/")
    public List<Cede> listar() {
        return cedeService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cede> obtenerPorId(@PathVariable Long id) {
        return cedeService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Cede crear(@RequestBody Cede cede) {
        return cedeService.crear(cede);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cede> actualizar(@PathVariable Long id, @RequestBody Cede datos) {
        try {
            return ResponseEntity.ok(cedeService.actualizar(id, datos));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cedeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}