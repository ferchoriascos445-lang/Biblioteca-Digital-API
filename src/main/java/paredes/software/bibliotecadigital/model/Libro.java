package paredes.software.bibliotecadigital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String titulo;

    private int anioPublicacion;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "libro_genero",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    @JsonIgnore // 🔹 Evita ciclos y ConcurrentModificationException
    private Set<Genero> generos = new HashSet<>();

    // Método de ayuda para agregar un género al libro
    public void addGenero(Genero genero) {
        this.generos.add(genero);
        genero.getLibros().add(this);
    }
}


