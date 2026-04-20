package paredes.software.bibliotecadigital.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prestamos")
public class Prestamo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    //🔹 Enum para representar el estado del préstamo
    public enum EstadoPrestamo {
        ACTIVO,
        DEVUELTO,
        ATRASADO
    }

    private EstadoPrestamo estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"prestamos"}) // Evita ciclos al serializar JSON
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    @JsonIgnoreProperties({"prestamos"}) // Evita ciclos al serializar JSON
    private Libro libro;


}