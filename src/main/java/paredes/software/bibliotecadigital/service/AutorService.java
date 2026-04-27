package paredes.software.bibliotecadigital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import paredes.software.bibliotecadigital.dto.AutorRequestDTO;
import paredes.software.bibliotecadigital.dto.AutorResponseDTO;
import paredes.software.bibliotecadigital.exception.RecursoNoEncontradoException;
import paredes.software.bibliotecadigital.model.Autor;
import paredes.software.bibliotecadigital.repository.AutorRepository;

@Service
@RequiredArgsConstructor //🔹 Anotación de Lombok para generar un constructor con los campos finales
public class AutorService {

    private final AutorRepository autorRepository;

    // 🔹 Método de conversion

    // Entidad -> ResponseDTO
    private AutorResponseDTO toResponseDTO(Autor autor) {
        AutorResponseDTO dto = new AutorResponseDTO();
        dto.setId(autor.getId());
        dto.setNombre(autor.getNombre());
        dto.setApellido(autor.getApellido());
        dto.setNacionalidad(autor.getNacionalidad());
        return dto;
    }

    // ResponseDTO -> Entidad
    private Autor toEntity(AutorRequestDTO dto) {
        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());
        autor.setApellido(dto.getApellido());
        autor.setNacionalidad(dto.getNacionalidad());
        return autor;
    }


    // 🔹 Método para obtener todos los autores de la base de datos
    public List<AutorResponseDTO> getAllAutores() {
        return autorRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Método para obtener un autor por su ID
    public AutorResponseDTO getAutorById(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con ID: " + id));
        return toResponseDTO(autor);
    }

    // 🔹 Método para guardar un nuevo autor en la base de datos
    public AutorResponseDTO saveAutor(AutorRequestDTO dto){
        Autor autor = toEntity(dto);
        Autor nuevoAutor = autorRepository.save(autor);
        return toResponseDTO(nuevoAutor);
    }

    // 🔹 Método para actualizar un autor existente
    public AutorResponseDTO updateAutor(Long id, AutorRequestDTO autorActualizado) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con ID: " + id));
        autor.setNombre(autorActualizado.getNombre());
        autor.setApellido(autorActualizado.getApellido());
        autor.setNacionalidad(autorActualizado.getNacionalidad());
        Autor updatedAutor = autorRepository.save(autor);
        return toResponseDTO(updatedAutor);
    }

    // 🔹 Método para eliminar un autor por su ID
    public void deleteAutor(Long id) {
        // Verificar si el autor existe antes de intentar eliminarlo para evitar excepciones innecesarias
        autorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con ID: " + id));
        autorRepository.deleteById(id);
    }

    public Autor getAutorEntityById(Long autorId) {
        return autorRepository.findById(autorId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con ID: " + autorId));
    }


}

