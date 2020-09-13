package tp.tacs.api.controllers;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.model.ScoreboardResponse;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
class AdminApiControllerTest {

    Usuario usuario0 = new Usuario(0l, "mail0@mock.com", "mock0");
    Usuario usuario1 = new Usuario(1l, "mail1@mock.com", "mock1");

    Partida partida;
    List<Usuario> usuarios = new ArrayList<>();

    @Spy
    Municipio municipioA;
    @Spy
    Municipio municipioB;
    List<Municipio> municipios = new ArrayList<>();

    Date fechaCreacion = new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime();
    Date fechaInicio = new GregorianCalendar(2018, Calendar.FEBRUARY, 11).getTime();
    Date fechaFin = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();

    private AdminApiController adminController = new AdminApiController();

    PartidaDao partidaDao = new PartidaDao();

    UsuarioDao usuarioDao = new UsuarioDao();

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);

        usuario0.setPartidasJugadas(1L);
        usuario0.setPartidasGanadas(1L);
        usuario0.setRachaActual(1L);
        usuario1.setPartidasJugadas(2L);
        usuario1.setPartidasGanadas(1L);
        usuario1.setRachaActual(0L);
        usuarioDao.save(usuario0);
        usuarioDao.save(usuario1);
        adminController.setUsuarioDao(usuarioDao);

        mockearMunicipio(municipioA, 500d, 0d, 10f, usuario0);
        mockearMunicipio(municipioB, 0d, 0d, 110f, usuario1);
        municipios.add(municipioA);
        municipios.add(municipioB);

        partida = new Partida(usuarios, Estado.EN_CURSO, null, municipios, null, null);
        partida.setFechaCreacion(fechaCreacion);
        adminController.setPartidaDao(partidaDao);
    }

    @AfterEach
    public void after() {
        partidaDao.limpiar();
    }

    private void mockearMunicipio(Municipio municipio, double latitud, double longitud, float altura, Usuario duenio) {
        doReturn(latitud).when(municipio).getLatitud();
        doReturn(longitud).when(municipio).getLongitud();
        ArrayList<Double> coordenadasA = Lists.newArrayList(municipio.getLatitud(), municipio.getLongitud());
        doReturn(coordenadasA).when(municipio).getCoordenadas();
        doReturn(altura).when(municipio).getAltura();
        doNothing().when(municipio).producir();
        doReturn(duenio).when(municipio).getDuenio();
    }

    @Test
    void getEstadisticas() {
        ResponseEntity<EstadisticasDeJuegoModel> estadisticasDeJuegoResponseEntity = adminController.getEstadisticas(fechaInicio, fechaFin);
        assertEquals(1L, estadisticasDeJuegoResponseEntity.getBody().getPartidasCreadas());
        assertEquals(1L, estadisticasDeJuegoResponseEntity.getBody().getPartidasEnCurso());
        assertEquals(0L, estadisticasDeJuegoResponseEntity.getBody().getPartidasCanceladas());
        assertEquals(0L, estadisticasDeJuegoResponseEntity.getBody().getPartidasTerminadas());
        assertEquals(HttpStatus.OK, estadisticasDeJuegoResponseEntity.getStatusCode());
    }

    @Test
    void getEstadisticasDeUsuario() {
        ResponseEntity<EstadisticasDeUsuarioModel> estadisticasDeUsuarioResponseEntity = adminController.getEstadisticasDeUsuario(0L);
        assertEquals(1L, estadisticasDeUsuarioResponseEntity.getBody().getPartidasJugadas());
        assertEquals(1L, estadisticasDeUsuarioResponseEntity.getBody().getPartidasGanadas());
        assertEquals(1L, estadisticasDeUsuarioResponseEntity.getBody().getRachaActual());
        assertEquals(HttpStatus.OK, estadisticasDeUsuarioResponseEntity.getStatusCode());
    }

    @Test
    void getScoreboard() {
        ResponseEntity<ScoreboardResponse> scoreboard = adminController.getScoreboard(2L, 0L);
        assertEquals(1L, scoreboard.getBody().getScoreboard().get(0).getPartidasJugadas());
        assertEquals(1L, scoreboard.getBody().getScoreboard().get(0).getPartidasGanadas());
        assertEquals(1L, scoreboard.getBody().getScoreboard().get(0).getRachaActual());
        assertEquals(HttpStatus.OK, scoreboard.getStatusCode());
    }

}