package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.vacunacion.AplicacionDosis;
import pe.edu.utp.vacunacioncard.repository.vacunacion.AplicacionDosisRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.IAplicacionDosisService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración integral del acto de vacunación y dosis aplicadas.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AplicacionDosisServiceImpl implements IAplicacionDosisService {

    private final AplicacionDosisRepository repo;

    /***
     * Recupera todas las aplicaciones de dosis registradas en el sistema con control de lectura.
     * @return Lista de objetos {@link AplicacionDosis}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AplicacionDosis> listarTodas() {
        log.info("Ejecutando consulta global del historial de aplicaciones de dosis");
        return repo.findAll();
    }

    /***
     * Obtiene un registro de aplicación de dosis específico mediante su identificador único con control de lectura.
     * @param id El identificador único de la aplicación de dosis.
     * @return Un {@link Optional} que contiene la aplicación de dosis si es hallada.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AplicacionDosis> obtenerPorId(Long id) {
        log.info("Buscando registro de aplicación de dosis por ID: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra una nueva aplicación de dosis o actualiza una existente con control de persistencia y manejo de excepciones.
     * @param aplicacion El objeto {@link AplicacionDosis} con los detalles clín
     * @return La {@link AplicacionDosis} guardada con éxito, o null si ocurre un fallo crítico.
     */
    @Override
    @Transactional
    public AplicacionDosis registrarAplicacion(AplicacionDosis aplicacion) {
        log.info("Iniciando registro de aplicación de dosis N° {} para el paciente ID: {}", 
                aplicacion.getDosisNumero(), 
                aplicacion.getPaciente() != null ? aplicacion.getPaciente().getId() : "Desconocido");
        try {
            AplicacionDosis guardada = repo.save(aplicacion);
            log.info("Aplicación de dosis registrada con éxito bajo el ID generado: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar el acto de vacunación: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Obtiene el historial de aplicaciones de dosis administradas a un paciente específico con control de lectura.
     * @param pacienteId Identificador único del paciente.
     * @return Lista de objetos {@link AplicacionDosis}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AplicacionDosis> listarPorPaciente(Long pacienteId) {
        log.info("Consultando historial cronológico de dosis del paciente ID: {}", pacienteId);
        return repo.findByPacienteId(pacienteId);
    }

    /***
     * Obtiene el consolidado de personas inoculadas bajo un mismo lote de fabricación.
     * @param lote Número de lote de la vacuna a rastrear.
     * @return Lista de objetos {@link AplicacionDosis}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AplicacionDosis> listarPorLote(String lote) {
        log.info("Rastreando aplicaciones de vacunas pertenecientes al lote médico: {}", lote);
        return repo.findByLoteVacunaIgnoreCase(lote);
    }
}
