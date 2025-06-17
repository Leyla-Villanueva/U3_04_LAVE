package utez.edu.mx.almacenes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private String clave;
    private String estado;
    private String municipio;

    // Métodos manuales por si Lombok falla
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Getters y setters para otros campos si no usas Lombok

    public String getClave() {
        return this.clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return this.estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return this.municipio;
    }
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
}