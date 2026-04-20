package paredes.software.bibliotecadigital.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import paredes.software.bibliotecadigital.model.Usuario;
import paredes.software.bibliotecadigital.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // GET - Obtener todos los usuarios de la base de datos
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    // GET - Obtener un usuario específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    // GET - Obtener un usuario por su correo electrónico (búsqueda única)
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getUsuarioByEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.getUsuarioByEmail(email));
    }

    // GET - Buscar usuarios por nombre y apellido (búsqueda con parámetros)
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> getUsuariosByNombreAndApellido(@RequestParam String nombre, @RequestParam String apellido) {
        return ResponseEntity.ok(usuarioService.getUsuariosByNombreAndApellido(nombre, apellido));
    }

    // POST - Crear un nuevo usuario en la base de datos (retorna 201 CREATED)
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    // PUT - Actualizar un usuario existente por su ID
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuarioActualizadoResponse = usuarioService.updateUsuario(id, usuarioActualizado);
        return ResponseEntity.ok(usuarioActualizadoResponse);
    }

    // DELETE - Eliminar un usuario de la base de datos (retorna 204 NO CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

