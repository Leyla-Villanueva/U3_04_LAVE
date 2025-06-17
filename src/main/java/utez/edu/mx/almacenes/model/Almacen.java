package utez.edu.mx.almacenes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String claveAlmacen;

    private LocalDate fechaRegistro;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor a 0")
    private Double precioVenta;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de renta debe ser mayor a 0")
    private Double precioRenta;

    @Pattern(regexp = "G|M|P", message = "El tamaño debe ser G, M o P")
    private String tamaño;

    @ManyToOne
    @JoinColumn(name = "cede_id")
    @NotNull(message = "El almacén debe estar asociado a una cede")

    private Cede cede;
}
