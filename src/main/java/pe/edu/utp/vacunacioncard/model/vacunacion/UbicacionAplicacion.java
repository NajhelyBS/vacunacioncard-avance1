package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.io.Serializable; // <-- Añade este import
import jakarta.persistence.*;
import lombok.*;

/**
 * Clase UbicacionAplicacion que representa la ubicacion donde se aplica una vacuna.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionAplicacion implements Serializable { // <-- Implementa Serializable

    private static final long serialVersionUID = 1L; // <-- Añade este serial de versión

    @Column(name = "nombre_lugar")
    private String nombreLugar;

    @Column(name = "direccion_aplicacion")
    private String direccion;

    @Column(name = "distrito")
    private String distrito;
}
