package utez.edu.mx.almacenes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utez.edu.mx.almacenes.exception.ResourceNotFoundException;
import utez.edu.mx.almacenes.model.Cede;
import utez.edu.mx.almacenes.repository.CedeRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CedeService {

    private final CedeRepository cedeRepository;
    private final Random random = new Random();

    public Cede createCede(Cede cede) {
        // Guardar para obtener ID
        Cede saved = cedeRepository.save(cede);

        // Generar claveCede: C[id]-[ddMMyyyy]-[4 dígitos aleatorios]
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        int aleatorio = 1000 + random.nextInt(9000);
        String clave = "C" + saved.getId() + "-" + fecha + "-" + aleatorio;

        saved.setClaveCede(clave);

        // Guardar nuevamente con clave generada
        return cedeRepository.save(saved);
    }

    public List<Cede> getAllCedes() {
        return cedeRepository.findAll();
    }

    public Cede getCedeById(Long id) {
        return cedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cede no encontrada con id: " + id));
    }

    public Cede updateCede(Long id, Cede nuevaCede) {
        Cede cede = getCedeById(id);
        cede.setEstado(nuevaCede.getEstado());
        cede.setMunicipio(nuevaCede.getMunicipio());
        // claveCede NO se actualiza para mantener integridad
        return cedeRepository.save(cede);
    }

    public void deleteCede(Long id) {
        cedeRepository.deleteById(id);
    }
}