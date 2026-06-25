package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.repository.usuario.UsuarioRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IUsuarioService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la gestión de usuarios base del sistema.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository repo;

    /**
     * Recupera la lista de todos los usuarios registrados que se encuentran activos.
     * @return Lista de usuarios activos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarActivos() {
        log.info("Consultando el listado general de usuarios activos en el sistema");
        return repo.findByActivo(true);
    }

    /**
     * Busca un usuario por su número de DNI de forma polimórfica.
     * @param dni Documento nacional de identidad.
     * @return Un Optional con el usuario si existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorDni(String dni) {
        log.info("Buscando usuario base por DNI: {}", dni);
        return repo.findByDni(dni);
    }

    /**
     * Cambia el estado de actividad de un usuario protegiendo la escritura con try-catch.
     * @param id Identificador del usuario.
     */
    @Override
    @Transactional
    public void cambiarEstado(Long id) {
        log.info("Iniciando solicitud de cambio de estado lógico para el usuario ID: {}", id);
        try {
            repo.findById(id).ifPresent(u -> {
                u.setActivo(!u.isActivo());
                repo.save(u);
                log.info("Estado del usuario ID {} cambiado exitosamente a: {}", id, u.isActivo());
            });
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar alternar el estado del usuario ID {}: {}", id, e.getMessage());
        }
    }
}
