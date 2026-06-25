package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión operativa y registro del Personal de Enfermería.
 */
public interface IEnfermeroService {

    /**
     * Obtiene una lista global con todos los profesionales de enfermería registrados.
     * @return Lista de objetos {@link Enfermero}.
     */
    List<Enfermero> listarTodos();

    /**
     * Recupera el registro de un enfermero específico mediante su identificador único de base de datos.
     * @param id El identificador único de la cuenta de enfermero.
     * @return Un {@link Optional} que contiene al enfermero si es hallado.
     */
    Optional<Enfermero> obtenerPorId(Long id);

    /**
     * Registra un nuevo enfermero en el sistema con control estricto de persistencia y errores.
     * @param enfermero El objeto enfermero con los datos profesionales a guardar.
     * @return El {@link Enfermero} guardado con éxito, o null si ocurre una anomalía.
     */
    Enfermero registrar(Enfermero enfermero);

    /**
     * Localiza a un profesional de salud mediante su credencial o código de colegiatura.
     * @param colegiatura Número de registro profesional.
     * @return Un {@link Optional} con el resultado de la búsqueda.
     */
    Optional<Enfermero> obtenerPorColegiatura(String colegiatura);

    /**
     * Agrupa y lista al personal de enfermería según su centro médico o de vacunación asignado.
     * @param centroTrabajo Nombre del local asistencial.
     * @return Lista de objetos {@link Enfermero}.
     */
    List<Enfermero> listarPorCentroTrabajo(String centroTrabajo);
}
