package pe.edu.utp.vacunacioncard.service.salud.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import pe.edu.utp.vacunacioncard.repository.salud.AlergiaRepository;
import pe.edu.utp.vacunacioncard.service.salud.IAlergiaService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de alergias médicas.
 * Administra la lógica de negocio asociada al registro de condiciones alérgicas,
 * consultas por niveles de gravedad y el control de persistencia de la entidad Alergia.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiaServiceImpl implements IAlergiaService {

    private final AlergiaRepository repo;

    /**
     * Recupera un listado global con todas las alergias catalogadas en el sistema.
     *
     * @return {@link List} que contiene la totalidad de las entidades {@link Alergia}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Alergia> listarTodas() {
        log.info("Listando todas las alergias");
        return repo.findAll();
    }

    /**
     * Busca el registro clínico de una alergia mediante su identificador único.
     *
     * @param id Identificador único de la alergia.
     * @return Un {@link Optional} que contiene la {@link Alergia} si es hallada,
     *         o un contenedor vacío si no se registran coincidencias.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Alergia> obtenerPorId(Long id) {
        log.info("Buscando alergia por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra una nueva condición alérgica o actualiza una existente en el sistema.
     *
     * @param alergia Entidad {@link Alergia} con la descripción, nombre y severidad a persistir.
     * @return La entidad {@link Alergia} almacenada de forma persistente con su ID asignado.
     * @throws ServiceException Si ocurre una falla en el acceso a datos durante el proceso de guardado.
     */
    @Override
    @Transactional
    public Alergia registrar(Alergia alergia) {
        log.info("Registrando alergia: {}", alergia.getNombre());
        try {
            Alergia guardada = repo.save(alergia);
            log.info("Alergia registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar alergia: " + alergia.getNombre(), e);
        }
    }

    /**
     * Filtra y obtiene las alergias registradas según su nivel de severidad o criticidad clínica.
     * La consulta se ejecuta de forma insensible a mayúsculas y minúsculas (Case Insensitive)
     * basándose en la configuración del repositorio.
     *
     * @param severidad Nivel de gravedad a filtrar (ej. "LEVE", "MODERADA", "SEVERA").
     * @return {@link List} de entidades {@link Alergia} que coinciden con el grado estipulado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Alergia> listarPorSeveridad(String severidad) {
        log.info("Listando alergias por severidad: {}", severidad);
        return repo.findBySeveridadIgnoreCase(severidad);
    }

    /**
     * Remueve de forma definitiva una alergia del sistema mediante su identificador único.
     *
     * @param id Identificador único del registro alérgico a eliminar.
     * @throws ServiceException Si se presenta un error de integridad de datos o restricción al borrar.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando alergia con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Alergia eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar alergia con ID: " + id, e);
        }
    }
}
