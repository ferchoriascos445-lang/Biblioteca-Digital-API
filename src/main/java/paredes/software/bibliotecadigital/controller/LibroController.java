package paredes.software.bibliotecadigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paredes.software.bibliotecadigital.model.Libro;
import paredes.software.bibliotecadigital.service.LibroService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    // GET - Obtener todos los libros
    @GetMapping
    public List<Libro> getAllLibros() {
        return libroService.getAllLibros();
    }

    // GET - Obtener un libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id) {
        return libroService.getLibroById(id)
                .map(libro -> new ResponseEntity<>(libro, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST - Crear un solo libro
    @PostMapping
    public ResponseEntity<?> createLibro(@RequestBody Libro libro) {
        try {
            Libro nuevoLibro = libroService.saveLibro(libro);
            return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar el libro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // POST - Crear varios libros a la vez
    @PostMapping("/batch")
    public ResponseEntity<?> createLibrosBatch(@RequestBody List<Libro> libros) {
        try {
            List<Libro> nuevosLibros = libroService.saveAll(libros);
            return new ResponseEntity<>(nuevosLibros, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar los libros: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // PUT - Actualizar un libro
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLibro(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        try {
            Libro updatedLibro = libroService.updateLibro(id, libroActualizado);
            return new ResponseEntity<>(updatedLibro, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - Eliminar un libro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        libroService.deleteLibro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
