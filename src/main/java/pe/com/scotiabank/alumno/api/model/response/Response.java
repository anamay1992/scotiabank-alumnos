package pe.com.scotiabank.alumno.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.scotiabank.alumno.api.constant.Constant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Clase que representa al Response genérico base.")
public class Response<T> extends BaseResponse {

    @Schema(description = "Objeto genérico enviado por el Response.", example = "Alumno")
    public T data;

    public static <T> Response<T> responseOk(T data) {
        Response<T> response = new Response<>();
        response.setCode(Constant.CODE_200);
        response.setMessage(Constant.MESSAGE_200);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> responseCreate(T data) {
        Response<T> response = new Response<>();
        response.setCode(Constant.CODE_201);
        response.setMessage(Constant.MESSAGE_201);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> responseDelete(T data) {
        Response<T> response = new Response<>();
        response.setCode(Constant.CODE_204);
        response.setMessage(Constant.MESSAGE_204);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> responseHandle(String code, String message) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

}
