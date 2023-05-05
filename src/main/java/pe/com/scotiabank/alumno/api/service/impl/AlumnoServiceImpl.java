package pe.com.scotiabank.alumno.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.scotiabank.alumno.api.constant.Constant;
import pe.com.scotiabank.alumno.api.exception.NotFoundException;
import pe.com.scotiabank.alumno.api.model.entity.Alumno;
import pe.com.scotiabank.alumno.api.model.response.Response;
import pe.com.scotiabank.alumno.api.model.response.ResponseList;
import pe.com.scotiabank.alumno.api.repository.AlumnoRepository;
import pe.com.scotiabank.alumno.api.service.AlumnoService;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    @Autowired
    public AlumnoServiceImpl(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ResponseList<Alumno>> findAllFilter() {
        return alumnoRepository.findAll()
                .filter(alumno -> alumno.getEstado() == 1)
                .collectList()
                .map(alumnos -> ResponseList.responseOk(alumnos))
                .doOnSuccess(respuesta -> log.debug("respuesta: {}", respuesta));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ResponseList<Alumno>> findAllQuery() {
        return alumnoRepository.findAllByEstado(1)
                .collectList()
                .map(alumnos -> ResponseList.responseOk(alumnos))
                .doOnSuccess(respuesta -> log.info("respuesta: {}", respuesta));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Response<Alumno>> findById(Long id) {
        return alumnoRepository.findById(id)
                .map(alumnoEncontrado -> Response.responseOk(alumnoEncontrado))
                .switchIfEmpty(Mono.just(Response.responseHandle(Constant.CODE_404, Constant.MESSAGE_404)))
                .doOnSuccess(respuesta -> log.info("respuesta: {}", respuesta));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Response<Alumno>> findByDni(String dni) {
        return alumnoRepository.findByDni(dni)
                .map(alumnoEncontrado -> Response.responseOk(alumnoEncontrado))
                .switchIfEmpty(Mono.just(Response.responseHandle(Constant.CODE_404, Constant.MESSAGE_404)))
                .doOnSuccess(respuesta -> log.info("respuesta: {}", respuesta));
    }

    @Override
    @Transactional
    public Mono<Response<Alumno>> save(Alumno alumno) {
        return alumnoRepository.save(alumno)
                .map(alumnoGuardado -> Response.responseCreate(alumnoGuardado))
                .doOnSuccess(respuesta -> log.info("respuesta: {}", respuesta));
    }

    @Override
    @Transactional
    public Mono<Response<Alumno>> updateById(Long id, Alumno alumno) {
        return alumnoRepository.findById(id)
                .flatMap(alumnoEncontrado -> {
                    BeanUtils.copyProperties(alumno, alumnoEncontrado, "id");
                    return alumnoRepository.save(alumnoEncontrado);
                })
                .map(alumnoActualizado -> Response.responseCreate(alumnoActualizado))
                .switchIfEmpty(Mono.error(new NotFoundException(Constant.MESSAGE_404)));
    }

    @Override
    @Transactional
    public Mono<Response<Alumno>> deleteById(Long id) {
        return alumnoRepository.findById(id)
                .flatMap(alumnoEncontrado -> alumnoRepository.delete(alumnoEncontrado)
                        .then(Mono.just(Response.responseDelete(alumnoEncontrado))))
                .switchIfEmpty(Mono.error(new NotFoundException(Constant.MESSAGE_404)));
    }

}
