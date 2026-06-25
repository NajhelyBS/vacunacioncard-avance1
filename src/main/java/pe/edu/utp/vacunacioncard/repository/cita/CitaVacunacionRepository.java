package pe.edu.utp.vacunacioncard.repository.cita;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import java.util.List;

public interface CitaVacunacionRepository extends JpaRepository<CitaVacunacion, Long> {

    List<CitaVacunacion> findByPacienteId(Long pacienteId);
    List<CitaVacunacion> findByEstado(String estado);
}
