package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.repository.usuario.EnfermeroRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IEnfermeroService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración y control del personal asistencial de enfermería.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EnfermeroServiceImpl implements IEnfermeroService {

    private final EnfermeroRepository repo;

    /***
     * Recupera todos los enfermeros registrados en el sistema con control de lectura.
     * @return Lista de objetos {@link Enfermero}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Enfermero> listarTodos() {
        log.info("Ejecutando consulta del padrón global de personal de enfermería");
        return repo.findAll();
    }

    /***
     * Obtiene un registro de enfermero específico mediante su identificador único con control de lectura.
     * @param id El identificador único del enfermero.
     * @return Un {@link Optional} que contiene el registro si es hallado. 
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Enfermero> obtenerPorId(Long id) {
        log.info("Buscando registro de enfermero con ID: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra un nuevo enfermero o actualiza sus credenciales de gestión con control de errores.
     * @param enfermero El objeto enfermero con los niveles de acceso asignados.
     * @return El {@link Enfermero} guardado con éxito, o null si ocurre una anomalía.
     */
    @Override
    @Transactional
    public Enfermero registrar(Enfermero enfermero) {
        log.info("Iniciando almacenamiento del profesional de salud con colegiatura: {}", enfermero.getColegiatura());
        try {
            Enfermero guardado = repo.save(enfermero);
            log.info("Personal de enfermería registrado con éxito bajo el ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar el enfermero profesional: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Localiza a un profesional de salud mediante su credencial o código de colegiatura.
     * @param colegiatura Número de registro profesional.
     * @return Un {@link Optional} con el resultado de la búsqueda.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Enfermero> obtenerPorColegiatura(String colegiatura) {
        log.info("Solicitando verificación clínica de enfermero por número de colegiatura: {}", colegiatura);
        return repo.findByColegiatura(colegiatura);
    }

    /***
     * Filtra el personal de enfermería según su centro médico o de vacunación asignado.
     * @param centroTrabajo Nombre del local asistencial.
     * @return Lista de objetos {@link Enfermero}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Enfermero> listarPorCentroTrabajo(String centroTrabajo) {
        log.info("Filtrando personal de enfermería destacado en el establecimiento: {}", centroTrabajo);
        return repo.findByCentroTrabajoIgnoreCase(centroTrabajo);
    }
}

