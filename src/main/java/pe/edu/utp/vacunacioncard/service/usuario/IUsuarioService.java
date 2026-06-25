package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio genérica para la administración de usuarios base del sistema.
 */
public interface IUsuarioService {

    /**
     * Recupera la lista de todos los usuarios que se encuentran activos en el sistema.
     * @return Lista de objetos {@link Usuario}.
     */
    List<Usuario> listarActivos();

    /**
     * Busca un usuario polimórficamente a través de su número de DNI.
     * @param dni Documento nacional de identidad.
     * @return Un {@link Optional} con el usuario si es hallado.
     */
    Optional<Usuario> buscarPorDni(String dni);

    /**
     * Cambia el estado de actividad de un usuario (Inactivación/Activación lógica).
     * @param id Identificador único del usuario.
     */
    void cambiarEstado(Long id);
}
