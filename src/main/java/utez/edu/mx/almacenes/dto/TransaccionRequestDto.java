package utez.edu.mx.almacenes.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionRequestDto {

    @NotNull(message = "Almacen ID is required")
    private Long almacenId;

    @NotNull(message = "Cliente ID is required")
    private Long clienteId;

    @NotNull(message = "Transaction type is required")
    private Boolean esVenta;
}