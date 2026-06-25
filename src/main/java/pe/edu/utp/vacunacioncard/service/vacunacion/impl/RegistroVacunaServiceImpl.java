package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.repository.vacunacion.RegistroVacunaRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.IRegistroVacunaService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración de la bitácora de vacunas aplicadas.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegistroVacunaServiceImpl implements IRegistroVacunaService {

    private final RegistroVacunaRepository repo;

    /***
     * Recupera todos los registros de vacunas aplicadas en el sistema con control de lectura.
     * @return Lista de objetos {@link RegistroVacuna}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroVacuna> listarTodos() {
        log.info("Ejecutando consulta del registro histórico global de vacunas");
        return repo.findAll();
    }

    /***
     * Obtiene un registro específico de vacuna aplicada mediante su identificador único con control de lectura.
     * @param id El identificador único del registro de vacuna.
     * @return Un {@link Optional} que contiene el registro de vacuna si es hallado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroVacuna> obtenerPorId(Long id) {
        log.info("Buscando registro de vacuna por ID primario: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra oficialmente la aplicación de una vacuna con control de persistencia y manejo de excepciones.
     * @param registro El objeto {@link RegistroVacuna} con los datos de la dosis
     * @return El {@link RegistroVacuna} guardado con éxito, o null si ocurre un fallo crítico.
     */
    @Override
    @Transactional
    public RegistroVacuna guardar(RegistroVacuna registro) {
        log.info("Iniciando persistencia de registro de dosis N° {} para la vacuna ID: {}", 
                registro.getNumeroDosis(),
                registro.getVacuna() != null ? registro.getVacuna().getId() : "N/A");
        try {
            RegistroVacuna guardado = repo.save(registro);
            log.info("Inmunización registrada de forma exitosa bajo el ID asignado: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar la aplicación de la vacuna: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Filtra el historial de vacunas aplicadas de acuerdo a su lote de fabricación con control de lectura.
     * @param lote Número de lote a rastrear.
     * @return Lista de objetos {@link RegistroVacuna}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroVacuna> listarPorLote(String lote) {
        log.info("Rastreando historial de vacunas aplicadas pertenecientes al lote: {}", lote);
        return repo.findByLoteIgnoreCase(lote);
    }

    /***
     * Obtiene la bitácora de inmunizaciones ejecutadas por un profesional de salud específico con control de lectura.
     * @param enfermeroId Identificador único del enfermero aplicador.
     * @return Lista de objetos {@link RegistroVacuna}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroVacuna> listarPorEnfermero(Long enfermeroId) {
        log.info("Consultando historial de aplicaciones realizadas por el enfermero ID: {}", enfermeroId);
        return repo.findByEnfermeroAplicadorId(enfermeroId);
    }
}

