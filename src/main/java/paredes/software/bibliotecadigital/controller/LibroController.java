package paredes.software.bibliotecadigital.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import paredes.software.bibliotecadigital.model.Libro;
import paredes.software.bibliotecadigital.service.LibroService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/libros")
@RequiredArgsConstructor
public class LibroController {

    // Inyectamos el servicio de libros para manejar la lógica de negocio relacionada con los libros
    private final LibroService libroService;

    // GET - Obtener todos los libros
    @GetMapping
    public ResponseEntity<List<Libro>> getAllLibros() {
        return ResponseEntity.ok(libroService.getAllLibros());
    }

    // GET - Obtener un libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.getLibroById(id));
    }

    // GET - Obtener libros por título (busqueda parcial, ignorando mayúsculas)
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Libro>> getLibrosByTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(libroService.getLibrosByTitulo(titulo));
    }

    // GET - Obtener libros por el nombre del autor (busqueda parcial, ignorando mayúsculas)
    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<Libro>> getLibrosByAutor(@PathVariable String autor) {
        return ResponseEntity.ok(libroService.getLibrosByAutor(autor));
    }

    // GET - Obtener libros por el nombre del género (busqueda parcial, ignorando mayúsculas)
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Libro>> getLibrosByGenero(@PathVariable String genero) {
        return ResponseEntity.ok(libroService.getLibrosByGenero(genero));
    }


    // POST - Crear un solo libro
    @PostMapping
    public ResponseEntity<Libro> createLibro(@RequestBody Libro libro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.saveLibro(libro));
    }

    // POST - Crear varios libros a la vez
    @PostMapping("/batch")
    public ResponseEntity<List<Libro>> createLibrosBatch(@RequestBody List<Libro> libros) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.saveAll(libros));
    }

    // PUT - Actualizar un libro
    @PutMapping("/{id}")
    public ResponseEntity<Libro> updateLibro(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        return ResponseEntity.ok(libroService.updateLibro(id, libroActualizado));
    }

    // DELETE - Eliminar un libro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        libroService.deleteLibro(id);
        return ResponseEntity.noContent().build();
    }
}
