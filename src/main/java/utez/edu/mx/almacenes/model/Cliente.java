package utez.edu.mx.almacenes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    @Column(nullable = false)
    private String nombreCompleto;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "Phone number must be a valid format (E.164 standard)"
    )
    @Column(nullable = false)
    private String numeroTel;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Almacen> almacenes;
}
