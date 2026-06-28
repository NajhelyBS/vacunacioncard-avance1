package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import pe.edu.utp.vacunacioncard.repository.vacunacion.CartillaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.ICartillaVacunacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de Cartillas de Vacunación.
 * Constituye el eje central del módulo médico, administrando el historial digitalizado 
 * de dosis de los ciudadanos y permitiendo su validación remota mediante identificadores QR.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartillaVacunacionServiceImpl implements ICartillaVacunacionService {

    private final CartillaVacunacionRepository repo;

    /**
     * Recupera un listado global con todas las cartillas de vacunación registradas en el sistema.
     *
     * @return {@link List} que contiene la totalidad de las entidades {@link CartillaVacunacion}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CartillaVacunacion> listarTodas() {
        log.info("Listando todas las cartillas de vacunación");
        return repo.findAll();
    }

    /**
     * Busca una cartilla de vacunación específica mediante su identificador interno único.
     *
     * @param id Identificador único de la cartilla en la base de datos.
     * @return Un {@link Optional} que contiene la {@link CartillaVacunacion} si se encuentra,
     *         o un contenedor vacío si no existe el registro.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartillaVacunacion> obtenerPorId(Long id) {
        log.info("Buscando cartilla por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra una nueva cartilla sanitaria o actualiza los metadatos de un historial médico existente.
     *
     * @param cartilla Entidad {@link CartillaVacunacion} que enlaza al paciente y sus registros de dosis.
     * @return La entidad {@link CartillaVacunacion} guardada de forma persistente con su respectivo ID.
     * @throws ServiceException Si ocurre una anomalía de acceso a datos o violación de restricciones de llave única.
     */
    @Override
    @Transactional
    public CartillaVacunacion guardar(CartillaVacunacion cartilla) {
        log.info("Guardando cartilla del paciente ID: {}",
                cartilla.getPaciente() != null ? cartilla.getPaciente().getId() : "N/A");
        try {
            CartillaVacunacion guardada = repo.save(cartilla);
            log.info("Cartilla guardada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar cartilla de vacunación", e);
        }
    }

    /**
     * Localiza la cartilla de vacunación correspondiente a un paciente en específico.
     *
     * @param pacienteId Identificador único del paciente del cual se requiere el historial médico.
     * @return Un {@link Optional} con la {@link CartillaVacunacion} vinculada al ciudadano, 
     *         o vacío si aún no se le ha aperturado una cartilla.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartillaVacunacion> obtenerPorPaciente(Long pacienteId) {
        log.info("Buscando cartilla del paciente ID: {}", pacienteId);
        return repo.findByPacienteId(pacienteId);
    }

    /**
     * Busca y valida un historial médico empleando la cadena de texto única codificada en su código QR.
     *
     * @param codigoQR Cadena única hash o alfanumérica que representa la firma del código QR de la cartilla.
     * @return Un {@link Optional} con la {@link CartillaVacunacion} hallada, o vacío si el código no coincide.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartillaVacunacion> obtenerPorCodigoQR(String codigoQR) {
        log.info("Buscando cartilla por código QR");
        return repo.findByCodigoQR(codigoQR);
    }
}
