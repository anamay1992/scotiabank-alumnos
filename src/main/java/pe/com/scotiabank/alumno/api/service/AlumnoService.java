package pe.com.scotiabank.alumno.api.service;

import pe.com.scotiabank.alumno.api.model.entity.Alumno;
import pe.com.scotiabank.alumno.api.model.response.Response;
import pe.com.scotiabank.alumno.api.model.response.ResponseList;
import reactor.core.publisher.Mono;

public interface AlumnoService {

    Mono<ResponseList<Alumno>>  findAllFilter();
    Mono<ResponseList<Alumno>>  findAllQuery();
    Mono<Response<Alumno>> findById(Long id);
    Mono<Response<Alumno>> findByDni(String dni);
    Mono<Response<Alumno>> save(Alumno alumno);
    Mono<Response<Alumno>> updateById(Long id, Alumno alumno);
    Mono<Response<Alumno>> deleteById(Long id);

}
