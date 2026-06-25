package pe.edu.utp.vacunacioncard.service.comun;

import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para el registro técnico y control de la Auditoría del sistema.
 */
public interface IRegistroAuditoriaService {

    /**
     * Registra un evento o acción de usuario en la base de datos de auditoría con control de errores.
     * @param auditoria El objeto con los metadatos de la acción a persistir.
     * @return El {@link RegistroAuditoria} guardado, o null si ocurre una anomalía.
     */
    RegistroAuditoria registrarAccion(RegistroAuditoria auditoria);

    /**
     * Obtiene el listado completo e histórico de auditoría del sistema.
     * @return Lista de objetos {@link RegistroAuditoria}.
     */
    List<RegistroAuditoria> listarTodos();

    /**
     * Recupera una traza de auditoría específica mediante su identificador único.
     * @param id El identificador único del registro.
     * @return Un {@link Optional} que contiene la fila de auditoría si es hallada.
     */
    Optional<RegistroAuditoria> obtenerPorId(Long id);

    /**
     * Extrae las trazas de auditoría vinculadas a un operador o usuario específico.
     * @param usuarioId Identificador único del usuario operador.
     * @return Lista de objetos {@link RegistroAuditoria}.
     */
    List<RegistroAuditoria> listarPorUsuario(Long usuarioId);

    /**
     * Filtra el historial según la estructura o tabla del sistema afectada por la transacción.
     * @param entidad Nombre de la entidad (ej: "Vacuna").
     * @return Lista de objetos {@link RegistroAuditoria}.
     */
    List<RegistroAuditoria> listarPorEntidadAfectada(String entidad);
}
