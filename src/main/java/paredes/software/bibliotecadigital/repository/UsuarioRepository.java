package paredes.software.bibliotecadigital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import paredes.software.bibliotecadigital.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar un usuario por su correo electrónico
    Optional<Usuario> findByEmail(String email);

    // Verificar si un usuario existe por su correo electrónico
    boolean existsByEmail(String email);

    //Verificar si un usuario existe por su nombre y apellido
    boolean existsByNombreAndApellido(String nombre, String apellido);


    // Método para buscar un usuario por su nombre y apellido 
    List <Usuario> findByNombreAndApellido(String nombre, String apellido);

    
}
