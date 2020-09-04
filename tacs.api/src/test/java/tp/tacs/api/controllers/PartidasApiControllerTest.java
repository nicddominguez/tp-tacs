package tp.tacs.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class PartidasApiControllerTest {

    @InjectMocks
    private PartidasApiController partidasApiController;

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void actualizarEstadoPartida() {
        ResponseEntity<Void> response = partidasApiController.actualizarEstadoPartida(1, EstadoDeJuegoModel.CANCELADA);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void atacarMunicipio() {
        ResponseEntity<AtacarMunicipioResponse> response = partidasApiController.atacarMunicipio(1, new AtacarMunicipioBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void crearPartida() {
        ResponseEntity<Void> response = partidasApiController.crearPartida(new CrearPartidaBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getPartida() {
        ResponseEntity<PartidaModel> response = partidasApiController.getPartida(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void listarPartidas() {
        ResponseEntity<List<PartidaModel>> response = partidasApiController.listarPartidas(1, null,null, EstadoDeJuegoModel.CANCELADA, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void moverGauchos() {
        ResponseEntity<MoverGauchosResponse> response = partidasApiController.moverGauchos(1, new MoverGauchosBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}