package pe.edu.utp.vacunacioncard.service.salud.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import pe.edu.utp.vacunacioncard.repository.salud.CondicionMedicaRepository;
import pe.edu.utp.vacunacioncard.service.salud.ICondicionMedicaService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de condiciones médicas de los pacientes.
 * Proporciona soporte de negocio para registrar patologías o estados de salud preexistentes,
 * realizar búsquedas bajo el estándar internacional CIE-10 y segmentar por estado operativo.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CondicionMedicaServiceImpl implements ICondicionMedicaService {

    private final CondicionMedicaRepository repo;

    /**
     * Recupera un listado global con todas las condiciones médicas registradas en la aplicación.
     *
     * @return {@link List} que aloja la totalidad de las entidades {@link CondicionMedica}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CondicionMedica> listarTodas() {
        log.info("Listando todas las condiciones médicas");
        return repo.findAll();
    }

    /**
     * Busca la ficha informativa de una condición médica mediante su identificador único.
     *
     * @param id Identificador único de la condición médica.
     * @return Un {@link Optional} con la {@link CondicionMedica} si se encuentra en la base de datos,
     *         o un contenedor vacío si no hay coincidencias.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CondicionMedica> obtenerPorId(Long id) {
        log.info("Buscando condición médica por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra una nueva patología o condición de salud, o actualiza un registro preexistente.
     *
     * @param condicion Entidad {@link CondicionMedica} que contiene los datos clínicos a persistir.
     * @return La entidad {@link CondicionMedica} guardada con su identificador único asignado.
     * @throws ServiceException Si ocurre un error de persistencia o conectividad con el repositorio.
     */
    @Override
    @Transactional
    public CondicionMedica registrar(CondicionMedica condicion) {
        log.info("Registrando condición médica: {}", condicion.getNombre());
        try {
            CondicionMedica guardada = repo.save(condicion);
            log.info("Condición médica registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar condición médica: " + condicion.getNombre(), e);
        }
    }

    /**
     * Busca una condición médica específica empleando su código estandarizado internacional CIE-10.
     * La consulta ignora por completo la distinción gramatical entre mayúsculas y minúsculas
     * (Case Insensitive) al procesar la cadena del código.
     *
     * @param codigoCIE10 Código estandarizado de la enfermedad (ej. "E11" para Diabetes mellitus tipo 2).
     * @return Un {@link Optional} con la {@link CondicionMedica} asociada al código, o vacío si no se encuentra.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CondicionMedica> obtenerPorCodigoCIE10(String codigoCIE10) {
        log.info("Buscando condición por código CIE-10: {}", codigoCIE10);
        return repo.findByCodigoCIE10IgnoreCase(codigoCIE10);
    }

    /**
     * Filtra y obtiene las condiciones médicas basándose en si están catalogadas como activas o no.
     *
     * @param activa Estado clínico o de vigencia a evaluar: {@code true} para condiciones vigentes/activas,
     *               {@code false} para condiciones inactivas o dadas de baja en el catálogo.
     * @return {@link List} de entidades {@link CondicionMedica} que cumplen con el estado lógico provisto.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CondicionMedica> listarPorEstado(boolean activa) {
        log.info("Listando condiciones médicas por estado activa: {}", activa);
        return repo.findByActiva(activa);
    }

    /**
     * Remueve permanentemente una condición médica del catálogo del sistema mediante su ID.
     *
     * @param id Identificador único de la condición médica que se desea eliminar.
     * @throws ServiceException Si ocurre un fallo de integridad referencial o restricción de datos al borrar.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando condición médica con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Condición médica eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar condición médica con ID: " + id, e);
        }
    }
}
