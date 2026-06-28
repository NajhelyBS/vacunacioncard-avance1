package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.repository.usuario.PacienteRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IPacienteService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de datos e historiales de los Pacientes.
 * Administra el núcleo operativo del sistema relacionado con la identificación de ciudadanos,
 * el control de su estado activo en el padrón de vacunación y su vinculación clínica.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements IPacienteService {

    private final PacienteRepository repo;

    /**
     * Recupera un listado global con la totalidad de los pacientes registrados en el sistema.
     *
     * @return {@link List} que contiene todas las entidades {@link Paciente}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Paciente> listarTodos() {
        log.info("Listando todos los pacientes");
        return repo.findAll();
    }

    /**
     * Busca la información detallada de un paciente utilizando su identificador interno único.
     *
     * @param id Identificador único asignado al paciente en la base de datos.
     * @return Un {@link Optional} que contiene al {@link Paciente} si existe, 
     *         o un contenedor vacío si no se registra la coincidencia.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorId(Long id) {
        log.info("Buscando paciente por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Inserta un nuevo paciente en el padrón nacional o actualiza sus datos de filiación.
     *
     * @param paciente Entidad {@link Paciente} con los datos de identidad y contacto a persistir.
     * @return La entidad {@link Paciente} guardada con su identificador interno autogenerado.
     * @throws ServiceException Si ocurre una anomalía de persistencia o restricción de unicidad con el DNI.
     */
    @Override
    @Transactional
    public Paciente registrar(Paciente paciente) {
        log.info("Registrando paciente DNI: {}", paciente.getDni());
        try {
            Paciente guardado = repo.save(paciente);
            log.info("Paciente registrado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar paciente DNI: " + paciente.getDni(), e);
        }
    }

    /**
     * Localiza a un paciente empleando su Documento Nacional de Identidad (DNI).
     *
     * @param dni Cadena única de 8 caracteres numéricos correspondiente a la identidad ciudadana.
     * @return Un {@link Optional} con el {@link Paciente} hallado, o vacío si el DNI no está registrado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorDni(String dni) {
        log.info("Buscando paciente por DNI: {}", dni);
        return repo.findByDni(dni);
    }

    /**
     * Recupera la ficha de un paciente mediante el número o código único de su Historia Clínica.
     *
     * @param historiaClinicaId Identificador alfanumérico único correspondiente al historial médico.
     * @return Un {@link Optional} que envuelve al {@link Paciente} asociado, o un contenedor vacío.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> obtenerPorHistoriaClinica(String historiaClinicaId) {
        log.info("Buscando paciente por historia clínica: {}", historiaClinicaId);
        return repo.findByHistoriaClinicaId(historiaClinicaId);
    }

    /**
     * Filtra los pacientes registrados según su vigencia o estado lógico activo en el sistema.
     *
     * @param activo Criterio de filtrado: {@code true} para ciudadanos habilitados en el padrón,
     *               {@code false} para aquellos inactivos o dados de baja.
     * @return {@link List} de entidades {@link Paciente} que coinciden con el estado enviado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Paciente> listarPorEstado(boolean activo) {
        log.info("Listando pacientes por estado activo: {}", activo);
        return repo.findByActivo(activo);
    }
}
