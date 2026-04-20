package paredes.software.bibliotecadigital.model;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 25)
    private String telefono;


    
    @Column(nullable = false, updatable = false)
    private LocalDate fechaRegistro;

    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Prestamo> prestamos;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDate.now();
    }

    
}

