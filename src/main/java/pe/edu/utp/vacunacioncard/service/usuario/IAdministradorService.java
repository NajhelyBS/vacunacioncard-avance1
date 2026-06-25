package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Administrador;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión operativa de Usuarios Administradores.
 */
public interface IAdministradorService {

    /**
     * Obtiene una lista con todos los administradores registrados en el sistema.
     * @return Lista de objetos {@link Administrador}.
     */
    List<Administrador> listarTodos();

    /**
     * Recupera el registro de un administrador específico mediante su identificador único.
     * @param id El identificador único de la cuenta de administrador.
     * @return Un {@link Optional} que contiene al administrador si es hallado.
     */
    Optional<Administrador> obtenerPorId(Long id);

    /**
     * Registra un nuevo administrador o actualiza sus credenciales de gestión con control de errores.
     * @param administrador El objeto administrador con los niveles de acceso asignados.
     * @return El {@link Administrador} guardado con éxito, o null si ocurre una anomalía.
     */
    Administrador registrar(Administrador administrador);

    /**
     * Filtra el personal administrativo según su departamento o área de trabajo.
     * @param area Departamento institucional a consultar.
     * @return Lista de objetos {@link Administrador}.
     */
    List<Administrador> listarPorArea(String area);
}
