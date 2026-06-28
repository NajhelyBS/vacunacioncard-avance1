package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.repository.vacunacion.RegistroVacunaRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.IRegistroVacunaService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para el control de los Registros de Vacunas aplicadas.
 * Gestiona el almacenamiento de los datos de inoculación individual, vinculando la 
 * información logística de lotes, el número de dosis y el personal médico responsable.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegistroVacunaServiceImpl implements IRegistroVacunaService {

    private final RegistroVacunaRepository repo;

    /**
     * Recupera un listado global con todos los registros de inoculaciones guardados en el sistema.
     *
     * @return {@link List} que aloja la totalidad de las entidades {@link RegistroVacuna}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroVacuna> listarTodos() {
        log.info("Listando todos los registros de vacunas");
        return repo.findAll();
    }

    /**
     * Busca la información detallada de una inoculación específica mediante su identificador único.
     *
     * @param id Identificador único del registro de vacuna.
     * @return Un {@link Optional} con el {@link RegistroVacuna} si existe en el repositorio,
     *         o un contenedor vacío si no hay coincidencias.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegistroVacuna> obtenerPorId(Long id) {
        log.info("Buscando registro de vacuna por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Almacena o actualiza la cartilla física de inoculación con los detalles de la dosis aplicada.
     *
     * @param registro Entidad {@link RegistroVacuna} que contiene el lote, dosis y relaciones médicas.
     * @return La entidad {@link RegistroVacuna} guardada con su identificador único autogenerado.
     * @throws ServiceException Si ocurre una anomalía de persistencia o fallo al acceder al repositorio.
     */
    @Override
    @Transactional
    public RegistroVacuna guardar(RegistroVacuna registro) {
        log.info("Guardando registro de dosis N° {} para vacuna ID: {}",
                registro.getNumeroDosis(),
                registro.getVacuna() != null ? registro.getVacuna().getId() : "N/A");
        try {
            RegistroVacuna guardado = repo.save(registro);
            log.info("Registro de vacuna guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar registro de vacuna", e);
        }
    }

    /**
     * Filtra los registros de inoculaciones según el código identificador de lote de fabricación.
     *
     * @param lote Código alfanumérico del lote de vacunas
     * @return {@link List} de entidades {@link RegistroVacuna} vinculadas al lote consultado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroVacuna> listarPorLote(String lote) {
        log.info("Listando registros por lote: {}", lote);
        return repo.findByLoteIgnoreCase(lote);
    }

    /**
     * Obtiene el historial de vacunas aplicadas por un miembro específico del personal de enfermería
     *
     * @param enfermeroId Identificador único del enfermero aplicador.
     * @return {@link List} de entidades {@link RegistroVacuna} inyectadas por el profesional.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegistroVacuna> listarPorEnfermero(Long enfermeroId) {
        log.info("Listando registros del enfermero ID: {}", enfermeroId);
        return repo.findByEnfermeroAplicadorId(enfermeroId);
    }
}
