package pe.com.scotiabank.alumno.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse {

    @Schema(description = "Codigo de respuesta.", example = "200")
    public String code;

    @Schema(description = "Mensaje de respuesta.", example = "Ok")
    private String message;

}
