package utez.edu.mx.almacenes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utez.edu.mx.almacenes.model.Cede;

import java.util.List;
import java.util.Optional;

@Repository
public interface CedeRepository extends JpaRepository<Cede, Long> {

    Optional<Cede> findByClave(String key);

    @Query("SELECT c FROM Cede c WHERE LOWER(c.estado) LIKE LOWER(CONCAT('%', :state, '%'))")
    List<Cede> findByEstadoContainingIgnoreCase(@Param("state") String state);

    @Query("SELECT c FROM Cede c WHERE LOWER(c.municipio) LIKE LOWER(CONCAT('%', :city, '%'))")
    List<Cede> findByMunicipioContainingIgnoreCase(@Param("city") String city);

    boolean existsByEstadoAndMunicipio(String state, String city);
}
