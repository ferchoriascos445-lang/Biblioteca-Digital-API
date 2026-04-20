package paredes.software.bibliotecadigital.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import paredes.software.bibliotecadigital.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    //Metodo para buscar un préstamo por usuario
    List<Prestamo> findByUsuarioId(Long usuarioId);

    //Metodo para buscar un préstamo por libro
    List<Prestamo> findByLibroId(Long libroId);

    //Metodo de prestamos por estado (Activo, Devuelto, Vencido)
    List<Prestamo> findByEstado(Prestamo.EstadoPrestamo estado);

    //Metodo para buscar préstamos activos por usuario
    List<Prestamo> findByUsuarioIdAndEstado(Long usuarioId, Prestamo.EstadoPrestamo estado);

    //Metodo para verificar si un libro está prestado (estado activo)
    boolean existsByLibroIdAndEstado(Long libroId, Prestamo.EstadoPrestamo estado);

    //Metodo para buscar préstamos por rango de fechas
    List<Prestamo> findByFechaPrestamoBetween(LocalDate inicio, LocalDate fin);

    //Metodo para buscar préstamos vencidos (fecha de devolucion pasada y estado activo)
    List<Prestamo> findByFechaDevolucionBeforeAndEstado(LocalDate fecha, Prestamo.EstadoPrestamo estado);
}
