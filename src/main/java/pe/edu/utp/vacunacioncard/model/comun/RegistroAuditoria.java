package pe.edu.utp.vacunacioncard.model.comun;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Clase RegistroAuditoria que representa el registro de auditoría de acciones en el sistema.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class RegistroAuditoria {
    private final String id = UUID.randomUUID().toString();
    private Usuario usuario;
    private String accion;
    private String entidadAfectada;
    private String idEntidad;
    private String detalles;
    private final LocalDateTime fechaHora = LocalDateTime.now(ZoneId.of("America/Lima"));
    private String ipAddress;

    public RegistroAuditoria(Usuario usuario, String accion, String entidadAfectada, String detalles) {
        if (usuario == null) throw new IllegalArgumentException("Usuario no puede ser nulo");        
        this.usuario = usuario;
        this.accion = accion;
        this.entidadAfectada = entidadAfectada;
        this.detalles = detalles;
    }

}
