package paredes.software.bibliotecadigital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import paredes.software.bibliotecadigital.model.Prestamo;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoResponseDTO {

    private Long id;

    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    // 🔹 Usamos String para desacoplar del enum de la entidad
    private Prestamo.EstadoPrestamo estado;

    // 🔹 Información resumida del usuario (evita sobrecarga)
    private Long usuarioId;
    private String nombreUsuario;

    // 🔹 Información resumida del libro (evita objetos pesados)
    private Long libroId;
    private String tituloLibro;
}