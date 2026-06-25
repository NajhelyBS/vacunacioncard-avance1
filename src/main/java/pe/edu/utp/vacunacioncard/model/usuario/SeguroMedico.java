package pe.edu.utp.vacunacioncard.model.usuario;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase SeguroMedico que representa el seguro medico asociado a un paciente.
 * Contiene informacion relevante sobre cobertura y poliza.
 *
 */

@Builder
@Entity
@Table(name = "mae_seguro_medico")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeguroMedico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "numero_poliza", nullable = false)
    private String numeroPoliza;

    @Column(name = "cobertura", nullable = false)
    private String cobertura;
}
