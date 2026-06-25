package pe.edu.utp.vacunacioncard.service.comun.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.comun.Calendario;
import pe.edu.utp.vacunacioncard.repository.comun.CalendarioRepository;
import pe.edu.utp.vacunacioncard.service.comun.ICalendarioService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la gestión de la agenda y días del calendario general.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarioServiceImpl implements ICalendarioService {

    private final CalendarioRepository repo;

    /***
     * Registra o actualiza un día en el calendario con control de persistencia y manejo de excepciones.
     * @param calendario Objeto {@link Calendario} con la fecha y estado a guardar
     * @return El objeto {@link Calendario} guardado o null si ocurre un error crítico.
     */
    @Override
    @Transactional
    public Calendario guardarDia(Calendario calendario) {
        log.info("Configurando estado del calendario para la fecha: {}", calendario.getFecha());
        try {
            Calendario diaGuardado = repo.save(calendario);
            log.info("Fecha almacenada con éxito bajo el ID asignado: {}", diaGuardado.getId());
            return diaGuardado;
        } catch (DataAccessException e) {
            log.error("Error crítico al intentar guardar la configuración del día [{}]: {}", 
                    calendario.getFecha(), e.getMessage());
            return null;
        }
    }

    /***
     * Obtiene un día específico del calendario según su fecha, con control de lectura.
     * @param fecha Objeto {@link LocalDate} que representa la fecha a consultar.
     * @return Un {@link Optional} que contiene la información del día si existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Calendario> obtenerPorFecha(LocalDate fecha) {
        log.info("Solicitando verificación operacional de la fecha: {}", fecha);
        return repo.findByFecha(fecha);
    }

    /***
     * Lista los días del calendario según su disponibilidad laboral.
     * @param esHabil Criterio lógico para filtrar días hábiles o feriados/fines de semana.
     * @return Lista de objetos {@link Calendario}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Calendario> listarPorDisponibilidad(boolean esHabil) {
        log.info("Filtrando días del calendario según estado de laborabilidad (esHabil): {}", esHabil);
        return repo.findByEsHabil(esHabil);
    }
}
