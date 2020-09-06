package tp.tacs.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.model.ScoreboardResponse;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class AdminApiControllerTest {

    @InjectMocks
    private AdminApiController adminController;

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getEstadisticas() {
        ResponseEntity<EstadisticasDeJuegoModel> estadisticasDeJuegoResponseEntity = adminController.getEstadisticas(new Date(), new Date());
        assertEquals(HttpStatus.OK, estadisticasDeJuegoResponseEntity.getStatusCode());
    }

    @Test
    void getEstadisticasDeUsuario() {
        ResponseEntity<EstadisticasDeUsuarioModel> estadisticasDeUsuarioResponseEntity = adminController.getEstadisticasDeUsuario(1L);
        assertEquals(HttpStatus.OK, estadisticasDeUsuarioResponseEntity.getStatusCode());
    }

    @Test
    void getScoreboard() {
        ResponseEntity<ScoreboardResponse> scoreboard = adminController.getScoreboard(1L, 0L);
        assertEquals(HttpStatus.OK, scoreboard.getStatusCode());
    }

}