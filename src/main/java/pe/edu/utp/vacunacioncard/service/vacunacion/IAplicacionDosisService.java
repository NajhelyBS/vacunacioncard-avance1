package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.AplicacionDosis;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para el registro clínico y seguimiento de Aplicación de Dosis.
 */
public interface IAplicacionDosisService {

    /**
     * Obtiene una lista global con todos los registros de aplicaciones de dosis en el sistema.
     * @return Lista de objetos {@link AplicacionDosis}.
     */
    List<AplicacionDosis> listarTodas();

    /**
     * Recupera un registro de aplicación específico mediante su identificador único de base de datos.
     * @param id El identificador único del registro.
     * @return Un {@link Optional} que contiene la aplicación de dosis si es hallada.
     */
    Optional<AplicacionDosis> obtenerPorId(Long id);

    /**
     * Registra de forma oficial la aplicación de una dosis con control estricto de persistencia.
     * @param aplicacion El objeto con los metadatos clínicos de la inyección.
     * @return La {@link AplicacionDosis} guardada con éxito, o null si ocurre un fallo.
     */
    AplicacionDosis registrarAplicacion(AplicacionDosis aplicacion);

    /**
     * Extrae de forma cronológica el historial de dosis administradas a un paciente.
     * @param pacienteId Identificador único del paciente.
     * @return Lista de objetos {@link AplicacionDosis}.
     */
    List<AplicacionDosis> listarPorPaciente(Long pacienteId);

    /**
     * Obtiene el consolidado de personas inoculadas bajo un mismo lote de fabricación.
     * @param lote Número de lote de la vacuna a rastrear.
     * @return Lista de objetos {@link AplicacionDosis}.
     */
    List<AplicacionDosis> listarPorLote(String lote);
}

