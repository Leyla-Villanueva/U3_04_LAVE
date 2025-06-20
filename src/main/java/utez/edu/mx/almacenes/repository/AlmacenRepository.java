package utez.edu.mx.almacenes.repository;

import utez.edu.mx.almacenes.model.Almacen;
import utez.edu.mx.almacenes.model.AlmacenSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {

    Optional<Almacen> findByClave(String key);

    List<Almacen> findByCedeId(Long cedeId);

    List<Almacen> findBySize(AlmacenSize size);

    @Query("SELECT w FROM Almacen w WHERE w.precioVenta BETWEEN :minPrice AND :maxPrice")
    List<Almacen> findByPrecioVentaBetween(@Param("minPrice") BigDecimal minPrice,
                                           @Param("maxPrice") BigDecimal maxPrice);

    @Query("SELECT w FROM Almacen w WHERE w.precioRenta BETWEEN :minPrice AND :maxPrice")
    List<Almacen> findByPrecioRentaBetween(@Param("minPrice") BigDecimal minPrice,
                                             @Param("maxPrice") BigDecimal maxPrice);

    List<Almacen> findByFechaRegistroAfter(LocalDate date);

    @Query("SELECT COUNT(w) FROM Almacen w WHERE w.cede.id = :cedeId")
    long countByCedeId(@Param("cedeId") Long cedeId);

    List<Almacen> findByVendidoFalseAndRentadoFalse();

    List<Almacen> findByVendidoTrue();

    List<Almacen> findByRentadoTrue();

    List<Almacen> findByClienteId(Long clienteId);

    @Query("SELECT a FROM Almacen a WHERE a.cliente.id = :clienteId AND (a.vendido = true OR a.rentado = true)")
    List<Almacen> findActiveAlmacenesByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT COUNT(a) FROM Almacen a WHERE a.vendido = true")
    Long countVendidos();

    @Query("SELECT COUNT(a) FROM Almacen a WHERE a.rentado = true")
    Long countRentados();

    @Query("SELECT COUNT(a) FROM Almacen a WHERE a.vendido = false AND a.rentado = false")
    Long countDisponibles();
}

