package pe.edu.utp.vacunacioncard.service.cita.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import pe.edu.utp.vacunacioncard.repository.cita.HorarioRepository;
import pe.edu.utp.vacunacioncard.service.cita.IHorarioService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de los horarios de atención.
 * Controla la lógica de negocio para establecer los bloques horarios disponibles,
 * consultas por días específicos de la semana y su persistencia en el sistema.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HorarioServiceImpl implements IHorarioService {

    private final HorarioRepository repo;

    /**
     * Recupera una lista con todos los bloques de horarios registrados en el sistema.
     *
     * @return {@link List} que contiene todas las entidades {@link Horario}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Horario> listarTodos() {
        log.info("Listando todos los horarios");
        return repo.findAll();
    }

    /**
     * Busca un bloque de horario específico mediante su identificador único.
     *
     * @param id Identificador único del horario.
     * @return Un {@link Optional} con el {@link Horario} si se encuentra en la base de datos,
     *         o un contenedor vacío si no existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Horario> obtenerPorId(Long id) {
        log.info("Buscando horario por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra un nuevo horario de atención o actualiza un registro existente.
     *
     * @param horario Entidad {@link Horario} que contiene los datos del bloque y su día de la semana.
     * @return La entidad {@link Horario} guardada con su identificador único asignado.
     * @throws ServiceException Si se presenta un error de persistencia o acceso a datos al guardar.
     */
    @Override
    @Transactional
    public Horario guardar(Horario horario) {
        log.info("Guardando horario para día: {}", horario.getDiaSemana());
        try {
            Horario guardado = repo.save(horario);
            log.info("Horario guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar horario", e);
        }
    }

    /**
     * Filtra y obtiene los bloques de horarios asociados a un día de la semana en específico.
     *
     * @param diaSemana Enumerado {@link DiaSemana} que representa el día a consultar (ej. LUNES, MARTES).
     * @return {@link List} de entidades {@link Horario} asignadas al día seleccionado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Horario> listarPorDia(DiaSemana diaSemana) {
        log.info("Listando horarios del día: {}", diaSemana);
        return repo.findByDiaSemana(diaSemana);
    }

    /**
     * Elimina del sistema un bloque de horario mediante su identificador único.
     *
     * @param id Identificador único del horario que se desea eliminar.
     * @throws ServiceException Si ocurre una excepción de persistencia o restricción de llave foránea al borrar.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando horario con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Horario eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar horario con ID: " + id, e);
        }
    }
}
