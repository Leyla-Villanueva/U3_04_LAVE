package utez.edu.mx.almacenes.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CedeResponseDto {

    private Long id;
    private String key;
    private String state;
    private String city;
}
