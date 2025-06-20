package utez.edu.mx.almacenes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "cedes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = true)
    private String clave;

    @NotBlank(message = "State is required")
    @Column(nullable = false)
    private String estado;

    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String municipio;

}