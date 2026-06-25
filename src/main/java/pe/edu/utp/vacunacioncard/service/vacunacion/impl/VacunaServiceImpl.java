package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import pe.edu.utp.vacunacioncard.repository.vacunacion.VacunaRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.IVacunaService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración del catálogo maestro de vacunas.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VacunaServiceImpl implements IVacunaService {

    private final VacunaRepository repo;

    /***
     * Recupera todas las vacunas registradas en el sistema con control de lectura.
     * @return Lista de objetos {@link Vacuna}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> listarTodas() {
        log.info("Ejecutando consulta del catálogo global de vacunas registradas");
        return repo.findAll();
    }

    /***
     * Recupera el registro de una vacuna específica mediante su identificador único.
     * @param id El identificador único de la vacuna.
     * @return Un {@link Optional} que contiene la vacuna si es hallada.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vacuna> obtenerPorId(Long id) {
        log.info("Buscando registro de vacuna por ID primario: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra una nueva vacuna en el catálogo médico o actualiza sus propiedades de stock.
     * @param vacuna El objeto vacuna con las especificaciones técnicas a guardar.
     * @return La {@link Vacuna} guardada con éxito, o null si ocurre un fallo.
     */
    @Override
    @Transactional
    public Vacuna registrar(Vacuna vacuna) {
        log.info("Iniciando almacenamiento de la especificación médica de la vacuna: {}", vacuna.getNombre());
        try {
            Vacuna guardada = repo.save(vacuna);
            log.info("Vacuna registrada exitosamente en el catálogo con el ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar la vacuna médica: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Filtra el catálogo maestro devolviendo únicamente las vacunas habilitadas para su uso.
     * @param disponible Estado lógico de disponibilidad.
     * @return Lista de objetos {@link Vacuna}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> listarPorDisponibilidad(boolean disponible) {
        log.info("Filtrando catálogo de vacunas según disponibilidad de stock: {}", disponible);
        return repo.findByDisponible(disponible);
    }

    /***
     * Obtiene el listado de vacunas vinculadas a un proveedor o laboratorio farmacéutico.
     * @param laboratorioId Identificador único del laboratorio.
     * @return Lista de objetos {@link Vacuna}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Vacuna> listarPorLaboratorio(Long laboratorioId) {
        log.info("Consultando vacunas distribuidas por el laboratorio farmacéutico ID: {}", laboratorioId);
        return repo.findByLaboratorioId(laboratorioId);
    }
}

