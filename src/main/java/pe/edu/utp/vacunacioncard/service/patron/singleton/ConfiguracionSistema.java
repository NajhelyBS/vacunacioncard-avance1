package pe.edu.utp.vacunacioncard.service.patron.singleton;

import java.io.Serializable;
import java.time.ZoneId;
import lombok.Getter;
import lombok.Setter; 

/**
 * Singleton que centraliza la configuracion global del sistema de vacunacion.
 */
@Getter
@Setter 
public class ConfiguracionSistema implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static ConfiguracionSistema instancia;

    private final ZoneId zonaHoraria = ZoneId.of("America/Lima");
    private String nombreSistema = "VacunacionCard";
    private int maxIntentosLogin = 3;
    private int diasValidezCita = 30;
    private boolean notificacionesActivas = true;

    /**
     * Constructor con visibilidad de paquete para mitigar el Code Smell de SonarQube.
     */
    ConfiguracionSistema() {
    }

    /**
     * Proporciona el punto de acceso global a la instancia única de la configuración.
     */
    public static synchronized ConfiguracionSistema getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionSistema();
        }
        return instancia;
    }
}
