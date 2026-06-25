package pe.edu.utp.vacunacioncard.service.cita.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import pe.edu.utp.vacunacioncard.repository.cita.HorarioRepository;
import pe.edu.utp.vacunacioncard.service.cita.IHorarioService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración de franjas horarias de atención.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HorarioServiceImpl implements IHorarioService {

    private final HorarioRepository repo;

    /***
     * Consulta el catálogo completo de franjas horarias registradas en la base de datos.
     * @return Lista de Horario.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Horario> listarTodos() {
        log.info("Ejecutando consulta del catálogo global de horarios de atención");
        return repo.findAll();
    }

    /***
     * Busca una franja horaria específica por su identificador único.
     * @param id Identificador de la franja horaria.
     * @return Un {@link Optional} que contiene el horario si es hallado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Horario> obtenerPorId(Long id) {
        log.info("Buscando franja horaria por ID: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra o actualiza un esquema de horario de atención con control de persistencia.
     * @param horario El objeto horario con los datos cronológicos a guardar.
     * @return El {@link Horario} guardado con éxito, o null si ocurre un fallo.
     */
    @Override
    @Transactional
    public Horario guardar(Horario horario) {
        log.info("Iniciando almacenamiento del horario para el día tipado: {}", horario.getDiaSemana());
        try {
            Horario horarioGuardado = repo.save(horario);
            log.info("Horario registrado exitosamente con ID asignado: {}", horarioGuardado.getId());
            return horarioGuardado;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar el horario: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Obtiene los bloques de horarios disponibles filtrados por la constante estricta del día.
     * @param diaSemana Objeto {@link DiaSemana} que representa el día a consultar.
     * @return Lista de objetos {@link Horario}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Horario> listarPorDia(DiaSemana diaSemana) {
        log.info("Consultando bloques horarios programados para el día enum: {}", diaSemana);
        return repo.findByDiaSemana(diaSemana);
    }
}
