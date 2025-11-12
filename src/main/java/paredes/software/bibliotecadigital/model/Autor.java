package paredes.software.bibliotecadigital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "autores")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "libros")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String nacionalidad;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // evita ciclos al serializar JSON (muy importante)
    private Set<Libro> libros = new HashSet<>();

    // 🔹 Método de utilidad para mantener coherencia bidireccional
    public void addLibro(Libro libro) {
        libros.add(libro);
        libro.setAutor(this);
    }

    public void removeLibro(Libro libro) {
        libros.remove(libro);
        libro.setAutor(null);
    }
}

