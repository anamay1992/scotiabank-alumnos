package pe.com.scotiabank.alumno.api.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pe.com.scotiabank.alumno.api.model.entity.Alumno;
import pe.com.scotiabank.alumno.api.model.response.Response;
import pe.com.scotiabank.alumno.api.model.response.ResponseList;
import pe.com.scotiabank.alumno.api.service.AlumnoService;
import pe.com.scotiabank.alumno.api.service.impl.AlumnoServiceImpl;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ComponentScan(basePackages = "paquete.donde.esta.AlumnoServiceImpl")
@WebFluxTest(controllers = AlumnoController.class)
public class AlumnoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AlumnoService alumnoService;

    @InjectMocks
    private AlumnoController alumnoController;

    @Test
    public void testFindAllFilter() {
        List<Alumno> alumnos = Arrays.asList(
                new Alumno("12345678", "Angel Leonard", "Namay Cabanillas", 30, 1),
                new Alumno("12345679", "Angel Leonard", "Namay Cabanillas", 30, 1)
        );
        when(alumnoService.findAllFilter()).thenReturn(Mono.just(ResponseList.responseOk(alumnos)));
        Mono<ResponseList<Alumno>> response = webTestClient.get().uri("/api/alumnos/filter")
                .exchange()
                .expectStatus().isOk()
                .returnResult(new ParameterizedTypeReference<ResponseList<Alumno>>() {})
                .getResponseBody()
                .single();
        StepVerifier.create(response)
                .assertNext(respuesta -> {
                    assertEquals("200", respuesta.getCode());
                    assertEquals("Información obtenida correctamente.", respuesta.getMessage());
                    assertEquals(alumnos, respuesta.getData());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindAllQuery() {
        List<Alumno> alumnos = Arrays.asList(
                new Alumno("12345678", "Angel Leonard", "Namay Cabanillas", 30, 1),
                new Alumno("12345679", "Angel Leonard", "Namay Cabanillas", 30, 1)
        );
        when(alumnoService.findAllQuery()).thenReturn(Mono.just(ResponseList.responseOk(alumnos)));
        Mono<ResponseList<Alumno>> response = webTestClient.get().uri("/api/alumnos/query")
                .exchange()
                .expectStatus().isOk()
                .returnResult(new ParameterizedTypeReference<ResponseList<Alumno>>() {})
                .getResponseBody()
                .single();
        StepVerifier.create(response)
                .assertNext(respuesta -> {
                    assertEquals("200", respuesta.getCode());
                    assertEquals("Información obtenida correctamente.", respuesta.getMessage());
                    assertEquals(alumnos, respuesta.getData());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testSave() {
        Alumno alumno = new Alumno("12345688", "Angel Leonard", "Namay Cabanillas", 30, 1);
        when(alumnoService.save(alumno)).thenReturn(Mono.just(Response.responseCreate(alumno)));
        webTestClient.post().uri("/api/alumnos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(alumno)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.code").isEqualTo("201")
                .jsonPath("$.message").isEqualTo("Se guardo la información correctamente.")
                .jsonPath("$.data.id").isEqualTo(alumno.getId());
    }

}