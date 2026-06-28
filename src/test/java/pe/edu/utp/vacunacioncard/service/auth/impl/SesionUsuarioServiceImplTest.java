package pe.edu.utp.vacunacioncard.service.auth.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.SesionUsuarioRepository;
import pe.edu.utp.vacunacioncard.service.patron.singleton.ConfiguracionSistema;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service - SesionUsuarioServiceImpl")
class SesionUsuarioServiceImplTest {

    @Mock
    private SesionUsuarioRepository repo;

    @InjectMocks
    private SesionUsuarioServiceImpl service;

    @BeforeEach
    void setUp() {
        // Inicializa y limpia el Singleton antes de cada test para asegurar que 
        // las reglas de negocio de bloqueo usen los valores correctos (maxIntentos = 3)
        ConfiguracionSistema.getInstancia().reset();
    }

    @Test
    @DisplayName("Crear sesión guarda y retorna correctamente")
    void crearSesion() {
        SesionUsuario sesion = SesionUsuario.builder().token("abc123").activa(true).build();
        when(repo.save(sesion)).thenReturn(sesion);

        SesionUsuario resultado = service.crearSesion(sesion);

        assertNotNull(resultado);
        assertEquals("abc123", resultado.getToken());
        
        // Verifica que interactúe con el repositorio
        verify(repo, times(1)).save(sesion);
    }

    @Test
    @DisplayName("Obtener por token retorna la sesión si existe")
    void obtenerPorToken_existe() {
        SesionUsuario sesion = SesionUsuario.builder().token("token123").build();
        when(repo.findByToken("token123")).thenReturn(Optional.of(sesion));

        Optional<SesionUsuario> resultado = service.obtenerPorToken("token123");

        assertTrue(resultado.isPresent());
        verify(repo, times(1)).findByToken("token123");
    }

    @Test
    @DisplayName("Obtener por token retorna vacío si no existe")
    void obtenerPorToken_noExiste() {
        when(repo.findByToken("inexistente")).thenReturn(Optional.empty());

        assertTrue(service.obtenerPorToken("inexistente").isEmpty());
        verify(repo, times(1)).findByToken("inexistente");
    }

    @Test
    @DisplayName("Cerrar sesión la desactiva")
    void cerrarSesion() {
        SesionUsuario sesion = SesionUsuario.builder().id(1L).activa(true).token("t").build();
        when(repo.findById(1L)).thenReturn(Optional.of(sesion));
        when(repo.save(any(SesionUsuario.class))).thenReturn(sesion); 

        service.cerrarSesion(1L);

        assertFalse(sesion.isActiva());
        verify(repo, times(1)).findById(1L);
        verify(repo, times(1)).save(sesion);
    }

    @Test
    @DisplayName("Verificar bloqueo retorna true si excede intentos")
    void verificarBloqueo_excede() {
        
        assertTrue(service.verificarBloqueoCuenta(3));
        assertTrue(service.verificarBloqueoCuenta(10));
    }

    @Test
    @DisplayName("Verificar bloqueo retorna false dentro del límite")
    void verificarBloqueo_dentro() {
        assertFalse(service.verificarBloqueoCuenta(0));
        assertFalse(service.verificarBloqueoCuenta(2));
    }
}
