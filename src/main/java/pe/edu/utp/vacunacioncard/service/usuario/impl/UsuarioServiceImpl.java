package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.repository.usuario.UsuarioRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IUsuarioService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio base para la gestión de Usuarios del sistema.
 * Define las operaciones comunes de consulta de padrón activo, localización de cuentas 
 * por identidad ciudadana y control de estados lógicos de acceso.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository repo;

    /**
     * Recupera un listado exclusivo de todos los usuarios que se encuentran habilitados en el sistema.
     *
     * @return {@link List} que aloja a todas las entidades {@link Usuario} cuyo estado es activo.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarActivos() {
        log.info("Listando usuarios activos");
        return repo.findByActivo(true);
    }

    /**
     * Localiza a un usuario en el sistema empleando su Documento Nacional de Identidad (DNI).
     *
     * @param dni Cadena única de 8 dígitos correspondiente a la identificación ciudadana del usuario.
     * @return Un {@link Optional} que contiene la entidad {@link Usuario} si se encuentra,
     *         o un contenedor vacío si no existe el registro.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorDni(String dni) {
        log.info("Buscando usuario por DNI: {}", dni);
        return repo.findByDni(dni);
    }

    /**
     * Alterna de forma lógica el estado operativo de un usuario (Inactivación / Activación).
     * Si el usuario se encuentra registrado, este método invierte su estado booleano actual 
     * y persiste el cambio, evitando la eliminación física del registro para mantener la integridad referencial.
     *
     * @param id Identificador único del usuario al que se le cambiará el estado.
     * @throws ServiceException Si ocurre una anomalía de persistencia o acceso a la base de datos al guardar.
     */
    @Override
    @Transactional
    public void cambiarEstado(Long id) {
        log.info("Cambiando estado del usuario ID: {}", id);
        try {
            repo.findById(id).ifPresent(u -> {
                u.setActivo(!u.isActivo());
                repo.save(u);
                log.info("Usuario ID: {} estado cambiado a: {}", id, u.isActivo());
            });
        } catch (DataAccessException e) {
            throw new ServiceException("Error al cambiar estado del usuario ID: " + id, e);
        }
    }
}
