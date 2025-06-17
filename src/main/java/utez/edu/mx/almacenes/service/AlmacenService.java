package utez.edu.mx.almacenes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.almacenes.model.Almacen;
import utez.edu.mx.almacenes.model.Cede;
import utez.edu.mx.almacenes.repository.AlmacenRepository;
import utez.edu.mx.almacenes.repository.CedeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AlmacenService {

    @Autowired
    private AlmacenRepository almacenRepository;
    @Autowired
    private CedeRepository cedeRepository;

    public List<Almacen> obtenerTodos() {
        return almacenRepository.findAll();
    }

    public Optional<Almacen> obtenerPorId(Long id) {
        return almacenRepository.findById(id);
    }

    public Almacen crear(Almacen almacen) {
        almacen.setFechaRegistro(LocalDate.now());
        Cede cedeCompleta = cedeRepository.findById(almacen.getCede().getId())
                .orElseThrow(() -> new RuntimeException("Cede no encontrada"));
        almacen.setCede(cedeCompleta);
        Almacen nuevo = almacenRepository.save(almacen);
        nuevo.setClave(generarClave(nuevo));
        return almacenRepository.save(nuevo);
    }

    public Almacen actualizar(Long id, Almacen datos) {
        return almacenRepository.findById(id)
                .map(almacen -> {
                    almacen.setPrecioVenta(datos.getPrecioVenta());
                    almacen.setPrecioRenta(datos.getPrecioRenta());
                    almacen.setTamanio(datos.getTamanio());
                    almacen.setCede(datos.getCede());
                    return almacenRepository.save(almacen);
                })
                .orElseThrow(() -> new RuntimeException("Almacen no encontrado"));
    }

    public void eliminar(Long id) {
        almacenRepository.deleteById(id);
    }

    private String generarClave(Almacen almacen) {
        String claveCede = almacen.getCede().getClave();
        return claveCede + "-A" + almacen.getId();
    }
}