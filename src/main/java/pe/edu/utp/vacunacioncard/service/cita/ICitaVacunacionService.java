package pe.edu.utp.vacunacioncard.service.cita;

import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para el agendamiento y seguimiento de Citas de Vacunación.
 */
public interface ICitaVacunacionService {

    /**
     * Obtiene una lista global con todas las citas de vacunación registradas en el sistema.
     * @return Lista de objetos {@link CitaVacunacion}.
     */
    List<CitaVacunacion> listarTodas();

    /**
     * Recupera un registro de cita específico mediante su identificador único.
     * @param id El identificador único de la cita.
     * @return Un {@link Optional} que contiene la cita si es hallada.
     */
    Optional<CitaVacunacion> obtenerPorId(Long id);

    /**
     * Agenda o actualiza una cita de vacunación en el sistema con validación de persistencia.
     * @param cita El objeto cita con los datos cronológicos y médicos.
     * @return La {@link CitaVacunacion} guardada con éxito, o null si ocurre un fallo.
     */
    CitaVacunacion programar(CitaVacunacion cita);

    /**
     * Obtiene el historial cronológico de citas correspondientes a un único paciente.
     * @param pacienteId Identificador único del paciente.
     * @return Lista de objetos {@link CitaVacunacion}.
     */
    List<CitaVacunacion> listarPorPaciente(Long pacienteId);

    /**
     * Filtra el consolidado de citas médicas según su estado operacional actual.
     * @param estado Criterio de estado (ej: PROGRAMADA, ASISTIDA).
     * @return Lista de objetos {@link CitaVacunacion}.
     */
    List<CitaVacunacion> listarPorEstado(String estado);
}

