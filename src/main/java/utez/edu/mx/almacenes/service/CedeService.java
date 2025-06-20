package utez.edu.mx.almacenes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.almacenes.model.Cede;
import utez.edu.mx.almacenes.repository.CedeRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CedeService {

    @Autowired
    private CedeRepository cedeRepository;

    public List<Cede> obtenerTodas() {
        return cedeRepository.findAll();
    }

    public Optional<Cede> obtenerPorId(Long id) {
        return cedeRepository.findById(id);
    }

    public Cede crear(Cede cede) {
        Cede nueva = cedeRepository.save(cede);
        nueva.setClave(generarClave(nueva.getId()));
        return cedeRepository.save(nueva);
    }

    public Cede actualizar(Long id, Cede datos) {
        return cedeRepository.findById(id)
                .map(cede -> {
                    cede.setEstado(datos.getEstado());
                    cede.setMunicipio(datos.getMunicipio());
                    return cedeRepository.save(cede);
                })
                .orElseThrow(() -> new RuntimeException("Cede no encontrada"));
    }

    public void eliminar(Long id) {
        cedeRepository.deleteById(id);
    }

    private String generarClave(Long id) {
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        int aleatorio = new Random().nextInt(9000) + 1000;
        return "C" + id + "-" + fecha + "-" + aleatorio;
    }
}