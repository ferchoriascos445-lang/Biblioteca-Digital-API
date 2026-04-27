package paredes.software.bibliotecadigital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorRequestDTO {

    private String nombre;
    private String apellido;
    private String nacionalidad;
}