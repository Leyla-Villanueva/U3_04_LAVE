package utez.edu.mx.almacenes.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionResponseDto {
    private AlmacenResponseDto almacen;
    private ClienteResponseDto cliente;
    private Boolean esVenta;
    private LocalDateTime fechaTransaccion;
    private BigDecimal montoTransaccion;
    private String tipoTransaccion;
}
