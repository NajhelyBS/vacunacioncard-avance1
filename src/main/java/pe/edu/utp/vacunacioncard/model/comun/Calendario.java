package pe.edu.utp.vacunacioncard.model.comun;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entidad Calendario que representa la persistencia y control de días hábiles y feriados.
 */
@Builder
@Entity
@Table(name = "mae_calendario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Calendario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", nullable = false, unique = true)
    private LocalDate fecha;

    @Column(name = "es_habil", nullable = false)
    private boolean esHabil;

    @Column(name = "descripcion_feriado")
    private String descripcionFeriado; 
}

