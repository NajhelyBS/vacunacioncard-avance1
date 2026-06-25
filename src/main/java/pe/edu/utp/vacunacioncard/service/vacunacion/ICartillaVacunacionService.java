package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión y emisión de Cartillas de Vacunación Digitales.
 */
public interface ICartillaVacunacionService {

    /**
     * Obtiene una lista global con todas las cartillas de vacunación registradas.
     * @return Lista de objetos {@link CartillaVacunacion}.
     */
    List<CartillaVacunacion> listarTodas();

    /**
     * Recupera una cartilla de vacunación específica mediante su identificador único.
     * @param id El identificador único de la cartilla.
     * @return Un {@link Optional} que contiene la cartilla si es hallada.
     */
    Optional<CartillaVacunacion> obtenerPorId(Long id);

    /**
     * Registra o actualiza una cartilla digital en el sistema con control de persistencia.
     * @param cartilla El objeto cartilla con el historial y códigos base.
     * @return La {@link CartillaVacunacion} guardada con éxito, o null si ocurre un fallo.
     */
    CartillaVacunacion guardar(CartillaVacunacion cartilla);

    /**
     * Recupera la cartilla de vacunación de un paciente utilizando su identificador único.
     * @param pacienteId Identificador único del paciente.
     * @return Un {@link Optional} con la cartilla digital hallada.
     */
    Optional<CartillaVacunacion> obtenerPorPaciente(Long pacienteId);

    /**
     * Valida y localiza una cartilla digital mediante el escaneo de su código QR.
     * @param codigoQR Texto estructurado del QR institucional.
     * @return Un {@link Optional} con el expediente de vacunación.
     */
    Optional<CartillaVacunacion> obtenerPorCodigoQR(String codigoQR);
}
