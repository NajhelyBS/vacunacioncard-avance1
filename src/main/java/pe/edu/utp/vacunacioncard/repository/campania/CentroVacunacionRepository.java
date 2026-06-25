package pe.edu.utp.vacunacioncard.repository.campania;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.campania.CentroVacunacion;
import java.util.List;

public interface CentroVacunacionRepository extends JpaRepository<CentroVacunacion, Long> {
    List<CentroVacunacion> findByActivo(boolean activo);
    List<CentroVacunacion> findByNombreContainingIgnoreCase(String nombre);
}

