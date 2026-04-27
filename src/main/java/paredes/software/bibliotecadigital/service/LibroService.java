package paredes.software.bibliotecadigital.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import paredes.software.bibliotecadigital.dto.AutorResponseDTO;
import paredes.software.bibliotecadigital.dto.GeneroResponseDTO;
import paredes.software.bibliotecadigital.dto.LibroRequestDTO;
import paredes.software.bibliotecadigital.dto.LibroResponseDTO;
import paredes.software.bibliotecadigital.exception.RecursoNoEncontradoException;
import paredes.software.bibliotecadigital.model.Autor;
import paredes.software.bibliotecadigital.model.Libro;
import paredes.software.bibliotecadigital.repository.LibroRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //🔹 Anotación de Lombok para generar un constructor con los campos finales
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorService autorService;
    private final GeneroService generoService;

    // 🔹 Métodos de conversión

    // Entidad -> ResponseDTO
    private LibroResponseDTO toResponseDTO(Libro libro) {

        LibroResponseDTO dto = new LibroResponseDTO();
        dto.setId(libro.getId());
        dto.setIsbn(libro.getIsbn());
        dto.setTitulo(libro.getTitulo());
        dto.setAnioPublicacion(libro.getAnioPublicacion());

        // 🔹 Convertir el autor a AutorResponseDTO
        AutorResponseDTO autorDto = new AutorResponseDTO();
        autorDto.setId(libro.getAutor().getId());
        autorDto.setNombre(libro.getAutor().getNombre());
        autorDto.setApellido(libro.getAutor().getApellido());
        autorDto.setNacionalidad(libro.getAutor().getNacionalidad());
        dto.setAutor(autorDto);

        // 🔹 Convertir la lista de géneros a List<GeneroResponseDTO>
        List<GeneroResponseDTO> generoDtos = libro.getGeneros().stream()
                .map(genero -> {
                    GeneroResponseDTO generoDto = new GeneroResponseDTO();
                    generoDto.setId(genero.getId());
                    generoDto.setNombre(genero.getNombre());
                    return generoDto;
                })
                .collect(Collectors.toList());

        dto.setGeneros(generoDtos);

        return dto;
    }

    // RequestDTO -> Entidad
    private Libro toEntity(LibroRequestDTO dto) {

        Libro libro = new Libro();

        libro.setIsbn(dto.getIsbn());
        libro.setTitulo(dto.getTitulo());
        libro.setAnioPublicacion(dto.getAnioPublicacion());

        // 🔹 Buscar autor 
        Autor autor = autorService.getAutorEntityById(dto.getAutorId());
        libro.setAutor(autor);

        // 🔹 Buscar géneros
        libro.setGeneros(
                generoService.getGenerosByIds(dto.getGenerosIds())
                        .stream()
                        .collect(Collectors.toSet())
        );

        return libro;
    }

    // 🔹 Método para obtener todos los libros de la base de datos
    public List<LibroResponseDTO> getAllLibros() {
        return libroRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Método para obtener un libro por su ID
    public LibroResponseDTO getLibroById(Long id) {
        return libroRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con ID: " + id));
    }

    // 🔹 Método para obtener libros por título (busqueda parcial, ignorando mayúsculas)
    public List<LibroResponseDTO> getLibrosByTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Método para obtener libros por el nombre del autor (busqueda parcial, ignorando mayúsculas)
    public List<LibroResponseDTO> getLibrosByAutor(String autor) {
        return libroRepository.findByAutorNombreContainingIgnoreCase(autor).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Método para obtener libros por el nombre del género (busqueda parcial, ignorando mayúsculas)
    public List<LibroResponseDTO> getLibrosByGenero(String genero) {
        return libroRepository.findByGenerosNombreContainingIgnoreCase(genero).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Método para guardar un nuevo libro en la base de datos
    public LibroResponseDTO saveLibro(LibroRequestDTO dto) {

        if (libroRepository.existsByIsbn(dto.getIsbn())) {
            throw new IllegalArgumentException("Ya existe un libro con el ISBN: " + dto.getIsbn());
        }

        Libro libro = toEntity(dto);
        return toResponseDTO(libroRepository.save(libro));
    }

    // 🔹 Método para guardar una lista de libros en la base de datos (útil para operaciones en lote)
    public List<LibroResponseDTO> saveAll(List<LibroRequestDTO> dtos) {

        List<Libro> libros = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return libroRepository.saveAll(libros).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Método para actualizar un libro existente
    public LibroResponseDTO updateLibro(Long id, LibroRequestDTO dto) {

        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con ID: " + id));

        libro.setIsbn(dto.getIsbn());
        libro.setTitulo(dto.getTitulo());
        libro.setAnioPublicacion(dto.getAnioPublicacion());

        // 🔹 Actualizar autor 
        Autor autor = autorService.getAutorEntityById(dto.getAutorId());
        libro.setAutor(autor);
       

        // 🔹 Actualizar géneros
        libro.setGeneros(
                generoService.getGenerosByIds(dto.getGenerosIds())
                        .stream()
                        .collect(Collectors.toSet())
        );

        return toResponseDTO(libroRepository.save(libro));
    }

    // 🔹 Método para eliminar un libro por su ID
    public void deleteLibro(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Libro no encontrado con ID: " + id);
        }
        libroRepository.deleteById(id);
    }

    // 🔹 Método para verificar si un libro existe por su ISBN (útil para evitar duplicados)
    public boolean existsLibroByIsbn(String isbn) {
        return libroRepository.existsByIsbn(isbn);
    }
}