package paredes.software.bibliotecadigital.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paredes.software.bibliotecadigital.model.Libro;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByIsbn(String isbn);
    /* Verificar si un libro existe por su ISBN */

    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    /* Buscar libros por título, ignorando mayúsculas y minúsculas */
}
