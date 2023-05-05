package pe.com.scotiabank.alumno.api.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.com.scotiabank.alumno.api.model.entity.Alumno;
import pe.com.scotiabank.alumno.api.model.response.Response;
import pe.com.scotiabank.alumno.api.model.response.ResponseList;
import pe.com.scotiabank.alumno.api.repository.AlumnoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AlumnoServiceImplTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        alumnoService = new AlumnoServiceImpl(alumnoRepository);
    }

    @Test
    public void testFindAllFilter() {
        List<Alumno> alumnos = Arrays.asList(
                new Alumno("12345678", "Angel Leonard", "Namay Cabanillas", 30, 1),
                new Alumno("12345679", "Angel Leonard", "Namay Cabanillas", 30, 1)
        );
        when(alumnoRepository.findAll()).thenReturn(Flux.fromIterable(alumnos));
        Mono<ResponseList<Alumno>> response = alumnoService.findAllFilter();
        StepVerifier.create(response)
                .assertNext(respuesta -> {
                    assertEquals(ResponseList.responseOk(alumnos), respuesta);
                    verify(alumnoRepository, timeout(1)).findAll();
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
        when(alumnoRepository.findAllByEstado(1)).thenReturn(Flux.fromIterable(alumnos));
        Mono<ResponseList<Alumno>> response = alumnoService.findAllQuery();
        StepVerifier.create(response)
                .assertNext(respuesta -> {
                    assertEquals(ResponseList.responseOk(alumnos), respuesta);
                    verify(alumnoRepository, timeout(1)).findAllByEstado(1);
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testSave() {
        Alumno alumno = new Alumno("12345688", "Angel Leonard", "Namay Cabanillas", 30, 1);
        when(alumnoRepository.save(alumno)).thenReturn(Mono.just(alumno));
        Mono<Response<Alumno>> response = alumnoService.save(alumno);
        StepVerifier.create(response)
                .assertNext(respuesta -> {
                    assertEquals(Response.responseCreate(alumno), respuesta);
                    verify(alumnoRepository, timeout(1)).save(alumno);
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindById() {
        Alumno alumno = new Alumno(1L, "12345678", "Angel Leonard", "Namay Cabanillas", 30, 1);
        when(alumnoRepository.findById(1L)).thenReturn(Mono.just(alumno));
        Mono<Response<Alumno>> response = alumnoService.findById(1L);
        StepVerifier.create(response)
                .assertNext(respuesta -> {
                    assertEquals(Response.responseOk(alumno), respuesta);
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindByDni() {
        Alumno alumno = new Alumno(1L, "12345678", "Angel Leonard", "Namay Cabanillas", 30, 1);
        when(alumnoRepository.findByDni("12345678")).thenReturn(Mono.just(alumno));
        Mono<Response<Alumno>> response = alumnoService.findByDni("12345678");
        StepVerifier.create(response)
                .assertNext(respuesta -> {
                    assertEquals(Response.responseOk(alumno), respuesta);
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testUpdateById() {
        Alumno alumno = new Alumno(1L, "12345678", "Angel Leonard", "Namay Cabanillas", 30, 1);
        Alumno alumnoActualizado = new Alumno(1L, "12345678", "Angel Leonardo", "Namay Cabanillas", 30, 1);
        when(alumnoRepository.findById(1L)).thenReturn(Mono.just(alumno));
        when(alumnoRepository.save(alumnoActualizado)).thenReturn(Mono.just(alumnoActualizado));
        Mono<Response<Alumno>> response = alumnoService.updateById(1L, alumnoActualizado);
        StepVerifier.create(response)
                .assertNext(respuesta -> {
                    assertEquals(Response.responseOk(alumnoActualizado), respuesta);
                    verify(alumnoRepository, times(1)).findById(1L);
                    verify(alumnoRepository, times(1)).save(alumnoActualizado);
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testDeleteById() {
        Alumno alumno = new Alumno(1L, "12345678", "Angel Leonard", "Namay Cabanillas", 30, 1);
        when(alumnoRepository.findById(1L)).thenReturn(Mono.just(alumno));
        when(alumnoRepository.delete(alumno)).thenReturn(Mono.empty());
        Mono<Response<Alumno>> response = alumnoService.deleteById(1L);
        StepVerifier.create(response)
                .assertNext(respuesta -> {
                    assertEquals(Response.responseDelete(alumno), respuesta);
                    verify(alumnoRepository, times(1)).findById(1L);
                    verify(alumnoRepository, times(1)).delete(alumno);
                })
                .expectComplete()
                .verify();
    }

}