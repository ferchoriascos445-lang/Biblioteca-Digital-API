package paredes.software.bibliotecadigital.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import paredes.software.bibliotecadigital.model.Libro;
import paredes.software.bibliotecadigital.repository.LibroRepository;

import java.util.List;

@Service
@RequiredArgsConstructor//🔹 Anotación de Lombok para generar un constructor con los campos finales
public class LibroService {

    private final LibroRepository libroRepository;

    // 🔹 Método para obtener todos los libros de la base de datos
    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }

    // 🔹 Método para obtener un libro por su ID
    public Libro getLibroById(Long id) {
        return libroRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
    }

    // 🔹 Método para obtener libros por título (busqueda parcial, ignorando mayúsculas)
    public List<Libro> getLibrosByTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // 🔹 Método para obtener libros por el nombre del autor (busqueda parcial, ignorando mayúsculas)
    public List<Libro> getLibrosByAutor(String autor) {
        return libroRepository.findByAutorNombreContainingIgnoreCase(autor);
    }

    // 🔹 Método para obtener libros por el nombre del género (busqueda parcial, ignorando mayúsculas)
    public List<Libro> getLibrosByGenero(String genero) {
        return libroRepository.findByGenerosNombreContainingIgnoreCase(genero);
    }

    // 🔹 Método para guardar un nuevo libro en la base de datos
    public Libro saveLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    //🔹 Método para guardar libro por Isbn
    public Libro saveLibroByIsbn(Libro libro) {
        if (libroRepository.existsByIsbn(libro.getIsbn())) {
            throw new IllegalArgumentException("Ya existe un libro con el ISBN: " + libro.getIsbn());
        }
        return libroRepository.save(libro);
    }

    // 🔹 Método para guardar una lista de libros en la base de datos (útil para operaciones en lote)
    public List<Libro> saveAll(List<Libro> libros) {
        return libroRepository.saveAll(libros);
    }

    // 🔹 Método para actualizar un libro existente
    public Libro updateLibro(Long id, Libro libroActualizado) {
        return libroRepository.findById(id).map(libro -> {
            libro.setTitulo(libroActualizado.getTitulo());
            libro.setAnioPublicacion(libroActualizado.getAnioPublicacion());
            libro.setIsbn(libroActualizado.getIsbn());
            libro.setAutor(libroActualizado.getAutor());
            libro.setGeneros(libroActualizado.getGeneros());
            return libroRepository.save(libro);
        }).orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
    }


    // 🔹 Método para eliminar un libro por su ID
    public void deleteLibro(Long id) {
        // Verificar si el libro existe antes de intentar eliminarlo para evitar excepciones innecesarias
        if (!libroRepository.existsById(id)) {
            throw new IllegalArgumentException("Libro no encontrado con ID: " + id);
            
        }
        libroRepository.deleteById(id);
    }

    // 🔹 Método para verificar si un libro existe por su ISBN (útil para evitar duplicados)
    public boolean existsLibroByIsbn(String isbn) {
        return libroRepository.existsByIsbn(isbn);
    }

    // 🔹 Regla: Libro no disponible
    public boolean isLibroDisponible(Long id) {
        Libro libro = libroRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
        return libro.getAutor() != null;
    }

}

