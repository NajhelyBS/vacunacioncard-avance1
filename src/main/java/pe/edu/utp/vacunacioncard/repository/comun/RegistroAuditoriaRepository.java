package pe.edu.utp.vacunacioncard.repository.comun;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;
import java.util.List;

public interface RegistroAuditoriaRepository extends JpaRepository<RegistroAuditoria, Long> {

    List<RegistroAuditoria> findByUsuarioId(Long usuarioId);
    List<RegistroAuditoria> findByEntidadAfectadaIgnoreCase(String entidadAfectada);
}

