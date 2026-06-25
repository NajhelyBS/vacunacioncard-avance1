package pe.edu.utp.vacunacioncard.model.comun;

import java.time.ZoneId;

import lombok.Getter;

/**
 * Singleton que centraliza la configuracion global del sistema de vacunacion.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
public class ConfiguracionSistema {

    private static volatile ConfiguracionSistema instancia;

    private final ZoneId zonaHoraria = ZoneId.of("America/Lima");
    private String nombreSistema = "VacunacionCard";
    private int maxIntentosLogin = 3;
    private int diasValidezCita = 30;
    private boolean notificacionesActivas = true;

    private ConfiguracionSistema() {
    }

    public static ConfiguracionSistema getInstancia() {
        if (instancia == null) {
            synchronized (ConfiguracionSistema.class) {
                if (instancia == null) {
                    instancia = new ConfiguracionSistema();
                }
            }
        }
        return instancia;
    }

    public void setMaxIntentosLogin(int maxIntentosLogin) {
        this.maxIntentosLogin = maxIntentosLogin;
    }

    public void setDiasValidezCita(int diasValidezCita) {
        this.diasValidezCita = diasValidezCita;
    }

    public void setNotificacionesActivas(boolean notificacionesActivas) {
        this.notificacionesActivas = notificacionesActivas;
    }

    public void setNombreSistema(String nombreSistema) {
        this.nombreSistema = nombreSistema;
    }
}
