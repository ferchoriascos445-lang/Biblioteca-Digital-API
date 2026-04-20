package paredes.software.bibliotecadigital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import paredes.software.bibliotecadigital.model.Genero;
import paredes.software.bibliotecadigital.repository.GeneroRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;


    // 🔹 Método para obtener todos los géneros de la base de datos
    public List<Genero> getAllGeneros() {
        return generoRepository.findAll();
    }

    // 🔹 Método para obtener un género por su ID
    public Genero getGeneroById(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Género no encontrado con ID: " + id));
    }

    // 🔹 Método para obtener un género por su nombre (busqueda parcial, ignorando mayúsculas)
    public Genero getGeneroByNombre(String nombre) {
        return generoRepository.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Género no encontrado con nombre: " + nombre));
    }


    // 🔹 Método para guardar un nuevo género en la base de datos
    public Genero saveGenero(Genero genero) {
        if (generoRepository.findByNombreIgnoreCase(genero.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un género con el nombre: " + genero.getNombre());
        }
        return generoRepository.save(genero);
    }

    // 🔹 Método para actualizar un género existente
    public Genero updateGenero(Long id, Genero generoActualizado) {
        Genero genero = getGeneroById(id); // Reutilizamos el método para obtener el género por ID y manejar la excepción si no existe
        genero.setNombre(generoActualizado.getNombre());
        return generoRepository.save(genero);
    }

    // 🔹 Método para eliminar un género por su ID
    public void deleteGenero(Long id) {
        getGeneroById(id); // Verificar si el género existe antes de intentar eliminarlo para evitar excepciones innecesarias
        generoRepository.deleteById(id);
        
    }

}

