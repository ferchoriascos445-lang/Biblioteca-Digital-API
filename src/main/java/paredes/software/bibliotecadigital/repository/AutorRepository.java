package paredes.software.bibliotecadigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paredes.software.bibliotecadigital.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {


    /* Nombre de la nueva tabla intermedia
    Define la columna de la clave foránea de 'Libro' en la tabla intermedia */
    Autor findByNombreAndApellido(String nombre, String apellido);
}
