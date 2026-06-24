package pe.edu.utp.vacunacioncard.model.campania;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad CampaniaVacunacion que representa una campaña programada.
 */
@Builder
@Entity
@Table(name = "mae_campania_vacunacion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CampaniaVacunacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne 
    @JoinColumn(name = "vacuna_id")
    private Vacuna vacuna;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @ElementCollection 
    @CollectionTable(name = "campania_grupos_objetivo", joinColumns = @JoinColumn(name = "campania_id"))
    @Column(name = "grupo_objetivo")
    @Builder.Default
    private List<String> gruposObjetivo = new ArrayList<>();

    @Column(name = "meta_vacunacion")
    private int metaVacunacion;

    @Column(name = "vacunados_actuales")
    @Builder.Default
    private int vacunadosActuales = 0;

    @Column(name = "estado")
    @Builder.Default
    private String estado = "PLANEADA";


}