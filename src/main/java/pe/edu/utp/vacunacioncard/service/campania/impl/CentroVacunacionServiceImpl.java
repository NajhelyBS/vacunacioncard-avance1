package pe.edu.utp.vacunacioncard.service.campania.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.campania.CentroVacunacion;
import pe.edu.utp.vacunacioncard.repository.campania.CentroVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.campania.ICentroVacunacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de centros de vacunación.
 * Administra la lógica de negocio para el registro, baja de establecimientos
 * y búsquedas avanzadas por disponibilidad o coincidencias de nombre.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CentroVacunacionServiceImpl implements ICentroVacunacionService {

    private final CentroVacunacionRepository repo;

    /**
     * Recupera un listado completo con todos los centros de vacunación registrados.
     *
     * @return {@link List} que contiene todas las entidades {@link CentroVacunacion}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CentroVacunacion> listarTodos() {
        log.info("Listando todos los centros de vacunación");
        return repo.findAll();
    }

    /**
     * Busca un centro de vacunación específico utilizando su identificador único.
     *
     * @param id Identificador único del centro de vacunación.
     * @return Un {@link Optional} con el {@link CentroVacunacion} si se encuentra,
     *         o un contenedor vacío si no existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CentroVacunacion> obtenerPorId(Long id) {
        log.info("Buscando centro de vacunación por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra un nuevo centro de vacunación o actualiza la información de uno existente.
     *
     * @param centro Entidad {@link CentroVacunacion} con los datos a persistir.
     * @return La entidad {@link CentroVacunacion} guardada con su ID asignado de forma única.
     * @throws ServiceException Si ocurre un fallo de acceso a datos durante la persistencia.
     */
    @Override
    @Transactional
    public CentroVacunacion registrar(CentroVacunacion centro) {
        log.info("Registrando centro: {}", centro.getNombre());
        try {
            CentroVacunacion guardado = repo.save(centro);
            log.info("Centro registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar centro: " + centro.getNombre(), e);
        }
    }

    /**
     * Filtra los centros de vacunación de acuerdo a su disponibilidad operativa actual.
     *
     * @param activo Estado operativo a filtrar: {@code true} para centros habilitados,
     *               {@code false} para deshabilitados.
     * @return {@link List} de entidades {@link CentroVacunacion} que coinciden con el estado enviado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CentroVacunacion> listarPorEstado(boolean activo) {
        log.info("Listando centros por estado activo: {}", activo);
        return repo.findByActivo(activo);
    }

    /**
     * Busca centros de vacunación cuyo nombre contenga el texto enviado como parámetro.
     * La consulta ignora la diferencia entre mayúsculas y minúsculas (Case Insensitive) 
     * y evalúa coincidencias parciales.
     *
     * @param nombre Cadena de texto o término de búsqueda a buscar en el nombre del centro.
     * @return {@link List} de entidades {@link CentroVacunacion} que cumplen con el criterio de coincidencia.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CentroVacunacion> buscarPorNombre(String nombre) {
        log.info("Buscando centros por nombre: '{}'", nombre);
        return repo.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Elimina del sistema un centro de vacunación mediante su identificador único.
     *
     * @param id Identificador único del establecimiento a eliminar.
     * @throws ServiceException Si ocurre un error de persistencia o restricción de llave foránea al borrar.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando centro de vacunación con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Centro eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar centro con ID: " + id, e);
        }
    }
}
