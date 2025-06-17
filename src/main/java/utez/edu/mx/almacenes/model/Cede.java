package utez.edu.mx.almacenes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String claveCede; // Se genera automáticamente

    @NotBlank(message = "El estado no debe estar vacío")
    private String estado;

    @NotBlank(message = "El municipio no debe estar vacío")
    private String municipio;
}
