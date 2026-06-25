package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.SeguroMedico;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión y catalogación de Seguros Médicos de los pacientes.
 */
public interface ISeguroMedicoService {

    /**
     * Obtiene una lista con todos los seguros médicos registrados en el sistema.
     * @return Lista de objetos {@link SeguroMedico}.
     */
    List<SeguroMedico> listarTodos();

    /**
     * Recupera el registro de un seguro médico específico mediante su identificador único.
     * @param id El identificador único del seguro médico.
     * @return Un {@link Optional} que contiene el seguro si es hallado.
     */
    Optional<SeguroMedico> obtenerPorId(Long id);

    /**
     * Registra un nuevo seguro médico o actualiza uno existente con control de persistencia.
     * @param seguro El objeto seguro médico con los detalles de cobertura a guardar.
     * @return El {@link SeguroMedico} guardado con éxito, o null si ocurre un fallo.
     */
    SeguroMedico registrar(SeguroMedico seguro);

    /**
     * Localiza un seguro médico de forma estricta mediante su número de póliza asociado.
     * @param numeroPoliza Número de la póliza a consultar.
     * @return Un {@link Optional} con el seguro médico hallado.
     */
    Optional<SeguroMedico> obtenerPorNumeroPoliza(String numeroPoliza);
}
