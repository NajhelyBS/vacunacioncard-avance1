package pe.edu.utp.vacunacioncard.service.vacunacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.vacunacion.Laboratorio;
import pe.edu.utp.vacunacioncard.repository.vacunacion.LaboratorioRepository;
import pe.edu.utp.vacunacioncard.service.vacunacion.ILaboratorioService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la administración del catálogo de laboratorios farmacéuticos.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LaboratorioServiceImpl implements ILaboratorioService {

    private final LaboratorioRepository repo;

    /***
     * Recupera todos los laboratorios farmacéuticos registrados en el sistema con control de lectura.
     * @return Lista de objetos {@link Laboratorio}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Laboratorio> listarTodos() {
        log.info("Ejecutando consulta del catálogo global de laboratorios farmacéuticos");
        return repo.findAll();
    }

    /***
     * Obtiene un laboratorio específico mediante su identificador único con control de lectura.
     * @param id El identificador único del laboratorio.
     * @return Un {@link Optional} que contiene el laboratorio si es hallado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Laboratorio> obtenerPorId(Long id) {
        log.info("Buscando registro de laboratorio por ID: {}", id);
        return repo.findById(id);
    }

    /***
     * Registra un nuevo laboratorio farmacéutico o actualiza su procedencia con control de persistencia y manejo de excepciones.
     * @param laboratorio El objeto {@link Laboratorio} con los datos de fabricación a guardar
     * @return El {@link Laboratorio} guardado con éxito, o null si ocurre un fallo crítico.
     */
    @Override
    @Transactional
    public Laboratorio registrar(Laboratorio laboratorio) {
        log.info("Iniciando almacenamiento del laboratorio fabricante: {}", laboratorio.getNombre());
        try {
            Laboratorio guardado = repo.save(laboratorio);
            log.info("Laboratorio registrado exitosamente asignando el ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar el laboratorio farmacéutico: {}", e.getMessage());
            return null;
        }
    }

    /***
     * Filtra el catálogo maestro de laboratorios de acuerdo a su país de origen con control de lectura.
     * @param pais Nombre del país a consultar.
     * @return Lista de objetos {@link Laboratorio}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Laboratorio> listarPorPais(String pais) {
        log.info("Filtrando catálogo de laboratorios por país de origen: {}", pais);
        return repo.findByPaisOrigenIgnoreCase(pais);
    }
}

