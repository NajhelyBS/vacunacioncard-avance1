package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión clínica y registro de los Pacientes.
 */
public interface IPacienteService {

    /**
     * Obtiene una lista global con todos los pacientes registrados en el sistema.
     * @return Lista de objetos {@link Paciente}.
     */
    List<Paciente> listarTodos();

    /**
     * Recupera el expediente de un paciente específico mediante su ID único en base de datos.
     * @param id El identificador único del paciente.
     * @return Un {@link Optional} que contiene al paciente si es hallado.
     */
    Optional<Paciente> obtenerPorId(Long id);

    /**
     * Registra un nuevo paciente o actualiza sus antecedentes clínicos con control de persistencia.
     * @param paciente El objeto paciente con sus datos demográficos y médicos.
     * @return El {@link Paciente} guardado con éxito, o null si ocurre una anomalía.
     */
    Paciente registrar(Paciente paciente);

    /**
     * Localiza un paciente de forma estricta mediante su DNI.
     * @param dni Documento Nacional de Identidad a consultar.
     * @return Un {@link Optional} con el resultado.
     */
    Optional<Paciente> obtenerPorDni(String dni);

    /**
     * Busca a un paciente a través del identificador único de su historia clínica.
     * @param historiaClinicaId Identificador de la historia clínica.
     * @return Un {@link Optional} con el expediente hallado.
     */
    Optional<Paciente> obtenerPorHistoriaClinica(String historiaClinicaId);

    /**
     * Filtra el padrón general de pacientes de acuerdo a su estado operacional de vigencia.
     * @param activo Estado lógico (activo/inactivo).
     * @return Lista de objetos {@link Paciente}.
     */
    List<Paciente> listarPorEstado(boolean activo);
}

