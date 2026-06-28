package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.repository.usuario.EnfermeroRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IEnfermeroService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión del personal de enfermería.
 * Administra la lógica de negocio operativa del personal asistencial encargado de
 * las inoculaciones, validando sus números de colegiatura y asignación de sedes.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EnfermeroServiceImpl implements IEnfermeroService {

    private final EnfermeroRepository repo;

    /**
     * Recupera un listado completo de todos los enfermeros registrados en la base de datos.
     *
     * @return {@link List} que aloja a todas las entidades {@link Enfermero}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Enfermero> listarTodos() {
        log.info("Listando todos los enfermeros");
        return repo.findAll();
    }

    /**
     * Busca el registro detallado de un enfermero mediante su identificador único del sistema.
     *
     * @param id Identificador único del enfermero.
     * @return Un {@link Optional} que contiene al {@link Enfermero} si se encuentra,
     *         o un contenedor vacío si no existen registros.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Enfermero> obtenerPorId(Long id) {
        log.info("Buscando enfermero por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra un nuevo enfermero en el sistema o actualiza la ficha del personal asistente.
     *
     * @param enfermero Entidad {@link Enfermero} con la información médica y colegiatura a persistir.
     * @return La entidad {@link Enfermero} guardada con su identificador único autogenerado.
     * @throws ServiceException Si ocurre una anomalía de acceso a datos durante la persistencia.
     */
    @Override
    @Transactional
    public Enfermero registrar(Enfermero enfermero) {
        log.info("Registrando enfermero con colegiatura: {}", enfermero.getColegiatura());
        try {
            Enfermero guardado = repo.save(enfermero);
            log.info("Enfermero registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar enfermero", e);
        }
    }

    /**
     * Busca a un enfermero específico utilizando su número único de colegiatura profesional.
     *
     * @param colegiatura Código único del Colegio de Enfermeros del Perú (CEP).
     * @return Un {@link Optional} que envuelve al {@link Enfermero} si existe la coincidencia,
     *         o un contenedor vacío si no está registrado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Enfermero> obtenerPorColegiatura(String colegiatura) {
        log.info("Buscando enfermero por colegiatura: {}", colegiatura);
        return repo.findByColegiatura(colegiatura);
    }

    /**
     * Filtra y obtiene al personal de enfermería asignado a una sede o centro de salud específico.
     *
     * @param centroTrabajo Nombre del establecimiento de salud o centro de vacunación.
     * @return {@link List} de entidades {@link Enfermero} operativas en la sede enviada.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Enfermero> listarPorCentroTrabajo(String centroTrabajo) {
        log.info("Listando enfermeros por centro: {}", centroTrabajo);
        return repo.findByCentroTrabajoIgnoreCase(centroTrabajo);
    }
}
