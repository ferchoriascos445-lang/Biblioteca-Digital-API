package paredes.software.bibliotecadigital.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import paredes.software.bibliotecadigital.dto.PrestamoRequestDTO;
import paredes.software.bibliotecadigital.dto.PrestamoResponseDTO;
import paredes.software.bibliotecadigital.model.Prestamo;
import paredes.software.bibliotecadigital.service.PrestamoService;

@RestController
@RequestMapping("/api/v1/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;

    // GET - Obtener todos los préstamos
    @GetMapping
    public ResponseEntity<List<PrestamoResponseDTO>> getAllPrestamos() {
        return ResponseEntity.ok(prestamoService.getAllPrestamos());
    }

    // GET - Obtener un préstamo por ID
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> getPrestamoById(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.getPrestamoById(id));
    }

    // GET - Obtener préstamos por ID de usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PrestamoResponseDTO>> getPrestamosByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(prestamoService.getPrestamosByUsuarioId(usuarioId));
    }

    // GET - Obtener préstamos por ID de libro
    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<PrestamoResponseDTO>> getPrestamosByLibroId(@PathVariable Long libroId) {
        return ResponseEntity.ok(prestamoService.getPrestamosByLibroId(libroId));
    }

    // GET - Obtener préstamos por estado (Activo, Devuelto, Vencido)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PrestamoResponseDTO>> getPrestamosByEstado(@PathVariable String estado) {
        Prestamo.EstadoPrestamo estadoEnum = Prestamo.EstadoPrestamo.valueOf(estado.toUpperCase());
        return ResponseEntity.ok(prestamoService.getPrestamosByEstado(estadoEnum));
    }

    // GET - Obtener préstamos activos por usuario
    @GetMapping("/usuario/{usuarioId}/activos")
    public ResponseEntity<List<PrestamoResponseDTO>> getActivosByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(prestamoService.getPrestamosActivosByUsuario(usuarioId));
    }

    // GET - Obtener préstamos por rango de fechas
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<PrestamoResponseDTO>> getPrestamosByRangoFechas(
            @RequestParam String fechaInicio, @RequestParam String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);
        return ResponseEntity.ok(prestamoService.getPrestamosBetween(inicio, fin));
    }

    // GET - Obtener préstamos vencidos
    @GetMapping("/vencidos")
    public ResponseEntity<List<PrestamoResponseDTO>> getVencidos() {
        return ResponseEntity.ok(prestamoService.getPrestamosVencidos());
    }

    // POST - Crear un nuevo préstamo
    @PostMapping
    public ResponseEntity<PrestamoResponseDTO> createPrestamo(@RequestBody PrestamoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.createPrestamo(dto));
    }

    // PUT - Actualizar un préstamo existente
    @PutMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> updatePrestamo(
            @PathVariable Long id, @RequestBody PrestamoRequestDTO dto) {
        return ResponseEntity.ok(prestamoService.updatePrestamo(id, dto));
    }

    // PUT - Marcar un préstamo como devuelto
    @PutMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> marcarComoDevuelto(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.marcarComoDevuelto(id));
    }

    // DELETE - Eliminar un préstamo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrestamo(@PathVariable Long id) {
        prestamoService.deletePrestamo(id);
        return ResponseEntity.noContent().build();
    }
}


