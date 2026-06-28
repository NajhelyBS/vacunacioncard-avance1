package pe.edu.utp.vacunacioncard.service.cita.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import pe.edu.utp.vacunacioncard.repository.cita.CitaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.cita.ICitaVacunacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de citas de vacunación.
 * Controla el ciclo de vida de las citas (programación, cancelación) y provee
 * consultas filtradas por pacientes o estados del proceso de inmunización.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CitaVacunacionServiceImpl implements ICitaVacunacionService {

    private final CitaVacunacionRepository repo;

    /**
     * Recupera un listado global con todas las citas de vacunación registradas.
     *
     * @return {@link List} que contiene todas las entidades {@link CitaVacunacion}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> listarTodas() {
        log.info("Listando todas las citas de vacunación");
        return repo.findAll();
    }

    /**
     * Busca el detalle de una cita de vacunación mediante su identificador único.
     *
     * @param id Identificador único de la cita.
     * @return Un {@link Optional} con la {@link CitaVacunacion} si existe,
     *         o vacío en caso contrario.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CitaVacunacion> obtenerPorId(Long id) {
        log.info("Buscando cita por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra y programa una nueva cita de vacunación en el sistema.
     *
     * @param cita Entidad {@link CitaVacunacion} con los datos de fecha, hora, paciente y vacuna.
     * @return La entidad {@link CitaVacunacion} guardada con su respectivo ID generado.
     * @throws ServiceException Si se presenta un inconveniente de acceso a datos al programar la cita.
     */
    @Override
    @Transactional
    public CitaVacunacion programar(CitaVacunacion cita) {
        log.info("Programando cita para paciente ID: {}",
                cita.getPaciente() != null ? cita.getPaciente().getId() : "N/A");
        try {
            CitaVacunacion guardada = repo.save(cita);
            log.info("Cita programada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al programar cita de vacunación", e);
        }
    }

    /**
     * Obtiene el historial cronológico de citas correspondientes a un único paciente.
     *
     * @param pacienteId Identificador único del paciente asociado.
     * @return {@link List} de entidades {@link CitaVacunacion} vinculadas al paciente.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> listarPorPaciente(Long pacienteId) {
        log.info("Listando citas del paciente ID: {}", pacienteId);
        return repo.findByPacienteId(pacienteId);
    }

    /**
     * Recupera las citas que coinciden con un estado operativo específico.
     *
     * @param estado Estado de la cita (ej. "PENDIENTE", "ATENDIDA", "CANCELADA").
     * @return {@link List} de entidades {@link CitaVacunacion} que comparten dicho estado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> listarPorEstado(String estado) {
        log.info("Listando citas por estado: {}", estado);
        return repo.findByEstado(estado);
    }

    /**
     * Elimina del registro una cita de vacunación mediante su ID.
     *
     * @param id Identificador único de la cita a remover.
     * @throws ServiceException Si ocurre una excepción de persistencia o restricción de base de datos.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando cita con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Cita eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar cita con ID: " + id, e);
        }
    }
}
