package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.Administrador;
import pe.edu.utp.vacunacioncard.repository.usuario.AdministradorRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IAdministradorService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de usuarios con rol de Administrador.
 * Controla la lógica de negocio operativa relacionada con el personal administrativo,
 * permitiendo el control de accesos globales y la segmentación por áreas institucionales.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdministradorServiceImpl implements IAdministradorService {

    private final AdministradorRepository repo;

    /**
     * Recupera un listado global de todos los administradores registrados en el sistema.
     *
     * @return {@link List} que aloja todas las entidades {@link Administrador}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Administrador> listarTodos() {
        log.info("Listando todos los administradores");
        return repo.findAll();
    }

    /**
     * Busca la ficha informativa de un administrador a través de su identificador único.
     *
     * @param id Identificador único del administrador en el sistema.
     * @return Un {@link Optional} que contiene al {@link Administrador} si es hallado,
     *         o un contenedor vacío si no existen registros coincidentes.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Administrador> obtenerPorId(Long id) {
        log.info("Buscando administrador por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra un nuevo miembro del personal administrativo o actualiza sus credenciales de área.
     *
     * @param administrador Entidad {@link Administrador} que contiene los datos de la cuenta corporativa.
     * @return La entidad {@link Administrador} guardada con su identificador único asignado.
     * @throws ServiceException Si ocurre una anomalía de persistencia o fallo de red con el repositorio.
     */
    @Override
    @Transactional
    public Administrador registrar(Administrador administrador) {
        log.info("Registrando administrador del área: {}", administrador.getArea());
        try {
            Administrador guardado = repo.save(administrador);
            log.info("Administrador registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar administrador", e);
        }
    }

    /**
     * Filtra y obtiene los miembros del personal administrativo asignados a una sección operativa concreta.
     *
     * @param area Nombre o siglas del área institucional (ej. "SISTEMAS", "RECURSOS_HUMANOS").
     * @return {@link List} de entidades {@link Administrador} adscritas al área provista.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Administrador> listarPorArea(String area) {
        log.info("Listando administradores por área: {}", area);
        return repo.findByAreaIgnoreCase(area);
    }
}
