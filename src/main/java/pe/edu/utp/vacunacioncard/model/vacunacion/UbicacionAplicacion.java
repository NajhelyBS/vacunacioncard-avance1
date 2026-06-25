package pe.edu.utp.vacunacioncard.model.vacunacion;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase UbicacionAplicacion que representa la ubicacion donde se aplica una vacuna.
 * Incluye informacion del lugar de atencion.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionAplicacion {

    @Column(name = "nombre_lugar")
    private String nombreLugar;

    @Column(name = "direccion_aplicacion")
    private String direccion;

    @Column(name = "distrito")
    private String distrito;
}
