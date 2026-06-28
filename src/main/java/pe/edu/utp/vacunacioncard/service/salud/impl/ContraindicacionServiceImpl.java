package pe.edu.utp.vacunacioncard.service.salud.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;
import pe.edu.utp.vacunacioncard.repository.salud.ContraindicacionRepository;
import pe.edu.utp.vacunacioncard.service.salud.IContraindicacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de contraindicaciones médicas.
 * Controla la lógica de negocio orientada a la seguridad biológica del paciente, 
 * permitiendo identificar restricciones médicas asociadas a vacunas específicas.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContraindicacionServiceImpl implements IContraindicacionService {

    private final ContraindicacionRepository repo;

    /**
     * Recupera un listado completo de todas las contraindicaciones registradas en el sistema.
     *
     * @return {@link List} que contiene todas las entidades {@link Contraindicacion}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contraindicacion> listarTodas() {
        log.info("Listando todas las contraindicaciones");
        return repo.findAll();
    }

    /**
     * Busca una contraindicación específica utilizando su identificador único.
     *
     * @param id Identificador único de la contraindicación médica.
     * @return Un {@link Optional} con la {@link Contraindicacion} si se encuentra en el repositorio,
     *         o un contenedor vacío si no existe el registro.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Contraindicacion> obtenerPorId(Long id) {
        log.info("Buscando contraindicación por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra una nueva contraindicación o actualiza los datos clínicos de una existente.
     *
     * @param contraindicacion Entidad {@link Contraindicacion} con los criterios de restricción médica.
     * @return La entidad {@link Contraindicacion} guardada de forma persistente con su ID único generado.
     * @throws ServiceException Si ocurre una anomalía de persistencia o fallo en el acceso a datos.
     */
    @Override
    @Transactional
    public Contraindicacion registrar(Contraindicacion contraindicacion) {
        log.info("Registrando contraindicación");
        try {
            Contraindicacion guardada = repo.save(contraindicacion);
            log.info("Contraindicación registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar contraindicación", e);
        }
    }

    /**
     * Filtra e identifica las contraindicaciones clínicas que restringen la aplicación de una vacuna específica.
     *
     * @param vacunaId Identificador único de la vacuna bajo evaluación médica.
     * @return {@link List} de entidades {@link Contraindicacion} asociadas directamente al ID de la vacuna.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contraindicacion> listarPorVacunaAfectada(Long vacunaId) {
        log.info("Listando contraindicaciones de vacuna ID: {}", vacunaId);
        return repo.findByVacunaAfectadaId(vacunaId);
    }

    /**
     * Remueve permanentemente una contraindicación del catálogo del sistema a través de su ID.
     *
     * @param id Identificador único de la contraindicación que se desea eliminar.
     * @throws ServiceException Si ocurre un error de persistencia o restricción de llave foránea al borrar.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando contraindicación con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Contraindicación eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar contraindicación con ID: " + id, e);
        }
    }
}
