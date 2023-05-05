package pe.com.scotiabank.alumno.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import pe.com.scotiabank.alumno.api.constant.Constant;
import pe.com.scotiabank.alumno.api.model.response.Response;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class ResponseHandlerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Mono<Response>> handleException(Exception ex) {
        log.info("Mensaje de error: {}", ex.getMessage());
        log.info("Error en ejecucion: {}", ex.getStackTrace());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Mono.just(Response.responseHandle(Constant.CODE_500, Constant.MESSAGE_500)));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Mono<Response>> handleValid(WebExchangeBindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder message = new StringBuilder("Errores: [ ");
        bindingResult.getFieldErrors().forEach(e -> message.append(e.getField())
                .append(": El campo ")
                .append(e.getDefaultMessage())
                .append(", "));
        log.info("Mensaje de error: {}", message.toString().replace(", ", " ]."));
        log.info("Error en ejecucion: {}", ex.getStackTrace());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Mono.just(Response.responseHandle(Constant.CODE_400,message.toString().replace(", ", " ]."))));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Mono<Response>> handleDuplicateId(DataIntegrityViolationException ex) {
        log.info("Mensaje de error: {}", ex.getMessage());
        log.info("Error en ejecucion: {}", ex.getStackTrace());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Mono.just(Response.responseHandle(Constant.CODE_409, Constant.MESSAGE_409)));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Mono<Response>> handleBadRequest(BadRequestException ex) {
        log.debug("Mensaje de error: {}", ex.getMessage());
        log.debug("Error en ejecucion: {}", ex.getStackTrace());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Mono.just(Response.responseHandle(Constant.CODE_400, ex.getMessage())));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Mono<Response>> handleBadRequest(NotFoundException ex) {
        log.debug("Mensaje de error: {}", ex.getMessage());
        log.debug("Error en ejecucion: {}", ex.getStackTrace());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Mono.just(Response.responseHandle(Constant.CODE_400, ex.getMessage())));
    }

}
