package pe.com.scotiabank.alumno.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.scotiabank.alumno.api.constant.Constant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Clase que representa al ResponseList genérico base.")
public class ResponseList<T> extends BaseResponse {

    @Schema(description = "Lista de objeto genéricos enviados por el ResponseList.", example = "List<Alumno>")
    private List<T> data;

    public static <T> ResponseList<T> responseOk(List<T> data) {
        ResponseList<T> response = new ResponseList<>();
        response.setCode(Constant.CODE_200);
        response.setMessage(Constant.MESSAGE_200);
        response.setData(data);
        return response;
    }

}
