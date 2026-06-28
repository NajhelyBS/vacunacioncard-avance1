package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import pe.edu.utp.vacunacioncard.repository.vacunacion.VacunaRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.IVacunaService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de las Vacunas catalogadas.
 * Define la lógica de negocio para la administración del catálogo de biológicos,
 * control de stock comercial/disponibilidad y segmentación por entidades fabricantes.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VacunaServiceImpl implements IVacunaService {

    private final VacunaRepository repo;

    /**
     * Recupera un listado global con todas las vacunas registradas en el catálogo general.
     *
     * @return {@link List} que contiene la totalidad de las entidades {@link Vacuna}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> listarTodas() {
        log.info("Listando todas las vacunas");
        return repo.findAll();
    }

    /**
     * Busca la ficha técnica e informativa de una vacuna mediante su identificador único.
     *
     * @param id Identificador único de la vacuna.
     * @return Un {@link Optional} que contiene la {@link Vacuna} si es hallada,
     *         o un contenedor vacío si no se registran coincidencias.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vacuna> obtenerPorId(Long id) {
        log.info("Buscando vacuna por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Inserta una nueva variante de vacuna en el catálogo o actualiza las especificaciones de una existente.
     *
     * @param vacuna Entidad {@link Vacuna} con el nombre, descripción y datos operativos a persistir.
     * @return La entidad {@link Vacuna} almacenada de forma persistente con su ID generado.
     * @throws ServiceException Si ocurre una anomalía de persistencia o fallo de conectividad con el repositorio.
     */
    @Override
    @Transactional
    public Vacuna registrar(Vacuna vacuna) {
        log.info("Registrando vacuna: {}", vacuna.getNombre());
        try {
            Vacuna guardada = repo.save(vacuna);
            log.info("Vacuna registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar vacuna: " + vacuna.getNombre(), e);
        }
    }

    /**
     * Filtra los productos biológicos basándose en su estado actual de distribución o inventario.
     *
     * @param disponible Criterio de disponibilidad: {@code true} para vacunas en stock aptas para cita,
     *                   {@code false} para biológicos agotados o descontinuados temporalmente.
     * @return {@link List} de entidades {@link Vacuna} que cumplen con la condición de stock enviada.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> listarPorDisponibilidad(boolean disponible) {
        log.info("Listando vacunas por disponibilidad: {}", disponible);
        return repo.findByDisponible(disponible);
    }

    /**
     * Obtiene los productos biológicos catalogados que pertenecen a una firma o fabricante farmacéutico.
     *
     * @param laboratorioId Identificador único del laboratorio farmacéutico proveedor 
     * @return {@link List} de entidades {@link Vacuna} asociadas directamente al ID de la corporación.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> listarPorLaboratorio(Long laboratorioId) {
        log.info("Listando vacunas del laboratorio ID: {}", laboratorioId);
        return repo.findByLaboratorioId(laboratorioId);
    }

    /**
     * Remueve de forma definitiva una vacuna del catálogo del sistema utilizando su identificador único.
     *
     * @param id Identificador único de la vacuna que se desea eliminar.
     * @throws ServiceException Si ocurre una restricción de llave foránea o fallo de persistencia.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando vacuna con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Vacuna eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar vacuna con ID: " + id, e);
        }
    }
}
