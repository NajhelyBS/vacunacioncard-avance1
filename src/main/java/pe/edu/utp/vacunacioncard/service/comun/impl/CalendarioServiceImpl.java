package pe.edu.utp.vacunacioncard.service.comun.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.comun.Calendario;
import pe.edu.utp.vacunacioncard.repository.comun.CalendarioRepository;
import pe.edu.utp.vacunacioncard.service.comun.ICalendarioService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión del calendario del sistema.
 * Administra la lógica de negocio para registrar fechas operativas, evaluar la 
 * disponibilidad de días hábiles y controlar el ciclo de vida de la entidad Calendario.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarioServiceImpl implements ICalendarioService {

    private final CalendarioRepository repo;

    /**
     * Registra o actualiza una fecha específica en el calendario del sistema.
     *
     * @param calendario Entidad {@link Calendario} con la información de la fecha y sus atributos.
     * @return La entidad {@link Calendario} guardada con su identificador único asignado.
     * @throws ServiceException Si ocurre un error de persistencia o acceso a la base de datos al guardar.
     */
    @Override
    @Transactional
    public Calendario guardarDia(Calendario calendario) {
        log.info("Guardando día en calendario: {}", calendario.getFecha());
        try {
            Calendario guardado = repo.save(calendario);
            log.info("Día guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar día: " + calendario.getFecha(), e);
        }
    }

    /**
     * Busca la configuración de un día específico en el calendario mediante su fecha exacta.
     *
     * @param fecha Objeto {@link LocalDate} que representa la fecha exacta a consultar (ej. 2026-07-28).
     * @return Un {@link Optional} que contiene el {@link Calendario} si está registrado,
     *         o un contenedor vacío si la fecha no existe en el sistema.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Calendario> obtenerPorFecha(LocalDate fecha) {
        log.info("Buscando calendario por fecha: {}", fecha);
        return repo.findByFecha(fecha);
    }

    /**
     * Filtra y obtiene los días registrados según su condición de disponibilidad laborable o no laborable.
     *
     * @param esHabil Criterio de disponibilidad: {@code true} para listar días laborables/hábiles,
     *                {@code false} para listar días no laborables o feriados.
     * @return {@link List} de entidades {@link Calendario} que cumplen con la condición de disponibilidad enviada.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Calendario> listarPorDisponibilidad(boolean esHabil) {
        log.info("Listando días por disponibilidad hábil: {}", esHabil);
        return repo.findByEsHabil(esHabil);
    }

    /**
     * Elimina del registro general una fecha del calendario mediante su identificador único.
     *
     * @param id Identificador único del registro de calendario a eliminar.
     * @throws ServiceException Si se genera una falla de conectividad o restricción de datos al borrar el registro.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando día del calendario con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Día eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar día con ID: " + id, e);
        }
    }
}
