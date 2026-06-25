package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.Laboratorio;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión y catalogación de Laboratorios farmacéuticos.
 */
public interface ILaboratorioService {

    /**
     * Obtiene una lista global con todos los laboratorios registrados en el sistema.
     * @return Lista de objetos {@link Laboratorio}.
     */
    List<Laboratorio> listarTodos();

    /**
     * Recupera un registro de laboratorio específico mediante su identificador único.
     * @param id El identificador único del laboratorio.
     * @return Un {@link Optional} que contiene el laboratorio si es hallado.
     */
    Optional<Laboratorio> obtenerPorId(Long id);

    /**
     * Registra un nuevo laboratorio farmacéutico o actualiza su procedencia con control de persistencia.
     * @param laboratorio El objeto laboratorio con los datos de fabricación a guardar.
     * @return El {@link Laboratorio} guardado con éxito, o null si ocurre un fallo.
     */
    Laboratorio registrar(Laboratorio laboratorio);

    /**
     * Filtra el catálogo maestro de laboratorios de acuerdo a su país de origen.
     * @param pais Nombre del país a consultar.
     * @return Lista de objetos {@link Laboratorio}.
     */
    List<Laboratorio> listarPorPais(String pais);
}
