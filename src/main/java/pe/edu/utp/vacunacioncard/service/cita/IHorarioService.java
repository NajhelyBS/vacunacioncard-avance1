package pe.edu.utp.vacunacioncard.service.cita;

import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión y asignación de Horarios de atención.
 */
public interface IHorarioService {

    /**
     * Obtiene una lista con todos los horarios registrados en el sistema de salud.
     * @return Lista de objetos {@link Horario}.
     */
    List<Horario> listarTodos();

    /**
     * Recupera un registro de horario específico mediante su identificador único.
     * @param id El identificador único del horario.
     * @return Un {@link Optional} que contiene el horario si es hallado.
     */
    Optional<Horario> obtenerPorId(Long id);

    /**
     * Registra o actualiza un esquema de horario de atención con control de persistencia.
     * @param horario El objeto horario con los datos cronológicos a guardar.
     * @return El {@link Horario} guardado con éxito, o null si ocurre un fallo.
     */
    Horario guardar(Horario horario);

    /**
     * Obtiene los bloques de horarios disponibles filtrados por la constante estricta del día.
     * @param diaSemana Objeto {@link DiaSemana} que representa el día a consultar.
     * @return Lista de objetos {@link Horario}.
     */
    List<Horario> listarPorDia(DiaSemana diaSemana);
}
