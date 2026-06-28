package pe.edu.utp.vacunacioncard.service.campania.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import pe.edu.utp.vacunacioncard.repository.campania.CampaniaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.campania.ICampaniaVacunacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de campañas de vacunación.
 * Proporciona la lógica de negocio para la planificación, consulta por estados
 * y el ciclo de vida completo de la entidad CampaniaVacunacion.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CampaniaVacunacionServiceImpl implements ICampaniaVacunacionService {

    private final CampaniaVacunacionRepository repo;

    /**
     * Recupera un listado completo de todas las campañas de vacunación del sistema.
     *
     * @return {@link List} que contiene todas las entidades {@link CampaniaVacunacion}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CampaniaVacunacion> listarTodas() {
        log.info("Extrayendo el catálogo completo de campañas médicas registradas");
        return this.repo.findAll();
    }

    /**
     * Busca una campaña de vacunación específica mediante su identificador único.
     *
     * @param id Identificador único de la campaña de vacunación.
     * @return Un {@link Optional} con la {@link CampaniaVacunacion} si se encuentra,
     *         o un contenedor vacío si no existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CampaniaVacunacion> obtenerPorId(Long id) {
        log.info("Localizando campaña de vacunación con identificador único: {}", id);
        return this.repo.findById(id);
    }

    /**
     * Registra una nueva campaña de vacunación o actualiza los datos de una existente.
     *
     * @param campania Entidad {@link CampaniaVacunacion} con la información a persistir.
     * @return La entidad {@link CampaniaVacunacion} almacenada con su ID asignado.
     * @throws ServiceException Si ocurre un error de acceso o persistencia en la base de datos.
     */
    @Override
    @Transactional
    public CampaniaVacunacion registrar(CampaniaVacunacion campania) {
        log.info("Registrando campaña: {}", campania.getNombre());
        try {
            CampaniaVacunacion guardada = repo.save(campania);
            log.info("Campaña registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar campaña: " + campania.getNombre(), e);
        }
    }

    /**
     * Filtra y obtiene las campañas de vacunación según su estado operativo actual.
     *
     * @param estado Estado de la campaña (ej. "ACTIVA", "FINALIZADA", "PROGRAMADA").
     * @return {@link List} de entidades {@link CampaniaVacunacion} que coinciden con el estado enviado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CampaniaVacunacion> listarPorEstado(String estado) {
        log.info("Filtrando campañas por el criterio de estado operacional: {}", estado);
        return this.repo.findByEstado(estado);
    }

    /**
     * Elimina una campaña de vacunación del sistema mediante su ID.
     *
     * @param id Identificador único de la campaña que se desea eliminar.
     * @throws ServiceException Si ocurre un fallo de integridad o conectividad al borrar el registro.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Procediendo a remover la campaña médica con ID: {}", id);
        try {
            this.repo.deleteById(id);
            log.info("Registro de campaña eliminado exitosamente para ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar campaña con ID: " + id, e);
        }
    }
}
