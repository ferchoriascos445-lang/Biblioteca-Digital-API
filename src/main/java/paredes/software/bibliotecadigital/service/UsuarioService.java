package paredes.software.bibliotecadigital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import paredes.software.bibliotecadigital.dto.UsuarioRequestDTO;
import paredes.software.bibliotecadigital.dto.UsuarioResponseDTO;
import paredes.software.bibliotecadigital.exception.RecursoNoEncontradoException;
import paredes.software.bibliotecadigital.model.Usuario;
import paredes.software.bibliotecadigital.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // 🔹 Métodos de conversion

    // Entidad -> ResponseDTO
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        return dto;
    }

    // RequestDTO -> Entidad
    private Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        return usuario;
    }

    // 🔹 Métodos para gestionar usuarios
    public List<UsuarioResponseDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para obtener un usuario por su ID
    public UsuarioResponseDTO getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
        return toResponseDTO(usuario);
    }

    // Método para obtener un usuario por su email
    public UsuarioResponseDTO getUsuarioByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con email: " + email));
        return toResponseDTO(usuario);
    }

    // Método para obtener usuarios por nombre y apellido (busqueda exacta)
    public List<UsuarioResponseDTO> getUsuariosByNombreAndApellido(String nombre, String apellido) {
        return usuarioRepository.findByNombreAndApellido(nombre, apellido).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para guardar un nuevo usuario en la base de datos, verificando que no
    // exista otro con el mismo email o nombre y apellido
    public UsuarioResponseDTO saveUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + dto.getEmail());
        }
        if (usuarioRepository.existsByNombreAndApellido(dto.getNombre(), dto.getApellido())) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo nombre y apellido.");
        }
        Usuario usuario = toEntity(dto);
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return toResponseDTO(nuevoUsuario);
    }

    // Método para actualizar un usuario existente
    public UsuarioResponseDTO updateUsuario(Long id, UsuarioRequestDTO usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setEmail(usuarioActualizado.getEmail());
        usuario.setTelefono(usuarioActualizado.getTelefono());
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return toResponseDTO(updatedUsuario);
    }

    // Método para eliminar un usuario por su ID
    public void deleteUsuario(Long id) {
        usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
        usuarioRepository.deleteById(id);
    }

    // Método para verificar si un usuario existe por email (útil para evitar
    // duplicados)
    public boolean existsUsuarioByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
