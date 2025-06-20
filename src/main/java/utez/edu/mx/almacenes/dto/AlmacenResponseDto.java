package utez.edu.mx.almacenes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utez.edu.mx.almacenes.model.AlmacenSize;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlmacenResponseDto {

    private Long id;
    private String key;
    private LocalDate registrationDate;
    private BigDecimal salePrice;
    private BigDecimal rentalPrice;
    private AlmacenSize size;
    private CedeResponseDto cede;
}