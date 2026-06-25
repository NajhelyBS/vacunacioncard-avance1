package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import pe.edu.utp.vacunacioncard.repository.vacunacion.CartillaVacunacionRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.ICartillaVacunacionService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la emisión, consulta y control de las cartillas digitales.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartillaVacunacionServiceImpl implements ICartillaVacunacionService {

    private final CartillaVacunacionRepository repo;

    /***
     * Recupera todas las cartillas de vacunación emitidas en el sistema con control de lectura.
     * @return Lista de objetos {@link CartillaVacunacion}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CartillaVacunacion> listarTodas() {
        log.info("Ejecutando consulta global de cartillas de vacunación emitidas");
        return repo.findAll();
    }

    /***
     * Obtiene una cartilla de vacunación específica mediante su identificador único con control de lectura.
     * @param id El identificador único de la cartilla.
     * @return Un {@link Optional} que contiene la cartilla si es hallada.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartillaVacunacion> obtenerPorId(Long id) {
        log.info("Buscando cartilla de vacunación por ID primario: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra o actualiza una cartilla digital en el sistema con control de persistencia y manejo de excepciones.
     * @param cartilla El objeto {@link CartillaVacunacion} con el historial
     * @return La {@link CartillaVacunacion} guardada con éxito, o null si ocurre un fallo crítico.
     */
    @Override
    @Transactional
    public CartillaVacunacion guardar(CartillaVacunacion cartilla) {
        log.info("Iniciando persistencia/actualización de la cartilla digital del paciente ID: {}", 
                cartilla.getPaciente() != null ? cartilla.getPaciente().getId() : "N/A");
        try {
            CartillaVacunacion guardada = repo.save(cartilla);
            log.info("Cartilla digital registrada exitosamente bajo el ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar guardar la cartilla digital: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Recupera la cartilla de vacunación de un paciente utilizando su identificador único con control de lectura.
     * @param pacienteId Identificador único del paciente.
     * @return Un {@link Optional} con la cartilla digital hallada.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartillaVacunacion> obtenerPorPaciente(Long pacienteId) {
        log.info("Solicitando consulta de cartilla digital para el paciente ID: {}", pacienteId);
        return repo.findByPacienteId(pacienteId);
    }


    /***
     * Valida y localiza una cartilla digital mediante el escaneo de su código QR con control de lectura.
     * @param codigoQR Texto estructurado del QR institucional.
     * @return Un {@link Optional} con el expediente de vacunación.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CartillaVacunacion> obtenerPorCodigoQR(String codigoQR) {
        log.info("Procesando verificación de autenticidad de cartilla mediante código QR");
        return repo.findByCodigoQR(codigoQR);
    }
}
