package paredes.software.bibliotecadigital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroResponseDTO {

    private Long id;
    private String isbn;
    private String titulo;
    private int anioPublicacion;
    private AutorResponseDTO autor; // Relación con AutorResponseDTO
    private List<GeneroResponseDTO> generos; // Relación con GeneroResponseDTO
}