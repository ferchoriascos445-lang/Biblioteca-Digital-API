package paredes.software.bibliotecadigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paredes.software.bibliotecadigital.model.Genero;

import java.util.Optional;

public interface GeneroRepository extends JpaRepository<Genero, Long> {

    Genero findByNombre(String nombre);
    // Metodo para buscar un género por su nombre

    /*boolean existsByNombre(String nombre);*/

    Optional<Genero> findByNombreIgnoreCase(String nombre);

    // Metodo para verificar si un género existe por su nombre

    // Metodo para eliminar un género por su nombre
    void deleteByNombreIgnoreCase(String nombre);
}
