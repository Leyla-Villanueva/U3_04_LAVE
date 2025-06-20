package utez.edu.mx.almacenes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utez.edu.mx.almacenes.model.Cliente;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByNumeroTel(String phoneNumber);

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombreCompleto) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Cliente> findByNombreCompletoContainingIgnoreCase(@Param("name") String name);

    boolean existsByEmail(String email);

    boolean existsByNumeroTel(String phoneNumber);

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombreCompleto) LIKE LOWER(CONCAT(:prefix, '%'))")
    List<Cliente> findByNombreCompletoStartingWithIgnoreCase(@Param("prefix") String prefix);

    @Query("SELECT c FROM Cliente c WHERE c.nombreCompleto LIKE %:nombre%")
    List<Cliente> findByNombreCompletoContaining(@Param("nombre") String nombre);

    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.almacenes a WHERE a.vendido = true OR a.rentado = true")
    List<Cliente> findClientesConAlmacenes();
}

