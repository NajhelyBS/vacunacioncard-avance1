package pe.edu.utp.vacunacioncard.repository.comun;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.comun.Calendario;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface CalendarioRepository extends JpaRepository<Calendario, Long> {
    Optional<Calendario> findByFecha(LocalDate fecha);
    List<Calendario> findByEsHabil(boolean esHabil);
}
