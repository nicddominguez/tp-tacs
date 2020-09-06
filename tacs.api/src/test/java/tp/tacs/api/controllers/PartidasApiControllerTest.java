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
        ResponseEntity<Void> response = partidasApiController.actualizarEstadoPartida(1L, new PartidaModel());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void atacarMunicipio() {
        ResponseEntity<AtacarMunicipioResponse> response = partidasApiController.atacarMunicipio(1L, new AtacarMunicipioBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void crearPartida() {
        ResponseEntity<Void> response = partidasApiController.crearPartida(new CrearPartidaBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getPartida() {
        ResponseEntity<PartidaModel> response = partidasApiController.getPartida(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void listarPartidas() {
        ResponseEntity<ListarPartidasResponse> response = partidasApiController.listarPartidas(null, null, EstadoDeJuegoModel.ENPROGRESO, "", 10L, 0L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void moverGauchos() {
        ResponseEntity<MoverGauchosResponse> response = partidasApiController.moverGauchos(1L, new MoverGauchosBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}