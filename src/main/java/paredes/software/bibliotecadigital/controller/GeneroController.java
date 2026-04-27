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
import paredes.software.bibliotecadigital.dto.GeneroRequestDTO;
import paredes.software.bibliotecadigital.dto.GeneroResponseDTO;
import paredes.software.bibliotecadigital.service.GeneroService;

@RestController
@RequestMapping("/api/v1/generos")
@RequiredArgsConstructor
public class GeneroController {

    private final GeneroService generoService;

    //GET - obtener todos los géneros
    @GetMapping
    public ResponseEntity<List<GeneroResponseDTO>> getAllGeneros() {
        return ResponseEntity.ok(generoService.getAllGeneros());
    }
    
    //GET - obtener un género por ID
    @GetMapping("/{id}")
    public ResponseEntity<GeneroResponseDTO> getGeneroById(@PathVariable Long id) {
        return ResponseEntity.ok(generoService.getGeneroById(id));
    }

    //GET - obtener un género por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<GeneroResponseDTO> getGeneroByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(generoService.getGeneroByNombre(nombre));
    }

    //POST - crear un nuevo género
    @PostMapping
    public ResponseEntity<GeneroResponseDTO> createGenero(@RequestBody GeneroRequestDTO generoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generoService.saveGenero(generoDTO));
    }

    //PUT - actualizar un género existente
    @PutMapping("/{id}")
    public ResponseEntity<GeneroResponseDTO> updateGenero(@PathVariable Long id, @RequestBody GeneroRequestDTO generoActualizado) {
        return ResponseEntity.ok(generoService.updateGenero(id, generoActualizado));
    }
    
    //DELETE - eliminar un género por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenero(@PathVariable Long id) {
        generoService.deleteGenero(id);
        return ResponseEntity.noContent().build();
    }

}
