package utez.edu.mx.almacenes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CedeRequestDto {

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "City is required")
    private String city;
}

