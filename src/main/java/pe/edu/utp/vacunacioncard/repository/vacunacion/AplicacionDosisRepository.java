package pe.edu.utp.vacunacioncard.repository.vacunacion;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.vacunacion.AplicacionDosis;
import java.util.List;

public interface AplicacionDosisRepository extends JpaRepository<AplicacionDosis, Long> {

    List<AplicacionDosis> findByPacienteId(Long pacienteId);
    List<AplicacionDosis> findByLoteVacunaIgnoreCase(String loteVacuna);
}
