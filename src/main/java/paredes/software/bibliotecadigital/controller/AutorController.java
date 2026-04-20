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
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import paredes.software.bibliotecadigital.model.Autor;
import paredes.software.bibliotecadigital.service.AutorService;

@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    //GET - Obtener todos los autores
    @GetMapping
    public ResponseEntity<List<Autor>> getAllAutores() {
        return ResponseEntity.ok(autorService.getAllAutores());
    }

    //GET - Obtener un autor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Autor> getAutorById(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.getAutorById(id));
    }

    //POST - Crear un nuevo autor
    @PostMapping
    public ResponseEntity<Autor> createAutor(@RequestBody Autor autor) {
        Autor nuevoAutor = autorService.saveAutor(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAutor);
    }

    //PUT - Actualizar un autor existente
    @PutMapping("/{id}")
    public ResponseEntity<Autor> updateAutor(@PathVariable Long id, @RequestBody Autor autorActualizado) {
        Autor updatedAutor = autorService.updateAutor(id, autorActualizado);
        return ResponseEntity.ok(updatedAutor);
    }

    //DELETE - Eliminar un autor por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        autorService.deleteAutor(id);
        return ResponseEntity.noContent().build();
    }

}
