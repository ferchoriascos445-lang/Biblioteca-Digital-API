package paredes.software.bibliotecadigital.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import paredes.software.bibliotecadigital.model.Usuario;
import paredes.software.bibliotecadigital.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // Métodos para gestionar usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Método para obtener un usuario por su ID
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
    }

    // Método para obtener un usuario por su email
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con email: " + email));
    }

    // Método para obtener usuarios por nombre y apellido (busqueda exacta)
    public List<Usuario> getUsuariosByNombreAndApellido(String nombre, String apellido) {
        return usuarioRepository.findByNombreAndApellido(nombre, apellido);
    }

    // Método para guardar un nuevo usuario en la base de datos, verificando que no exista otro con el mismo email o nombre y apellido
    public Usuario saveUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        if (usuarioRepository.existsByNombreAndApellido(usuario.getNombre(), usuario.getApellido())) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo nombre y apellido.");
        }
        return usuarioRepository.save(usuario);
    }


    // Método para actualizar un usuario existente
    public Usuario updateUsuario(Long id, Usuario usuarioActualizado) {
            Usuario usuario = getUsuarioById(id); // Reutilizamos el método para obtener el usuario por ID y manejar la excepción si no existe
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            return usuarioRepository.save(usuario);
    }

    // Método para eliminar un usuario por su ID
    public void deleteUsuario(Long id) {
        getUsuarioById(id); // Verificar si el usuario existe antes de intentar eliminarlo para evitar excepciones innecesarias
        usuarioRepository.deleteById(id);
    }

    // Método para verificar si un usuario existe por email (útil para evitar duplicados)
    public boolean existsUsuarioByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
