package pe.edu.utp.vacunacioncard.service.campania;

import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la planificación y gestión de Campañas de Vacunación.
 */
public interface ICampaniaVacunacionService {

    /**
     * Obtiene una lista con todas las campañas de vacunación registradas en el sistema.
     * @return Lista de objetos {@link CampaniaVacunacion}.
     */
    List<CampaniaVacunacion> listarTodas();

    /**
     * Recupera una campaña de vacunación específica mediante su identificador único.
     * @param id El identificador único de la campaña.
     * @return Un {@link Optional} que contiene la campaña si es hallada.
     */
    Optional<CampaniaVacunacion> obtenerPorId(Long id);

    /**
     * Registra una nueva campaña de vacunación en el sistema con control de persistencia.
     * @param campania El objeto campaña con los datos a guardar.
     * @return La {@link CampaniaVacunacion} guardada, o null si ocurre una anomalía.
     */
    CampaniaVacunacion registrar(CampaniaVacunacion campania);

    /**
     * Filtra y obtiene las campañas de vacunación según su estado actual.
     * @param estado El estado a filtrar (ej: PLANEADA, ACTIVA, FINALIZADA).
     * @return Lista de objetos {@link CampaniaVacunacion} que coinciden con el estado.
     */
    List<CampaniaVacunacion> listarPorEstado(String estado);
}
