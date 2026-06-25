package pe.edu.utp.vacunacioncard.model.cita;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Horario que representa el horario de atención para vacunación.
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
    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false, length = 20)
    private DiaSemana diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "capacidad_por_intervalo", nullable = false)
    private int capacidadPorIntervalo;

    @ElementCollection
    @CollectionTable(
            name = "horario_intervalos_disponibles",
            joinColumns = @JoinColumn(name = "horario_id")
    )
    @Column(name = "intervalo", nullable = false)
    @Builder.Default
    private List<String> intervalosDisponibles = new ArrayList<>();
}
