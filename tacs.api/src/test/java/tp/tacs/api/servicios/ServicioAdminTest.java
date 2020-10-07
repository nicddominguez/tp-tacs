package tp.tacs.api.servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioAdminTest {

    private ServicioAdmin servicioAdmin;

    private Utils utils = new Utils();
    @Spy
    private UsuarioDao usuarioDao;
    @Spy
    private PartidaDao partidaDao;

    Date fechaInicio = new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime();
    Date fechaFin = new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        doReturn(new EstadisticasDeJuegoModel()).when(partidaDao).estadisticas(fechaInicio, fechaFin);
        doReturn(new EstadisticasDeUsuarioModel()).when(usuarioDao).estadisticas(0L);
        doReturn(new ArrayList<>()).when(usuarioDao).scoreBoard();
        servicioAdmin = new ServicioAdmin(utils, usuarioDao, partidaDao);
    }

    @Test
    void estadisticasDeJuego() {
        servicioAdmin.estadisticasDeJuego(fechaInicio, fechaFin);
        verify(partidaDao, times(1)).estadisticas(fechaInicio, fechaFin);
    }

    @Test
    void estadisticasDeUsuario() {
        servicioAdmin.estadisticasDeUsuario(0L);
        verify(usuarioDao, times(1)).estadisticas(0L);
    }

    @Test
    void tablaDePuntos() {
        servicioAdmin.tablaDePuntos(0L, 1L);
        verify(usuarioDao, times(1)).scoreBoard();
    }
}