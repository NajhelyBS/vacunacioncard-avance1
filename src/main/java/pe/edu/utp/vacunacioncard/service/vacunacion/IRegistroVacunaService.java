package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para el control, auditoría e historial de Registros de Vacunas.
 */
public interface IRegistroVacunaService {

    /**
     * Obtiene una lista global con todos los registros de vacunas almacenados en el sistema.
     * @return Lista de objetos {@link RegistroVacuna}.
     */
    List<RegistroVacuna> listarTodos();

    /**
     * Recupera una transacción de vacuna específica mediante su identificador único.
     * @param id El identificador único del registro.
     * @return Un {@link Optional} que contiene el registro si es hallado.
     */
    Optional<RegistroVacuna> obtenerPorId(Long id);

    /**
     * Guarda de forma oficial el registro de una vacuna aplicada con control de excepciones.
     * @param registro El objeto registro con los metadatos de la dosis e inyección.
     * @return El {@link RegistroVacuna} guardado con éxito, o null si ocurre un fallo.
     */
    RegistroVacuna guardar(RegistroVacuna registro);

    /**
     * Extrae el consolidado de vacunas inyectadas bajo un mismo lote médico.
     * @param lote Número de lote de fabricación a rastrear.
     * @return Lista de objetos {@link RegistroVacuna}.
     */
    List<RegistroVacuna> listarPorLote(String lote);

    /**
     * Obtiene la bitácora de inmunizaciones ejecutadas por un profesional de salud.
     * @param enfermeroId Identificador único del enfermero.
     * @return Lista de objetos {@link RegistroVacuna}.
     */
    List<RegistroVacuna> listarPorEnfermero(Long enfermeroId);
}
