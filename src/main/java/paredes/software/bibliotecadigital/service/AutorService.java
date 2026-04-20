package paredes.software.bibliotecadigital.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import paredes.software.bibliotecadigital.model.Autor;
import paredes.software.bibliotecadigital.repository.AutorRepository;

@Service
@RequiredArgsConstructor//🔹 Anotación de Lombok para generar un constructor con los campos finales
public class AutorService {

    private final AutorRepository autorRepository;


    // 🔹 Método de acceso al repositorio de autores (puede ser útil para otros servicios o controladores)
    public AutorRepository getAutorRepository() {
        return autorRepository;
    }

    // 🔹 Método para obtener todos los autores de la base de datos
    public List<Autor> getAllAutores() {
        return autorRepository.findAll();
    }

    // 🔹 Método para obtener un autor por su ID
    public Autor getAutorById(Long id) {
        return autorRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado con ID: " + id));
    }

    // 🔹 Método para guardar un nuevo autor en la base de datos
    public Autor saveAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    // 🔹 Método para actualizar un autor existente
    public Autor updateAutor(Long id, Autor autorActualizado) {
        return autorRepository.findById(id).map(autor -> {
            autor.setNombre(autorActualizado.getNombre());
            autor.setApellido(autorActualizado.getApellido());
            autor.setNacionalidad(autorActualizado.getNacionalidad());
            return autorRepository.save(autor);
        }).orElseThrow(() -> new IllegalArgumentException("Autor no encontrado con ID: " + id));
    }

    // 🔹 Método para eliminar un autor por su ID
    public void deleteAutor(Long id) {
        // Verificar si el autor existe antes de intentar eliminarlo para evitar excepciones innecesarias
        if (!autorRepository.existsById(id)) {
            throw new IllegalArgumentException("Autor no encontrado con ID: " + id);
        }
        autorRepository.deleteById(id);
    }
    
}
