package pe.edu.utp.vacunacioncard.service.cita.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import pe.edu.utp.vacunacioncard.repository.cita.CitaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.cita.ICitaVacunacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración del flujo de citas médicas de vacunación.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CitaVacunacionServiceImpl implements ICitaVacunacionService {

    private final CitaVacunacionRepository repo;

    /**
     * Consulta el total de citas médicas registradas en la base de datos en modo lectura.
     * @return Lista de citas.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> listarTodas() {
        log.info("Ejecutando consulta global de citas de vacunación en el sistema");
        return repo.findAll();
    }

    /**
     * Localiza una cita específica basándose en su clave primaria única.
     * @param id Identificador de la cita.
     * @return Un Optional encapsulando el resultado de la búsqueda.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CitaVacunacion> obtenerPorId(Long id) {
        log.info("Buscando registro de cita con ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra un agendamiento de cita controlando fallos críticos de acceso a datos.
     * @param cita Información estructural de la cita.
     * @return Objeto guardado o null en caso de excepción de base de datos.
     */
    @Override
    @Transactional
    public CitaVacunacion programar(CitaVacunacion cita) {
        log.info("Iniciando flujo de asignación/programación de cita para el paciente ID: {}", 
                cita.getPaciente() != null ? cita.getPaciente().getId() : "Desconocido");
        try {
            CitaVacunacion citaGuardada = repo.save(cita);
            log.info("Cita agendada exitosamente en el sistema con ID generado: {}", citaGuardada.getId());
            return citaGuardada;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar la cita médica: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Filtra el historial de agendamientos médicos vinculados a un paciente.
     * @param pacienteId Identificador del usuario paciente.
     * @return Lista de citas filtradas.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> listarPorPaciente(Long pacienteId) {
        log.info("Consultando historial de citas asignadas al paciente ID: {}", pacienteId);
        return repo.findByPacienteId(pacienteId);
    }

    /**
     * Recupera las citas segmentadas por su estado actual de atención.
     * @param estado Filtro operativo de estado.
     * @return Lista de coincidencias.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitaVacunacion> listarPorEstado(String estado) {
        log.info("Filtrando el registro general de citas por el estado administrativo: {}", estado);
        return repo.findByEstado(estado);
    }
}
