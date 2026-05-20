package pe.utp.edu.vacunacioncard.domain.notificacion;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.utp.edu.vacunacioncard.domain.usuario.Usuario;
import pe.utp.edu.vacunacioncard.domain.vacunacion.RegistroVacuna;

/**
 * Clase NotificacionRecordatorio que representa una notificación de recordatorio de vacunación.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class NotificacionRecordatorio extends Notificacion {
    private RegistroVacuna registroVacuna;
    private LocalDateTime fechaRecordatorio;

    public NotificacionRecordatorio(Usuario destinatario, RegistroVacuna registroVacuna, LocalDateTime fechaRecordatorio) {
        super(destinatario, "Recordatorio de próxima dosis");
        this.registroVacuna = registroVacuna;
        this.fechaRecordatorio = fechaRecordatorio;
    }
}
