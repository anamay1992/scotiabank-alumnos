package pe.com.scotiabank.alumno.api.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.com.scotiabank.alumno.api.model.entity.Alumno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoRepository extends ReactiveCrudRepository<Alumno, Long> {

    @Query("SELECT * FROM alumno WHERE estado = :estado")
    Flux<Alumno> findAllByEstado(int estado);

    Mono<Alumno> findByDni(String dni);

}
