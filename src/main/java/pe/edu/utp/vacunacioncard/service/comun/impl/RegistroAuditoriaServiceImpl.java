package pe.edu.utp.vacunacioncard.service.comun.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;
import pe.edu.utp.vacunacioncard.repository.comun.RegistroAuditoriaRepository;
import pe.edu.utp.vacunacioncard.service.comun.IRegistroAuditoriaService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para el registro sistemático de operaciones de auditoría.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegistroAuditoriaServiceImpl implements IRegistroAuditoriaService {

    private final RegistroAuditoriaRepository repo;

    /***
     * Registra una acción de auditoría en la base de datos con control transaccional y manejo de excepciones.
     * @param auditoria Objeto {@link RegistroAuditoria} que contiene la información de
     * @return El objeto {@link RegistroAuditoria} persistido o null si ocurre un error crítico.
     */
    @Override
    @Transactional
    public RegistroAuditoria registrarAccion(RegistroAuditoria auditoria) {
        log.info("Insertando nueva traza de auditoría para la acción: {}", auditoria.getAccion());
        try {
            RegistroAuditoria guardado = repo.save(auditoria);
            log.info("Registro de auditoría grabado con éxito bajo el ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            log.error("Error crítico no controlado al persistir bitácora de auditoría: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Obtiene el listado completo de auditoría del sistema con control de lectura.
     * @return Lista de objetos {@link RegistroAuditoria}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> listarTodos() {
        log.info("Solicitando consulta completa del historial de auditoría global");
        return repo.findAll();
    }

    /***
     * Recupera una traza de auditoría específica mediante su identificador único.
     * @param id El identificador único del registro.
     * @return Un {@link Optional} que contiene la fila de auditoría si es hallada.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroAuditoria> obtenerPorId(Long id) {
        log.info("Buscando traza de auditoría por clave primaria ID: {}", id);
        return repo.findById(id);
    }

    /***
     * Extrae las trazas de auditoría vinculadas a un operador o usuario específico.
     * @param usuarioId Identificador único del usuario operador.
     * @return Lista de objetos {@link RegistroAuditoria}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> listarPorUsuario(Long usuarioId) {
        log.info("Consultando historial de auditoría asociado al usuario ID: {}", usuarioId);
        return repo.findByUsuarioId(usuarioId);
    }

    /***
     * Filtra el historial según la estructura o tabla del sistema afectada por la transacción.
     * @param entidad Nombre de la entidad (ej: "Vacuna").
     * @return Lista de objetos {@link RegistroAuditoria}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroAuditoria> listarPorEntidadAfectada(String entidad) {
        log.info("Filtrando registros de auditoría sobre la estructura de datos: {}", entidad);
        return repo.findByEntidadAfectadaIgnoreCase(entidad);
    }
}
