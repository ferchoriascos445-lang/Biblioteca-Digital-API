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

    //Buscar libro por autor
    List<Libro> findByAutorNombreContainingIgnoreCase(String autor);
    /* Buscar libros por el nombre del autor, ignorando mayúsculas y minúsculas */

    //Buscar libro por género
    List<Libro> findByGenerosNombreContainingIgnoreCase(String nombre);
    /* Buscar libros por el nombre del género, ignorando mayúsculas y minúsculas */

    //Saber si el libro esta disponible
    boolean existsByIdAndAutorIsNotNull(Long id);
    /* Verificar si un libro existe por su ID y tiene un autor asignado */
 
    
}
