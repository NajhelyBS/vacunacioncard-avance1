package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para el control, asignación y seguimiento de Esquemas de Vacunación.
 */
public interface IEsquemaVacunacionService {

    /**
     * Obtiene una lista global con todos los esquemas de vacunación registrados en el sistema.
     * @return Lista de objetos {@link EsquemaVacunacion}.
     */
    List<EsquemaVacunacion> listarTodos();

    /**
     * Recupera un registro de esquema específico mediante su identificador único.
     * @param id El identificador único del esquema.
     * @return Un {@link Optional} que contiene el esquema si es hallado.
     */
    Optional<EsquemaVacunacion> obtenerPorId(Long id);

    /**
     * Registra o actualiza un esquema cronológico de vacunación con control de persistencia.
     * @param esquema El objeto esquema con los datos y vacunas requeridas.
     * @return El {@link EsquemaVacunacion} guardado con éxito, o null si ocurre un fallo.
     */
    EsquemaVacunacion guardar(EsquemaVacunacion esquema);

    /**
     * Extrae el consolidado de esquemas médicos asignados al perfil de un paciente.
     * @param pacienteId Identificador único del paciente.
     * @return Lista de objetos {@link EsquemaVacunacion}.
     */
    List<EsquemaVacunacion> listarPorPaciente(Long pacienteId);

    /**
     * Filtra el registro general de esquemas según su estado operativo de avance.
     * @param estado Criterio de estado (ej: PENDIENTE, COMPLETADO).
     * @return Lista de objetos {@link EsquemaVacunacion}.
     */
    List<EsquemaVacunacion> listarPorEstado(String estado);
}
