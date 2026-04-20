package paredes.software.bibliotecadigital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

//🔹 Entidad que representa un género literario en la biblioteca digital
@Entity
@Data
@Table(name = "generos")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @ManyToMany(mappedBy = "generos")
    @JsonIgnore
    private Set<Libro> libros = new HashSet<>();
}
