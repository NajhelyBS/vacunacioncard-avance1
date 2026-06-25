package pe.edu.utp.vacunacioncard.model.usuario;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase Enfermero que representa un profesional de salud encargado de aplicar vacunas.
 *
 */

@Entity
@Table(name = "mae_enfermero")
@NoArgsConstructor
@Getter
@Setter
public class Enfermero extends Usuario {

    @Column(name = "colegiatura")
    private String colegiatura;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "centro_trabajo")
    private String centroTrabajo;
}
