package paredes.software.bibliotecadigital.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import paredes.software.bibliotecadigital.model.Libro;
import paredes.software.bibliotecadigital.repository.LibroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }

    public Optional<Libro> getLibroById(Long id) {
        return libroRepository.findById(id);
    }

    public Libro saveLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public List<Libro> saveAll(List<Libro> libros) {
        return libroRepository.saveAll(libros);
    }

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

    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }
}

