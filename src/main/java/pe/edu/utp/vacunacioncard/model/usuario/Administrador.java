package pe.edu.utp.vacunacioncard.model.usuario;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase Administrador que representa un usuario administrador del sistema.
 * Gestiona configuraciones y usuarios.
 *
 */

@Entity
@Table(name = "mae_administrador")
@NoArgsConstructor
@Getter
@Setter
public class Administrador extends Usuario {

    @Column(name = "nivel_acceso")
    private String nivelAcceso = "TOTAL";

    @Column(name = "area")
    private String area;
}
