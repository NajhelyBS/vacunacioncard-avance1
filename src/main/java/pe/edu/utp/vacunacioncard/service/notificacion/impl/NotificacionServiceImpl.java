package pe.edu.utp.vacunacioncard.service.notificacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.repository.notificacion.NotificacionRepository;
import pe.edu.utp.vacunacioncard.service.notificacion.INotificacionService;
import pe.edu.utp.vacunacioncard.service.patron.creacional.factorymethod.INotificacionFactory;
import pe.edu.utp.vacunacioncard.service.patron.creacional.factorymethod.RecordatorioNotificacionFactory;
import pe.edu.utp.vacunacioncard.service.patron.creacional.factorymethod.SistemaNotificacionFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Implementación del servicio para la gestión y envío de notificaciones.
 * Administra el ciclo de vida de las alertas del sistema y recordatorios de dosis,
 * centralizando la lógica horaria local de Lima y aplicando patrones creacionales.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements INotificacionService {
    
    /** Zona horaria oficial para el registro y envío de alertas. */
    private static final String ZONE_LIMA = "America/Lima";

    private final NotificacionRepository repo;

    /**
     * Procesa y despacha de forma inmediata una notificación configurada previamente.
     * Modifica internamente el estado de la notificación a "ENVIADA" y estampa 
     * la marca de tiempo bajo la zona horaria {@code America/Lima}.
     *
     * @param notificacion Entidad {@link Notificacion} con la información base y el destinatario asignado.
     * @return La entidad {@link Notificacion} guardada en el histórico con su ID generado.
     * @throws ServiceException Si ocurre un fallo en el acceso a datos al intentar almacenar el envío.
     */
    @Override
    @Transactional
    public Notificacion enviarNotificacion(Notificacion notificacion) {
        log.info("Enviando notificación al usuario ID: {}", notificacion.getDestinatario().getId());
        try {
            notificacion.setFechaEnvio(LocalDateTime.now(ZoneId.of(ZONE_LIMA)));
            notificacion.setEstado("ENVIADA");
            Notificacion guardada = repo.save(notificacion);
            log.info("Notificación enviada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al enviar notificación", e);
        }
    }

    /**
     * Recupera el buzón histórico de todas las notificaciones destinadas a un usuario en particular.
     *
     * @param usuarioId Identificador único del usuario receptor.
     * @return {@link List} que contiene las entidades {@link Notificacion} dirigidas a dicho usuario.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> listarPorUsuario(Long usuarioId) {
        log.info("Listando notificaciones del usuario ID: {}", usuarioId);
        return repo.findByDestinatarioId(usuarioId);
    }

    /**
     * Construye y persiste una nueva alerta informativa dirigida a un usuario específico.
     * Este método hace uso del patrón de diseño <b>Factory Method</b> instanciando la clase
     * {@link SistemaNotificacionFactory} para delegar la creación específica del objeto Alerta.
     *
     * @param usuario Entidad {@link Usuario} que recibirá el mensaje de alerta.
     * @param mensaje Contenido textual de la advertencia o evento del sistema.
     * @throws ServiceException Si ocurre una excepción de persistencia durante el guardado de la alerta.
     */
    @Override
    @Transactional
    public void registrarNuevaAlerta(Usuario usuario, String mensaje) {
        log.info("Creando alerta para usuario ID: {}", usuario.getId());
        
        // EMPLEACIÓN DEL PATRÓN: Instanciamos la fábrica concreta para tipos de Sistema/Alerta
        INotificacionFactory factory = new SistemaNotificacionFactory("ALERTA");
        Notificacion nuevaAlerta = factory.crearNotificacion(usuario, mensaje);
        
        try {
            nuevaAlerta.setFechaEnvio(LocalDateTime.now(ZoneId.of(ZONE_LIMA)));
            nuevaAlerta.setEstado("ENVIADA");
            repo.save(nuevaAlerta);
            log.info("Alerta registrada para usuario ID: {}", usuario.getId());
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar alerta", e);
        }
    }

    /**
     * Registra un recordatorio cronológico para la aplicación de una dosis o vacuna pendiente.
     * Este método implementa el patrón de diseño <b>Factory Method</b> a través de la clase
     * {@link RecordatorioNotificacionFactory}, encapsulando las dependencias del registro médico
     * y la fecha programada. El registro se guarda inicialmente con el estado "PENDIENTE".
     *
     * @param usuario  Entidad {@link Usuario} que debe acudir a la vacunación.
     * @param registro Entidad {@link RegistroVacuna} que contiene el detalle de la dosis correspondiente.
     * @param fecha    Fecha y hora estimadas en las que se debe gatillar el recordatorio.
     * @throws ServiceException Si ocurre una falla en la base de datos al almacenar la planificación.
     */
    @Override
    @Transactional
    public void registrarRecordatorioVacuna(Usuario usuario, RegistroVacuna registro, LocalDateTime fecha) {
        log.info("Creando recordatorio de vacuna para usuario ID: {}", usuario.getId());
        
        // EMPLEACIÓN DEL PATRÓN: Instanciamos la fábrica de Recordatorios inyectando sus dependencias requeridas
        INotificacionFactory factory = new RecordatorioNotificacionFactory(registro, fecha);
        Notificacion recordatorio = factory.crearNotificacion(usuario, null);
        
        try {
            recordatorio.setFechaEnvio(LocalDateTime.now(ZoneId.of(ZONE_LIMA)));
            recordatorio.setEstado("PENDIENTE");
            repo.save(recordatorio);
            log.info("Recordatorio registrado para usuario ID: {}", usuario.getId());
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar recordatorio de vacuna", e);
        }
    }
}
