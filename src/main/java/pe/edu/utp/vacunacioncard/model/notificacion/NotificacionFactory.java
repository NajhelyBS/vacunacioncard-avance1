package pe.edu.utp.vacunacioncard.model.notificacion;

import java.time.LocalDateTime;

import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

/**
 * Factory Method para crear instancias de Notificacion segun el tipo solicitado.
 *
 * @author Grupo 1
 * @version 1.0
 */
public class NotificacionFactory {

    public static Notificacion crearNotificacion(String tipoNotificacion, Usuario destinatario, String mensaje) {
        return switch (tipoNotificacion.toUpperCase()) {
            case "SISTEMA" -> new NotificacionSistema(destinatario, mensaje, "SISTEMA");
            case "ALERTA" -> new NotificacionSistema(destinatario, mensaje, "ALERTA");
            case "INFORMACION" -> new NotificacionSistema(destinatario, mensaje, "INFORMACION");
            default -> throw new IllegalArgumentException("Tipo de notificacion no soportado: " + tipoNotificacion);
        };
    }

    public static Notificacion crearRecordatorio(Usuario destinatario, RegistroVacuna registroVacuna, LocalDateTime fechaRecordatorio) {
        return new NotificacionRecordatorio(destinatario, registroVacuna, fechaRecordatorio);
    }
}
