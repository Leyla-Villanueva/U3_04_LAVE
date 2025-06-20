package utez.edu.mx.almacenes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "almacenes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = true)
    private String clave;

    @NotNull(message = "Registration date is required")
    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @NotNull(message = "Sale price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Sale price must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta;

    @NotNull(message = "Rental price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Rental price must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioRenta;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Size is required")
    @Column(nullable = false)
    private AlmacenSize size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cede_id", nullable = false)
    @NotNull(message = "Cede is required")
    private Cede cede;

    @Column(nullable = false)
    @Builder.Default
    private Boolean vendido = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean rentado = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    public boolean isDisponible() {
        return !vendido && !rentado;
    }
}
