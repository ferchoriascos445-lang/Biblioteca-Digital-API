package paredes.software.bibliotecadigital.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import paredes.software.bibliotecadigital.model.Prestamo;
import paredes.software.bibliotecadigital.repository.PrestamoRepository;

@Service
@RequiredArgsConstructor
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;

    // Métodos para gestionar préstamos
    public List<Prestamo> getAllPrestamos() {
        return prestamoRepository.findAll();
    }

    // Método para obtener un préstamo por su ID
    public Prestamo getPrestamoById(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado con ID: " + id));
    }

    // Métodos para obtener préstamos por diferentes criterios
    public List<Prestamo> getPrestamosByUsuarioId(Long usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId);
    }

    // Método para obtener préstamos por libro
    public List<Prestamo> getPrestamosByLibroId(Long libroId) {
        return prestamoRepository.findByLibroId(libroId);
    }

    // Método para obtener préstamos por estado (Activo, Devuelto, Vencido)
    public List<Prestamo> getPrestamosByEstado(Prestamo.EstadoPrestamo estado) {
        return prestamoRepository.findByEstado(estado);
    }

    // Método para obtener préstamos activos por usuario
    public List<Prestamo> getPrestamosActivosByUsuario(Long usuarioId) {
        return prestamoRepository.findByUsuarioIdAndEstado(usuarioId, Prestamo.EstadoPrestamo.ACTIVO);
    }

    // Método para obtener préstamos por rango de fechas
    public List<Prestamo> getPrestamosBetween(LocalDate inicio, LocalDate fin) {
        return prestamoRepository.findByFechaPrestamoBetween(inicio, fin);
    }

    // Método para obtener préstamos vencidos (fecha de devolución pasada y estado activo)
    public List<Prestamo> getPrestamosVencidos() {
        return prestamoRepository.findByFechaDevolucionBeforeAndEstado(
            LocalDate.now(),
             Prestamo.EstadoPrestamo.ACTIVO);
    }

    // Método para guardar un nuevo préstamo en la base de datos, verificando que el libro no esté prestado en un préstamo activo
    public Prestamo savePrestamo(Prestamo prestamo) {
        if (prestamoRepository.existsByLibroIdAndEstado(
            prestamo.getLibro().getId(), 
            Prestamo.EstadoPrestamo.ACTIVO)) {
            throw new IllegalArgumentException("El libro ya está prestado en un préstamo activo.");
        }
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setEstado(Prestamo.EstadoPrestamo.ACTIVO);
        return prestamoRepository.save(prestamo);
    }


    // Método para actualizar un préstamo existente
    public Prestamo updatePrestamo(Long id, Prestamo prestamoActualizado) {
         Prestamo prestamo = getPrestamoById(id); // reutilizamos método propio
        prestamo.setFechaPrestamo(prestamoActualizado.getFechaPrestamo());
        prestamo.setFechaDevolucion(prestamoActualizado.getFechaDevolucion());
        prestamo.setEstado(prestamoActualizado.getEstado());
        prestamo.setUsuario(prestamoActualizado.getUsuario());
        prestamo.setLibro(prestamoActualizado.getLibro());
        return prestamoRepository.save(prestamo);
    }

    // Método para marcar un préstamo como devuelto
    public Prestamo devolPrestamo(Long id) {
        Prestamo prestamo = getPrestamoById(id); // reutilizamos método propio
        if (prestamo.getEstado() == Prestamo.EstadoPrestamo.ACTIVO) {
            throw new IllegalArgumentException("Este préstamo no está activo.");
        }
        prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);
        prestamo.setFechaDevolucion(LocalDate.now());
        return prestamoRepository.save(prestamo);
    }

    // Método para eliminar un préstamo por su ID
    public void deletePrestamo(Long id) {
        getPrestamoById(id); // Verificar si el préstamo existe antes de intentar eliminarlo para evitar excepciones innecesarias
        prestamoRepository.deleteById(id);
    }

    // Método para verificar si un libro está en un préstamo activo
    public boolean isLibroPrestadoActivo(Long libroId) {
        return prestamoRepository.existsByLibroIdAndEstado(libroId, Prestamo.EstadoPrestamo.ACTIVO);
    }
}
