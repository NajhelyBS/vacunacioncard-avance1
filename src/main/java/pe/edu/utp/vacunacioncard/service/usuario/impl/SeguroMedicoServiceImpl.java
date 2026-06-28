package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.SeguroMedico;
import pe.edu.utp.vacunacioncard.repository.usuario.SeguroMedicoRepository;
import pe.edu.utp.vacunacioncard.service.usuario.ISeguroMedicoService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de los Seguros Médicos de los usuarios.
 * Controla la lógica de negocio para la afiliación de coberturas médicas,
 * validación de contratos comerciales y auditoría de pólizas activas en el sistema.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SeguroMedicoServiceImpl implements ISeguroMedicoService {

    private final SeguroMedicoRepository repo;

    /**
     * Recupera un listado completo de todos los seguros médicos registrados en el sistema.
     *
     * @return {@link List} que aloja todas las entidades {@link SeguroMedico}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SeguroMedico> listarTodos() {
        log.info("Listando todos los seguros médicos");
        return repo.findAll();
    }

    /**
     * Busca la información de una cobertura o seguro mediante su identificador interno único.
     *
     * @param id Identificador único del seguro médico en la base de datos.
     * @return Un {@link Optional} que contiene el {@link SeguroMedico} si es hallado,
     *         o un contenedor vacío si no se registran coincidencias.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SeguroMedico> obtenerPorId(Long id) {
        log.info("Buscando seguro médico por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra una nueva entidad prestadora de salud o actualiza los datos de una póliza existente.
     *
     * @param seguro Entidad {@link SeguroMedico} que contiene la información contractual a persistir.
     * @return La entidad {@link SeguroMedico} guardada con su identificador único asignado.
     * @throws ServiceException Si ocurre una anomalía de persistencia o restricción de unicidad con la póliza.
     */
    @Override
    @Transactional
    public SeguroMedico registrar(SeguroMedico seguro) {
        log.info("Registrando seguro médico póliza: {}", seguro.getNumeroPoliza());
        try {
            SeguroMedico guardado = repo.save(seguro);
            log.info("Seguro médico registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar seguro médico", e);
        }
    }

    /**
     * Localiza un seguro de salud específico empleando su número único de póliza o contrato.
     *
     * @param numeroPoliza Cadena de texto única que identifica el contrato del seguro (ej. SIS, EsSalud o EPS privada).
     * @return Un {@link Optional} con el {@link SeguroMedico} hallado, o vacío si el número de póliza no existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SeguroMedico> obtenerPorNumeroPoliza(String numeroPoliza) {
        log.info("Buscando seguro por póliza: {}", numeroPoliza);
        return repo.findByNumeroPoliza(numeroPoliza);
    }

    /**
     * Remueve de manera permanente un registro de seguro médico del catálogo del sistema.
     *
     * @param id Identificador único del seguro médico que se desea eliminar.
     * @throws ServiceException Si se presenta un fallo de conectividad o restricción de llave foránea al borrar.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando seguro médico con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Seguro médico eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar seguro médico con ID: " + id, e);
        }
    }
}
