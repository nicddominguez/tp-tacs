package tp.tacs.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.threeten.bp.LocalDate;
import tp.tacs.api.model.EstadisticasDeJuego;
import tp.tacs.api.model.EstadisticasDeUsuario;

import java.util.List;

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
        ResponseEntity<EstadisticasDeJuego> estadisticasDeJuegoResponseEntity = adminController.getEstadisticas(LocalDate.now(), LocalDate.now());
        assertEquals(HttpStatus.OK, estadisticasDeJuegoResponseEntity.getStatusCode());
    }

    @Test
    void getEstadisticasDeUsuario() {
        ResponseEntity<EstadisticasDeUsuario> estadisticasDeUsuarioResponseEntity = adminController.getEstadisticasDeUsuario(1);
        assertEquals(HttpStatus.OK, estadisticasDeUsuarioResponseEntity.getStatusCode());
    }

    @Test
    void getScoreboard() {
        ResponseEntity<List<EstadisticasDeUsuario>> scoreboard = adminController.getScoreboard(0, 5);
        assertEquals(HttpStatus.OK, scoreboard.getStatusCode());
    }
}