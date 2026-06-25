package pe.edu.utp.vacunacioncard.service.notificacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.repository.notificacion.NotificacionRepository;
import pe.edu.utp.vacunacioncard.service.notificacion.INotificacionService;
import pe.edu.utp.vacunacioncard.service.patron.creacional.factorymethod.NotificacionFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Implementación de servicios para la administración del ecosistema de alertas.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements INotificacionService {
    private static final String ZONE_LIMA = "America/Lima";

    private final NotificacionRepository repo;

    /***
     * Registra y envía una notificación al sistema con control de persistencia y
     * manejo de excepciones.
     * 
     * @param notificacion Objeto {@link Notificacion} con la información a enviar.
     * @return El objeto {@link Notificacion} persistido o null si ocurre un error
     *         crítico.
     */
    @Override
    @Transactional
    public Notificacion enviarNotificacion(Notificacion notificacion) {
        log.info("Iniciando el procesamiento de despacho para la notificación");
        try {
            notificacion.setFechaEnvio(LocalDateTime.now(ZoneId.of(ZONE_LIMA)));
            notificacion.setEstado("ENVIADA");

            Notificacion guardada = repo.save(notificacion);
            log.info("Notificación almacenada con éxito. ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al registrar la notificación: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Obtiene todas las notificaciones recibidas por un usuario específico con
     * control de lectura.
     * 
     * @param usuarioId Identificador único del usuario destinatario.
     * @return Lista de objetos {@link Notificacion}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> listarPorUsuario(Long usuarioId) {
        log.info("Consultando la bandeja de notificaciones recibidas por el usuario ID: {}", usuarioId);
        return repo.findByDestinatarioId(usuarioId);
    }

    /***
     * Registra una nueva alerta para un usuario utilizando el patrón Factory
     * Method.
     * 
     * @param usuario Objeto {@link Usuario} que recibirá la alerta.
     * @param mensaje Mensaje de la alerta a enviar.
     */
    @Override
    @Transactional
    public void registrarNuevaAlerta(Usuario usuario, String mensaje) {
        log.info("Orquestando patrón Factory Method para generar un objeto de alerta estructurado");

        Notificacion nuevaAlerta = NotificacionFactory.crearNotificacion("ALERTA", usuario, mensaje);

        try {
            nuevaAlerta.setFechaEnvio(LocalDateTime.now(ZoneId.of(ZONE_LIMA)));
            nuevaAlerta.setEstado("ENVIADA");
            repo.save(nuevaAlerta);
            log.info("Alerta de fábrica registrada exitosamente en la base de datos");
        } catch (DataAccessException e) {
            log.error("Error al registrar la alerta de fábrica: {}", e.getMessage());
        }
    }

    /***
     * Genera un recordatorio de vacunación para un usuario específico usando el
     * patrón Factory Method.
     * 
     * @param usuario  El paciente que recibirá el recordatorio.
     * @param registro El registro de la vacuna asociada.
     * @param fecha    La fecha programada para el aviso.
     */
    @Override
    @Transactional
    public void registrarRecordatorioVacuna(Usuario usuario,
            pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna registro, LocalDateTime fecha) {
        log.info("Invocando a la Fábrica para construir dinámicamente un Recordatorio de Vacunación");

        Notificacion recordatorio = NotificacionFactory.crearRecordatorio(usuario, registro, fecha);

        try {
            recordatorio.setFechaEnvio(LocalDateTime.now(ZoneId.of(ZONE_LIMA)));
            recordatorio.setEstado("PENDIENTE");
            log.info("Recordatorio de fábrica guardado exitosamente para su posterior despacho");
        } catch (DataAccessException e) {
            log.error("Error al registrar el recordatorio de la fábrica: {}", e.getMessage());
        }
    }

}
