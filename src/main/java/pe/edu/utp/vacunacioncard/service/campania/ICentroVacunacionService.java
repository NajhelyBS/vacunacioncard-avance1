package pe.edu.utp.vacunacioncard.service.campania;

import pe.edu.utp.vacunacioncard.model.campania.CentroVacunacion;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de locales y Centros de Vacunación.
 */
public interface ICentroVacunacionService {

    /**
     * Obtiene una lista con todos los centros de vacunación registrados en el sistema.
     * @return Lista de objetos {@link CentroVacunacion}.
     */
    List<CentroVacunacion> listarTodos();

    /**
     * Recupera un centro de vacunación específico mediante su identificador único.
     * @param id El identificador único del centro.
     * @return Un {@link Optional} que contiene el establecimiento si es hallado.
     */
    Optional<CentroVacunacion> obtenerPorId(Long id);

    /**
     * Registra un nuevo establecimiento de salud en el sistema con control de errores.
     * @param centro El objeto centro de vacunación con los datos a guardar.
     * @return El {@link CentroVacunacion} guardado, o null si ocurre una anomalía.
     */
    CentroVacunacion registrar(CentroVacunacion centro);

    /**
     * Obtiene los centros de vacunación filtrados por su estado operativo (activos/inactivos).
     * @param activo Verdadero si se desean listar los centros operativos.
     * @return Lista de objetos {@link CentroVacunacion}.
     */
    List<CentroVacunacion> listarPorEstado(boolean activo);

    /**
     * Realiza una búsqueda por coincidencia de texto en el nombre del centro médico.
     * @param nombre Término de búsqueda (ej: "Hospital", "EsSalud").
     * @return Lista de objetos {@link CentroVacunacion}.
     */
    List<CentroVacunacion> buscarPorNombre(String nombre);
}

