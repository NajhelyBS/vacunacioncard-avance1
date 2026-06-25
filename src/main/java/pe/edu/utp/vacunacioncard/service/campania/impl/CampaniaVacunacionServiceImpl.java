package pe.edu.utp.vacunacioncard.service.campania.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import pe.edu.utp.vacunacioncard.repository.campania.CampaniaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.campania.ICampaniaVacunacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la gestión de campañas de vacunación.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CampaniaVacunacionServiceImpl implements ICampaniaVacunacionService {

    private final CampaniaVacunacionRepository repo;

    /**
     * Recupera todas las campañas registradas en el sistema en modo lectura.
     * @return Lista de campañas.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CampaniaVacunacion> listarTodas() {
        log.info("Ejecutando consulta global de campañas de vacunación");
        return repo.findAll();
    }

    /**
     * Busca una campaña específica por su ID de manera segura.
     * @param id Identificador único.
     * @return Optional con la campaña.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CampaniaVacunacion> obtenerPorId(Long id) {
        log.info("Solicitando búsqueda de campaña de vacunación con ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra una nueva campaña con control estricto de errores de persistencia.
     * @param campania Datos de la campaña.
     * @return Campaña guardada o null si falló la operación en base de datos.
     */
    @Override
    @Transactional
    public CampaniaVacunacion registrar(CampaniaVacunacion campania) {
        log.info("Iniciando registro de la campaña de vacunación: {}", campania.getNombre());
        try {
            CampaniaVacunacion campaniaGuardada = repo.save(campania);
            log.info("Campaña registrada exitosamente con ID asignado: {}", campaniaGuardada.getId());
            return campaniaGuardada;
        } catch (DataAccessException e) {
            log.error("Error crítico al intentar registrar la campaña [{}]: {}", campania.getNombre(), e.getMessage());
            return null;
        }
    }

    /**
     * Filtra las campañas de vacunación según su estado desde el repositorio.
     * @param estado El estado a buscar.
     * @return Lista de campañas filtradas.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CampaniaVacunacion> listarPorEstado(String estado) {
        log.info("Filtrando catálogo de campañas por el estado: {}", estado);
        return repo.findByEstado(estado);
    }
}
