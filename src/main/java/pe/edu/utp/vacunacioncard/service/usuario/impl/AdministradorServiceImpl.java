package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.usuario.Administrador;
import pe.edu.utp.vacunacioncard.repository.usuario.AdministradorRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IAdministradorService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración y control de usuarios de tipo Administrador.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdministradorServiceImpl implements IAdministradorService {

    private final AdministradorRepository repo;

    /***
     * Recupera todos los administradores registrados en el sistema con control de lectura.
     * @return Lista de objetos {@link Administrador}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Administrador> listarTodos() {
        log.info("Ejecutando consulta global del personal administrador del sistema");
        return repo.findAll();
    }

    /***
     * Obtiene un registro de administrador específico mediante su identificador único con control de lectura.
     * @param id El identificador único del administrador.
     * @return Un {@link Optional} que contiene el registro si es hallado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Administrador> obtenerPorId(Long id) {
        log.info("Buscando registro de administrador con ID: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra un nuevo administrador o actualiza sus credenciales de gestión con control de errores.
     * @param administrador El objeto administrador con los niveles de acceso asignados.
     * @return El {@link Administrador} guardado con éxito, o null si ocurre una anomalía.
     */
    @Override
    @Transactional
    public Administrador registrar(Administrador administrador) {
        log.info("Iniciando persistencia de nuevo usuario administrador para el área: {}", administrador.getArea());
        try {
            Administrador guardado = repo.save(administrador);
            log.info("Administrador registrado exitosamente bajo el ID generado: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar el administrador: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Filtra el personal administrativo según su departamento o área de trabajo.
     * @param area Departamento institucional a consultar.
     * @return Lista de objetos {@link Administrador}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Administrador> listarPorArea(String area) {
        log.info("Filtrando personal administrador por el área institucional: {}", area);
        return repo.findByAreaIgnoreCase(area);
    }
}
