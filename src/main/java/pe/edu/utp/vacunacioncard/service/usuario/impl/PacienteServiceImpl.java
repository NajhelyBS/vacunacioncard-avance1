package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.repository.usuario.PacienteRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IPacienteService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración integral de historias y expedientes de pacientes.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements IPacienteService {

    private final PacienteRepository repo;

    /***
     * Recupera todos los pacientes registrados en el sistema con control de lectura.
     * @return Lista de objetos {@link Paciente}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Paciente> listarTodos() {
        log.info("Ejecutando consulta del padrón global de pacientes inscritos");
        return repo.findAll();
    }

    /***
     * Obtiene un registro de paciente específico mediante su identificador único con control de lectura.
     * @param id El identificador único del paciente.
     * @return Un {@link Optional} que contiene al paciente si es hallado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorId(Long id) {
        log.info("Buscando expediente de paciente por ID: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra un nuevo paciente o actualiza su expediente con control de errores y manejo de excepciones.
     * @param paciente El objeto paciente con la información clínica y personal.
     * @return El {@link Paciente} guardado con éxito, o null si ocurre una anomalía crítica.
     */
    @Override
    @Transactional
    public Paciente registrar(Paciente paciente) {
        log.info("Iniciando flujo de persistencia para el expediente del paciente DNI: {}", paciente.getDni());
        try {
            Paciente guardado = repo.save(paciente);
            log.info("Expediente de paciente guardado exitosamente asignando ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar el expediente del paciente: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Localiza un paciente de forma estricta mediante su DNI.
     * @param dni Documento Nacional de Identidad a consultar.
     * @return Un {@link Optional} con el resultado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorDni(String dni) {
        log.info("Solicitando verificación y búsqueda de paciente por DNI: {}", dni);
        return repo.findByDni(dni);
    }

    /***
     * Busca a un paciente a través del identificador único de su historia clínica.
     * @param historiaClinicaId Identificador de la historia clínica.
     * @return Un {@link Optional} con el expediente hallado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorHistoriaClinica(String historiaClinicaId) {
        log.info("Solicitando búsqueda de expediente por número de Historia Clínica: {}", historiaClinicaId);
        return repo.findByHistoriaClinicaId(historiaClinicaId);
    }

    /***
     * Filtra el padrón general de pacientes de acuerdo a su estado operacional de vigencia.
     * @param activo Estado lógico (activo/inactivo).
     * @return Lista de objetos {@link Paciente}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Paciente> listarPorEstado(boolean activo) {
        log.info("Filtrando padrón de pacientes según estado operacional de vigencia: {}", activo);
        return repo.findByActivo(activo);
    }
}

