package pe.edu.utp.vacunacioncard.service.comun.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;
import pe.edu.utp.vacunacioncard.repository.comun.RegistroAuditoriaRepository;
import pe.edu.utp.vacunacioncard.service.comun.IRegistroAuditoriaService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de registros de auditoría del sistema.
 * Proporciona el soporte de persistencia y consulta para realizar la trazabilidad de 
 * las acciones ejecutadas por los usuarios y los cambios en las entidades del negocio.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegistroAuditoriaServiceImpl implements IRegistroAuditoriaService {

    private final RegistroAuditoriaRepository repo;

    /**
     * Inserta de forma persistente una nueva traza o acción de auditoría en el sistema.
     *
     * @param auditoria Entidad {@link RegistroAuditoria} que detalla la acción, el usuario y la fecha.
     * @return La entidad {@link RegistroAuditoria} almacenada con su identificador único asignado.
     * @throws ServiceException Si ocurre un error de persistencia o acceso a la base de datos al guardar.
     */
    @Override
    @Transactional
    public RegistroAuditoria registrarAccion(RegistroAuditoria auditoria) {
        log.info("Registrando acción de auditoría: {}", auditoria.getAccion());
        try {
            RegistroAuditoria guardado = repo.save(auditoria);
            log.info("Auditoría registrada con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar auditoría: " + auditoria.getAccion(), e);
        }
    }

    /**
     * Recupera un historial completo de todas las trazas de auditoría registradas en la aplicación.
     *
     * @return {@link List} que contiene la totalidad de las entidades {@link RegistroAuditoria}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> listarTodos() {
        log.info("Listando todos los registros de auditoría");
        return repo.findAll();
    }

    /**
     * Busca una traza de auditoría específica utilizando su identificador único.
     *
     * @param id Identificador único del registro de auditoría.
     * @return Un {@link Optional} que envuelve el {@link RegistroAuditoria} si es hallado, 
     *         o un contenedor vacío si no existe correspondencia.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroAuditoria> obtenerPorId(Long id) {
        log.info("Buscando auditoría por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Filtra e identifica las operaciones y acciones efectuadas por un usuario específico.
     *
     * @param usuarioId Identificador único del usuario responsable de las acciones.
     * @return {@link List} de entidades {@link RegistroAuditoria} vinculadas al usuario consultado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> listarPorUsuario(Long usuarioId) {
        log.info("Listando auditoría del usuario ID: {}", usuarioId);
        return repo.findByUsuarioId(usuarioId);
    }

    /**
     * Filtra los registros de auditoría según la tabla o entidad del sistema que sufrió modificaciones.
     * Este método realiza la consulta en la base de datos ignorando la distinción entre 
     * mayúsculas y minúsculas (Case Insensitive).
     *
     * @param entidad Nombre representativo de la clase o tabla afectada (ej. "Paciente", "CitaVacunacion").
     * @return {@link List} de entidades {@link RegistroAuditoria} que corresponden al criterio ingresado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> listarPorEntidadAfectada(String entidad) {
        log.info("Listando auditoría por entidad: {}", entidad);
        return repo.findByEntidadAfectadaIgnoreCase(entidad);
    }
}
