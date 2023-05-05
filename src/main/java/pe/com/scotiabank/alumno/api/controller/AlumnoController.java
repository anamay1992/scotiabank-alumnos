package pe.com.scotiabank.alumno.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.scotiabank.alumno.api.model.entity.Alumno;
import pe.com.scotiabank.alumno.api.model.response.Response;
import pe.com.scotiabank.alumno.api.model.response.ResponseList;
import pe.com.scotiabank.alumno.api.service.AlumnoService;
import reactor.core.publisher.Mono;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/alumnos")
@Tag(name = "Controlador alumno", description = "version 1.0")
public class AlumnoController {

    private final AlumnoService alumnoService;

    @Autowired
    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping("/filter")
    @Operation(summary = "Obtiene una lista de alumnos filtrados por estado activo (1).")
    @ApiResponse(responseCode = "200", description = "Información obtenida correctamente.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ResponseList.class)))
    public ResponseEntity<Mono<ResponseList<Alumno>>> findAllFilter() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alumnoService.findAllFilter());
    }

    @GetMapping("/query")
    @Operation(summary = "Obtiene una lista de alumnos filtrados por estado activo (1) partiendo de un @Query.")
    @ApiResponse(responseCode = "200", description = "Información obtenida correctamente.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ResponseList.class)))
    public ResponseEntity<Mono<ResponseList<Alumno>>> findAllQuery() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alumnoService.findAllQuery());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un alumno por su identificador único.")
    @ApiResponse(responseCode = "200", description = "Información obtenida correctamente.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "La información no fue encontrado.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)))
    public ResponseEntity<Mono<Response<Alumno>>> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alumnoService.findById(id));
    }

    @GetMapping("/{dni}")
    @Operation(summary = "Obtiene un alumno por su dni único.")
    @ApiResponse(responseCode = "200", description = "Información obtenida correctamente.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "La información no fue encontrado.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)))
    public ResponseEntity<Mono<Response<Alumno>>> findByDni(@PathVariable String dni) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alumnoService.findByDni(dni));
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo alumno.")
    @ApiResponse(responseCode = "201", description = "Se guardo la información correctamente.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "409", description = "El dni ingresado no puede registrarse debido a duplicidad.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)))
    public ResponseEntity<Mono<Response<Alumno>>> save(@Valid @RequestBody Alumno alumno) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alumnoService.save(alumno));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza los datos de un alumno existente por su identificador único.")
    @ApiResponse(responseCode = "201", description = "Se guardo la información correctamente.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "La información no fue encontrado.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)))
    public ResponseEntity<Mono<Response<Alumno>>> updateById(@PathVariable Long id,
                                                         @Valid @RequestBody Alumno alumno) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alumnoService.updateById(id, alumno));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un alumno existente por su identificador único.")
    @ApiResponse(responseCode = "204", description = "Se elimino la información correctamente.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "La información no fue encontrado.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)))
    public ResponseEntity<Mono<Response<Alumno>>> delete(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alumnoService.deleteById(id));
    }

}
