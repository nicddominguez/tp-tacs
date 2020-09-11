package tp.tacs.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.partida.RepoPartidas;
import tp.tacs.api.dominio.usuario.RepoUsuarios;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.model.ScoreboardResponse;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class AdminApiControllerTest {

    Usuario usuario0 = new Usuario(0l, "mail0@mock.com", "mock0");
    Usuario usuario1 = new Usuario(1l, "mail1@mock.com", "mock1");

    Partida partida;
    List<Usuario> usuarios = new ArrayList<>();
    List<Municipio> municipios = new ArrayList<>();

    Date fechaCreacion = new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime();
    Date fechaInicio = new GregorianCalendar(2018, Calendar.FEBRUARY, 11).getTime();
    Date fechaFin = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();

    private AdminApiController adminController = new AdminApiController();

    RepoUsuarios repoUsuarios = new RepoUsuarios();
    RepoPartidas repoPartidas = new RepoPartidas();


    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);

        usuario0.setPartidasJugadas(1L);
        usuario0.setPartidasGanadas(1L);
        usuario0.setRachaActual(1L);
        usuario1.setPartidasJugadas(2L);
        usuario1.setPartidasGanadas(1L);
        usuario1.setRachaActual(0L);
        repoUsuarios.agregarUsuario(usuario0);
        repoUsuarios.agregarUsuario(usuario1);
        adminController.setRepoUsuarios(repoUsuarios);

        partida = new Partida(usuarios, Estado.EN_CURSO, null, municipios, null, null);
        partida.setFechaCreacion(fechaCreacion);
        repoPartidas.agregarPartida(partida);
        adminController.setRepoPartidas(repoPartidas);
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