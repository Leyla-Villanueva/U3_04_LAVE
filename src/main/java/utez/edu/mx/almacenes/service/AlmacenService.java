package utez.edu.mx.almacenes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utez.edu.mx.almacenes.exception.ResourceNotFoundException;
import utez.edu.mx.almacenes.model.Almacen;
import utez.edu.mx.almacenes.repository.AlmacenRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlmacenService {

    private final AlmacenRepository almacenRepository;

    public Almacen createAlmacen(Almacen almacen) {

        almacen.setFechaRegistro(LocalDate.now());

        Almacen saved = almacenRepository.save(almacen);

        String claveAlmacen = saved.getCede().getClaveCede() + "-A" + saved.getId();
        saved.setClaveAlmacen(claveAlmacen);

        return almacenRepository.save(saved);
    }

    public List<Almacen> getAllAlmacenes() {
        return almacenRepository.findAll();
    }

    public Almacen getAlmacenById(Long id) {
        return almacenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Almacen no encontrado con id: " + id));
    }

    public Almacen updateAlmacen(Long id, Almacen nuevoAlmacen) {
        Almacen almacen = getAlmacenById(id);
        almacen.setPrecioVenta(nuevoAlmacen.getPrecioVenta());
        almacen.setPrecioRenta(nuevoAlmacen.getPrecioRenta());
        almacen.setTamaño(nuevoAlmacen.getTamaño());
        almacen.setCede(nuevoAlmacen.getCede());

        String nuevaClave = almacen.getCede().getClaveCede() + "-A" + almacen.getId();
        almacen.setClaveAlmacen(nuevaClave);
        return almacenRepository.save(almacen);
    }

    public void deleteAlmacen(Long id) {
        almacenRepository.deleteById(id);
    }
}
