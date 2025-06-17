package utez.edu.mx.almacenes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.almacenes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
