package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.usuario.SeguroMedico;
import pe.edu.utp.vacunacioncard.repository.usuario.SeguroMedicoRepository;
import pe.edu.utp.vacunacioncard.service.usuario.ISeguroMedicoService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración del catálogo de seguros médicos de pacientes.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SeguroMedicoServiceImpl implements ISeguroMedicoService {

    private final SeguroMedicoRepository repo;

    /***
     * Recupera todos los seguros médicos registrados en el sistema con control de lectura.
     * @return Lista de objetos {@link SeguroMedico}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SeguroMedico> listarTodos() {
        log.info("Ejecutando consulta global del catálogo maestro de seguros médicos");
        return repo.findAll();
    }

    /***
     * Obtiene un registro de seguro médico específico mediante su identificador único con control de lectura.
     * @param id El identificador único del seguro médico.
     * @return Un {@link Optional} que contiene el seguro si es hallado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SeguroMedico> obtenerPorId(Long id) {
        log.info("Buscando registro de seguro médico con ID: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra un nuevo seguro médico o actualiza uno existente con control de persistencia y manejo de excepciones.
     * @param seguro El objeto seguro médico con los detalles de cobertura a guardar.
     * @return El {@link SeguroMedico} guardado con éxito, o null si ocurre un fallo crítico.
     */
    @Override
    @Transactional
    public SeguroMedico registrar(SeguroMedico seguro) {
        log.info("Iniciando persistencia de nuevo seguro médico con póliza: {}", seguro.getNumeroPoliza());
        try {
            SeguroMedico guardado = repo.save(seguro);
            log.info("Seguro médico registrado exitosamente con el ID generado: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar el seguro médico: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Localiza un seguro médico de forma estricta mediante su número de póliza asociado.
     * @param numeroPoliza Número de la póliza a consultar.
     * @return Un {@link Optional} con el seguro médico hallado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SeguroMedico> obtenerPorNumeroPoliza(String numeroPoliza) {
        log.info("Solicitando búsqueda de seguro médico por número de póliza: {}", numeroPoliza);
        return repo.findByNumeroPoliza(numeroPoliza);
    }
}

