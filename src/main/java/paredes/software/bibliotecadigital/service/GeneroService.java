package paredes.software.bibliotecadigital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import paredes.software.bibliotecadigital.dto.GeneroRequestDTO;
import paredes.software.bibliotecadigital.dto.GeneroResponseDTO;
import paredes.software.bibliotecadigital.exception.RecursoNoEncontradoException;
import paredes.software.bibliotecadigital.model.Genero;
import paredes.software.bibliotecadigital.repository.GeneroRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;

    // 🔹 Métodos de conversion

    //Enteidad -> ResponseDTO
    private GeneroResponseDTO toResponseDTO(Genero genero) {
        GeneroResponseDTO dto = new GeneroResponseDTO();
        dto.setId(genero.getId());
        dto.setNombre(genero.getNombre());
        return dto;
    }
    
    // ResquestDTO -> Entidad
    private Genero toEntity(GeneroRequestDTO dto) {
        Genero genero = new Genero();
        genero.setNombre(dto.getNombre());
        return genero;
    }



    // 🔹 Método para obtener todos los géneros de la base de datos
    public List<GeneroResponseDTO> getAllGeneros() {
        return generoRepository.findAll().stream()
                .map(this::toResponseDTO)// Convertir cada entidad a DTO usando el método de conversión
                .collect(Collectors.toList());
    }

    // 🔹 Método para obtener un género por su ID
    public GeneroResponseDTO getGeneroById(Long id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Género no encontrado con ID: " + id));
        return toResponseDTO(genero);
    }

    // 🔹 Método para obtener un género por su nombre (busqueda parcial, ignorando mayúsculas)
    public GeneroResponseDTO getGeneroByNombre(String nombre) {
        Genero genero = generoRepository.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new RecursoNoEncontradoException("Género no encontrado con nombre: " + nombre));
        return toResponseDTO(genero);
    }


    // 🔹 Método para guardar un nuevo género en la base de datos
    public GeneroResponseDTO saveGenero(GeneroRequestDTO generoDTO) {
        if (generoRepository.findByNombreIgnoreCase(generoDTO.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un género con el nombre: " + generoDTO.getNombre());
        }
        Genero genero = toEntity(generoDTO);
        Genero guardado = generoRepository.save(genero);
        return toResponseDTO(guardado);
    }

    // 🔹 Método para actualizar un género existente
    public GeneroResponseDTO updateGenero(Long id, GeneroRequestDTO dto) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Género no encontrado con ID: " + id));
        genero.setNombre(dto.getNombre());
        Genero actualizado = generoRepository.save(genero);
        return toResponseDTO(actualizado);

    }

    // 🔹 Método para eliminar un género por su ID
    public void deleteGenero(Long id) {
        generoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Género no encontrado con ID: " + id));
        generoRepository.deleteById(id);
    }

    public List<Genero> getGenerosByIds(Object generosIds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGenerosByIds'");
    }

}

