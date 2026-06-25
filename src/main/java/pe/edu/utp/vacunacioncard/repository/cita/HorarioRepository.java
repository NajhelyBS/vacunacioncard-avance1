package pe.edu.utp.vacunacioncard.repository.cita;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findByDiaSemana(DiaSemana diaSemana);
}

