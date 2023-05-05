package pe.com.scotiabank.alumno.api.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Table(name = "alumno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Clase que representa un alumno.")
public class Alumno implements Serializable {

    @Id
    @Schema(description = "El identificador único del alumno.", example = "1")
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 8)
    @Schema(description = "El dni único del alumno.", required = true, example = "12345678")
    private String dni;

    @NotEmpty
    @Schema(description = "El nombre del alumno.", required = true, example = "Angel Leonard")
    private String nombre;

    @NotEmpty
    @Schema(description = "El apellido del alumno.", required = true, example = "Namay Cabanillas")
    private String apellido;

    @Positive
    @Schema(description = "La edad del alumno.", required = true, example = "30")
    private int edad;

    @Schema(description = "El estado del alumno.", example = "1")
    private int estado = 1;

    public Alumno(String dni, String nombre, String apellido, int edad, int estado) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.estado = estado;
    }

    private static final long serialVersionUID = 1L;

}
