package pe.edu.utp.vacunacioncard.model.cita;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase Horario que representa el horario de atencion para vacunacion.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Builder
@Entity
@Table(name = "mae_horario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dia_semana")
    private String diaSemana;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @Column(name = "capacidad_por_intervalo")
    private int capacidadPorIntervalo;

    @ElementCollection
    @CollectionTable(name = "mae_horario_intervalos", joinColumns = @JoinColumn(name = "horario_id"))
    @Column(name = "intervalo")
    private List<String> intervalosDisponibles = new ArrayList<>();
}
