package pe.utp.edu.vacunacioncard.domain.usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.utp.edu.vacunacioncard.domain.salud.Alergia;
import pe.utp.edu.vacunacioncard.domain.salud.CondicionMedica;
import pe.utp.edu.vacunacioncard.domain.salud.Contraindicacion;


/**
 * Clase Paciente que representa un paciente del sistema.
 * Contiene información médica y datos personales.
 *
 * @author Grupo 1
 * @version 1.0
 */



@Getter
@Setter
@NoArgsConstructor
public class Paciente extends Usuario{
    private String historiaClinicaId;
    private String grupoSanguineo;
    private List<Alergia> alergias;
    private List<CondicionMedica> condicionesMedicas;
    private List<Contraindicacion> contraindicaciones;
    private SeguroMedico seguroMedico;
    private String contactoEmergencia;

    public Paciente(String nombreCompleto, String dni, LocalDate fechaNacimiento) {
        super(nombreCompleto, dni, fechaNacimiento);
        this.alergias = new ArrayList<>();
        this.condicionesMedicas = new ArrayList<>();
        this.contraindicaciones = new ArrayList<>();
    }
}
