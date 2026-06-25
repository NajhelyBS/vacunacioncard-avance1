package pe.edu.utp.vacunacioncard.service.comun;

import pe.edu.utp.vacunacioncard.model.comun.Calendario;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de días hábiles y feriados institucionales.
 */
public interface ICalendarioService {

    /**
     * Registra o actualiza la configuración de un día en el calendario institucional.
     * @param calendario El objeto calendario con los datos del día a guardar.
     * @return El {@link Calendario} persistido, o null si ocurre una anomalía.
     */
    Calendario guardarDia(Calendario calendario);

    /**
     * Recupera la información operacional de una fecha específica.
     * @param fecha La fecha cronológica a consultar.
     * @return Un {@link Optional} que contiene la información del día si existe.
     */
    Optional<Calendario> obtenerPorFecha(LocalDate fecha);

    /**
     * Filtra los días registrados en el sistema según su disponibilidad laboral.
     * @param esHabil Criterio lógico para filtrar días hábiles o feriados/fines de semana.
     * @return Lista de objetos {@link Calendario}.
     */
    List<Calendario> listarPorDisponibilidad(boolean esHabil);
}
