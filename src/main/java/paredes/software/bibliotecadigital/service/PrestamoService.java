package paredes.software.bibliotecadigital.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import paredes.software.bibliotecadigital.dto.PrestamoRequestDTO;
import paredes.software.bibliotecadigital.dto.PrestamoResponseDTO;
import paredes.software.bibliotecadigital.exception.RecursoNoEncontradoException;
import paredes.software.bibliotecadigital.model.Prestamo;
import paredes.software.bibliotecadigital.repository.PrestamoRepository;

@Service
@RequiredArgsConstructor
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;

    //Metodo de conversion
    // Entidad -> ResponseDTO
    private PrestamoResponseDTO toResponseDTO(Prestamo prestamo) {
        PrestamoResponseDTO dto = new PrestamoResponseDTO();
        dto.setId(prestamo.getId());
        dto.setFechaPrestamo(prestamo.getFechaPrestamo());
        dto.setFechaDevolucion(prestamo.getFechaDevolucion());
        dto.setEstado(prestamo.getEstado());
        dto.setUsuarioId(prestamo.getUsuario().getId());
        dto.setLibroId(prestamo.getLibro().getId());
        return dto;
    }
    // ResponseDTO -> Entidad
    private Prestamo toEntity(PrestamoResponseDTO dto) {
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaPrestamo(dto.getFechaPrestamo());
        prestamo.setFechaDevolucion(dto.getFechaDevolucion());
        prestamo.setEstado(dto.getEstado());
        // Aquí se deberían setear las referencias a Usuario y Libro usando sus respectivos servicios
        return prestamo;
    }

    // Métodos para gestionar préstamos
    public List<PrestamoResponseDTO> getAllPrestamos() {
        return prestamoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para obtener un préstamo por su ID
    public PrestamoResponseDTO getPrestamoById(Long id) {
        return toResponseDTO(prestamoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Préstamo no encontrado con ID: " + id)));
    }

    // Métodos para obtener préstamos por diferentes criterios
    public List<PrestamoResponseDTO> getPrestamosByUsuarioId(Long usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para obtener préstamos por libro
    public List<PrestamoResponseDTO> getPrestamosByLibroId(Long libroId) {
        return prestamoRepository.findByLibroId(libroId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para obtener préstamos por estado (Activo, Devuelto, Vencido)
    public List<PrestamoResponseDTO> getPrestamosByEstado(Prestamo.EstadoPrestamo estado) {
        return prestamoRepository.findByEstado(estado).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para obtener préstamos activos por usuario
    public List<PrestamoResponseDTO> getPrestamosActivosByUsuario(Long usuarioId) {
        return prestamoRepository.findByUsuarioIdAndEstado(usuarioId, Prestamo.EstadoPrestamo.ACTIVO).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para obtener préstamos por rango de fechas
    public List<PrestamoResponseDTO> getPrestamosBetween(LocalDate inicio, LocalDate fin) {
        return prestamoRepository.findByFechaPrestamoBetween(inicio, fin).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para obtener préstamos vencidos (fecha de devolución pasada y estado
    // activo)
    public List<PrestamoResponseDTO> getPrestamosVencidos() {
        return prestamoRepository.findByFechaDevolucionBeforeAndEstado(
                LocalDate.now(),
                Prestamo.EstadoPrestamo.ACTIVO).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Método para guardar un nuevo préstamo en la base de datos, verificando que el
    // libro no esté prestado en un préstamo activo
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
    public PrestamoResponseDTO updatePrestamo(Long id, PrestamoRequestDTO dto) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Préstamo no encontrado con ID: " + id));
        prestamo.setFechaPrestamo(dto.getFechaPrestamo());
        prestamo.setFechaDevolucion(dto.getFechaDevolucion());
        prestamo.setEstado(dto.getEstado());
        // Aquí se deberían actualizar las referencias a Usuario y Libro usando sus respectivos servicios
        return toResponseDTO(prestamoRepository.save(prestamo));
    }
   

    // Método para marcar un préstamo como devuelto
    public Prestamo marcarComoDevuelto(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Préstamo no encontrado con ID: " + id));
        prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);
        prestamo.setFechaDevolucion(LocalDate.now());
        return prestamoRepository.save(prestamo);
    }
    
   

    // Método para eliminar un préstamo por su ID
    public void deletePrestamo(Long id) {
        getPrestamoById(id); // Verificar si el préstamo existe antes de intentar eliminarlo para evitar
                             // excepciones innecesarias
        prestamoRepository.deleteById(id);
    }

    // Método para verificar si un libro está en un préstamo activo
    public boolean isLibroPrestadoActivo(Long libroId) {
        return prestamoRepository.existsByLibroIdAndEstado(libroId, Prestamo.EstadoPrestamo.ACTIVO);
    }
  
   
}
