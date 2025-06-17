package utez.edu.mx.almacenes.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Almacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clave;
    private LocalDate fechaRegistro;
    private double precioVenta;
    private double precioRenta;
    private String tamanio;

    @ManyToOne
    @JoinColumn(name = "cede_id")
    private Cede cede;

    // Getters y setters manuales para evitar errores Lombok

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return this.clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }

    public LocalDate getFechaRegistro() {
        return this.fechaRegistro;
    }
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public double getPrecioVenta() {
        return this.precioVenta;
    }
    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getPrecioRenta() {
        return this.precioRenta;
    }
    public void setPrecioRenta(double precioRenta) {
        this.precioRenta = precioRenta;
    }

    public String getTamanio() {
        return this.tamanio;
    }
    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public Cede getCede() {
        return this.cede;
    }
    public void setCede(Cede cede) {
        this.cede = cede;
    }
}