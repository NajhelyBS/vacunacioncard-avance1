package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión del catálogo maestro de Vacunas del sistema.
 */
public interface IVacunaService {

    /**
     * Obtiene una lista global con todas las vacunas registradas en el sistema.
     * @return Lista de objetos {@link Vacuna}.
     */
    List<Vacuna> listarTodas();

    /**
     * Recupera el registro de una vacuna específica mediante su identificador único.
     * @param id El identificador único de la vacuna.
     * @return Un {@link Optional} que contiene la vacuna si es hallada.
     */
    Optional<Vacuna> obtenerPorId(Long id);

    /**
     * Registra una nueva vacuna en el catálogo médico o actualiza sus propiedades de stock.
     * @param vacuna El objeto vacuna con las especificaciones técnicas a guardar.
     * @return La {@link Vacuna} guardada con éxito, o null si ocurre un fallo.
     */
    Vacuna registrar(Vacuna vacuna);

    /**
     * Filtra el catálogo maestro devolviendo únicamente las vacunas habilitadas para su uso.
     * @param disponible Estado lógico de disponibilidad.
     * @return Lista de objetos {@link Vacuna}.
     */
    List<Vacuna> listarPorDisponibilidad(boolean disponible);

    /**
     * Obtiene el listado de vacunas vinculadas a un proveedor o laboratorio farmacéutico.
     * @param laboratorioId Identificador único del laboratorio.
     * @return Lista de objetos {@link Vacuna}.
     */
    List<Vacuna> listarPorLaboratorio(Long laboratorioId);
}
