package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion;
import pe.edu.utp.vacunacioncard.repository.vacunacion.EsquemaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.IEsquemaVacunacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de Esquemas de Vacunación.
 * Controla la lógica de negocio asociada a la planificación de cronogramas sanitarios,
 * permitiendo estructurar las dosis obligatorias y opcionales asignadas a cada paciente.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EsquemaVacunacionServiceImpl implements IEsquemaVacunacionService {

    private final EsquemaVacunacionRepository repo;

    /**
     * Recupera un listado completo de todos los esquemas de vacunación registrados en el sistema.
     *
     * @return {@link List} que contiene la totalidad de las entidades {@link EsquemaVacunacion}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EsquemaVacunacion> listarTodos() {
        log.info("Listando todos los esquemas de vacunación");
        return repo.findAll();
    }

    /**
     * Busca la ficha informativa de un esquema de vacunación mediante su identificador único.
     *
     * @param id Identificador único del esquema médico.
     * @return Un {@link Optional} con el {@link EsquemaVacunacion} si se encuentra en el repositorio,
     *         o un contenedor vacío si no existe el registro.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EsquemaVacunacion> obtenerPorId(Long id) {
        log.info("Buscando esquema por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra un nuevo esquema médico o actualiza la planificación existente para un paciente.
     *
     * @param esquema Entidad {@link EsquemaVacunacion} con el cronograma y datos de control.
     * @return La entidad {@link EsquemaVacunacion} guardada con su identificador único generado.
     * @throws ServiceException Si ocurre una anomalía de persistencia o fallo de conectividad con la base de datos.
     */
    @Override
    @Transactional
    public EsquemaVacunacion guardar(EsquemaVacunacion esquema) {
        log.info("Guardando esquema del paciente ID: {}",
                esquema.getPacienteAsignado() != null ? esquema.getPacienteAsignado().getId() : "N/A");
        try {
            EsquemaVacunacion guardado = repo.save(esquema);
            log.info("Esquema guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar esquema de vacunación", e);
        }
    }

    /**
     * Filtra e identifica los esquemas y calendarios médicos asociados a un único ciudadano.
     *
     * @param pacienteId Identificador único del paciente bajo seguimiento médico.
     * @return {@link List} de entidades {@link EsquemaVacunacion} vinculadas al ID del paciente.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EsquemaVacunacion> listarPorPaciente(Long pacienteId) {
        log.info("Listando esquemas del paciente ID: {}", pacienteId);
        return repo.findByPacienteAsignadoId(pacienteId);
    }

    /**
     * Obtiene los esquemas de vacunación agrupados por su estado clínico actual.
     *
     * @param estado Condición del esquema (ej. "COMPLETO", "EN_PROGRESO", "INCOMPLETO").
     * @return {@link List} de entidades {@link EsquemaVacunacion} que comparten la condición enviada.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EsquemaVacunacion> listarPorEstado(String estado) {
        log.info("Listando esquemas por estado: {}", estado);
        return repo.findByEstadoIgnoreCase(estado);
    }

    /**
     * Remueve de forma permanente un esquema de vacunación del sistema mediante su ID.
     *
     * @param id Identificador único del esquema que se desea eliminar.
     * @throws ServiceException Si ocurre un error de persistencia o restricción de integridad referencial.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando esquema de vacunación con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Esquema eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar esquema con ID: " + id, e);
        }
    }
}
