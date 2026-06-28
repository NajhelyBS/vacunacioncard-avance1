package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.Laboratorio;
import pe.edu.utp.vacunacioncard.repository.vacunacion.LaboratorioRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.ILaboratorioService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de Laboratorios farmacéuticos.
 * Administra la lógica de negocio asociada a las entidades fabricantes de las dosis,
 * controlando su información corporativa, país de procedencia y trazabilidad.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LaboratorioServiceImpl implements ILaboratorioService {

    private final LaboratorioRepository repo;

    /**
     * Recupera una lista global con todos los laboratorios farmacéuticos registrados.
     *
     * @return {@link List} que aloja a todas las entidades {@link Laboratorio}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Laboratorio> listarTodos() {
        log.info("Listando todos los laboratorios");
        return repo.findAll();
    }

    /**
     * Busca la ficha técnica de un laboratorio mediante su identificador único.
     *
     * @param id Identificador único del laboratorio en el sistema.
     * @return Un {@link Optional} que envuelve al {@link Laboratorio} si es hallado,
     *         o un contenedor vacío si no existen registros.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Laboratorio> obtenerPorId(Long id) {
        log.info("Buscando laboratorio por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra un nuevo laboratorio farmacéutico o actualiza los datos de uno existente.
     *
     * @param laboratorio Entidad {@link Laboratorio} que contiene los datos comerciales a persistir.
     * @return La entidad {@link Laboratorio} guardada con su identificador único asignado.
     * @throws ServiceException Si ocurre una anomalía de persistencia o acceso a la base de datos.
     */
    @Override
    @Transactional
    public Laboratorio registrar(Laboratorio laboratorio) {
        log.info("Registrando laboratorio: {}", laboratorio.getNombre());
        try {
            Laboratorio guardado = repo.save(laboratorio);
            log.info("Laboratorio registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar laboratorio: " + laboratorio.getNombre(), e);
        }
    }

    /**
     * Filtra y obtiene las empresas farmacéuticas según su país de origen o sede central.
     *
     * @param pais Nombre del país de procedencia 
     * @return {@link List} de entidades {@link Laboratorio} que coinciden con el territorio enviado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Laboratorio> listarPorPais(String pais) {
        log.info("Listando laboratorios por país: {}", pais);
        return repo.findByPaisOrigenIgnoreCase(pais);
    }

    /**
     * Remueve de forma definitiva un laboratorio del catálogo del sistema mediante su ID.
     *
     * @param id Identificador único del laboratorio que se desea eliminar.
     * @throws ServiceException Si ocurre un error de persistencia o restricción de integridad referencial.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando laboratorio con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Laboratorio eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar laboratorio con ID: " + id, e);
        }
    }
}
