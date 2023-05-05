package pe.com.scotiabank.alumno.api.exception;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@Getter
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    private Map<String, String> errors = new HashMap<>();

    public BadRequestException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

}
