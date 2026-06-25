package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion;
import pe.edu.utp.vacunacioncard.repository.vacunacion.EsquemaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.IEsquemaVacunacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración del calendario de esquemas de vacunación.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EsquemaVacunacionServiceImpl implements IEsquemaVacunacionService {

    private final EsquemaVacunacionRepository repo;

    /***
     * Recupera todos los esquemas de vacunación registrados en el sistema con control de lectura.
     * @return Lista de objetos {@link EsquemaVacunacion}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EsquemaVacunacion> listarTodos() {
        log.info("Ejecutando consulta del catálogo global de esquemas de vacunación");
        return repo.findAll();
    }

    /***
     * Obtiene un esquema de vacunación específico mediante su identificador único con control de lectura.
     * @param id El identificador único del esquema.
     * @return Un {@link Optional} que contiene el esquema si es hallado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EsquemaVacunacion> obtenerPorId(Long id) {
        log.info("Buscando esquema de vacunación por clave primaria ID: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra un nuevo esquema de vacunación o actualiza uno existente con control de persistencia y manejo de excepciones.
     * @param esquema El objeto {@link EsquemaVacunacion} con los detalles
     * @return El {@link EsquemaVacunacion} guardado con éxito, o null si ocurre un fallo crítico.
     */
    @Override
    @Transactional
    public EsquemaVacunacion guardar(EsquemaVacunacion esquema) {
        log.info("Iniciando almacenamiento de esquema clínico asignado al paciente ID: {}", 
                esquema.getPacienteAsignado() != null ? esquema.getPacienteAsignado().getId() : "N/A");
        try {
            EsquemaVacunacion guardado = repo.save(esquema);
            log.info("Esquema de vacunación registrado con éxito asignando ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar el esquema de inmunización: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Extrae el consolidado de esquemas médicos asignados al perfil de un paciente.
     * @param pacienteId Identificador único del paciente.
     * @return Lista de objetos {@link EsquemaVacunacion}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EsquemaVacunacion> listarPorPaciente(Long pacienteId) {
        log.info("Consultando la lista de esquemas asignados al paciente ID: {}", pacienteId);
        return repo.findByPacienteAsignadoId(pacienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EsquemaVacunacion> listarPorEstado(String estado) {
        log.info("Filtrando catálogo general de esquemas por estado operativo: {}", estado);
        return repo.findByEstadoIgnoreCase(estado);
    }
}
