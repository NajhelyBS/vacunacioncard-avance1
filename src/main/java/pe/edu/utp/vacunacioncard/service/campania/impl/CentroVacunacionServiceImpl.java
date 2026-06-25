package pe.edu.utp.vacunacioncard.service.campania.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.campania.CentroVacunacion;
import pe.edu.utp.vacunacioncard.repository.campania.CentroVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.campania.ICentroVacunacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración de establecimientos de vacunación.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CentroVacunacionServiceImpl implements ICentroVacunacionService {

    private final CentroVacunacionRepository repo;

    /**
     * Recupera todos los centros médicos de la base de datos en modo solo lectura.
     * @return Lista de establecimientos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CentroVacunacion> listarTodos() {
        log.info("Ejecutando consulta de todos los centros de vacunación");
        return repo.findAll();
    }

    /**
     * Busca un establecimiento específico por su ID único.
     * @param id Identificador del centro.
     * @return Un Optional con el centro si existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CentroVacunacion> obtenerPorId(Long id) {
        log.info("Buscando centro de vacunación con ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Guarda la información de un centro de vacunación controlando anomalías de acceso a datos.
     * @param centro Datos del nuevo establecimiento.
     * @return Objeto guardado o null si falla la inserción.
     */
    @Override
    @Transactional
    public CentroVacunacion registrar(CentroVacunacion centro) {
        log.info("Iniciando el registro del centro de vacunación: {}", centro.getNombre());
        try {
            CentroVacunacion centroGuardado = repo.save(centro);
            log.info("Centro médico registrado exitosamente con ID: {}", centroGuardado.getId());
            return centroGuardado;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar el centro [{}]: {}", centro.getNombre(), e.getMessage());
            return null;
        }
    }

    /**
     * Filtra los locales según su disponibilidad de atención actual.
     * @param activo Estado operativo.
     * @return Lista de locales filtrados.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CentroVacunacion> listarPorEstado(boolean activo) {
        log.info("Filtrando centros de vacunación por disponibilidad operativa: {}", activo);
        return repo.findByActivo(activo);
    }

    /**
     * Busca establecimientos de salud empleando coincidencia parcial de caracteres.
     * @param nombre Nombre o término clave.
     * @return Lista de coincidencias.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CentroVacunacion> buscarPorNombre(String nombre) {
        log.info("Buscando centros médicos que coincidan con el término: '{}'", nombre);
        return repo.findByNombreContainingIgnoreCase(nombre);
    }
}

