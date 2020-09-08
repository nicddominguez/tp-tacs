package tp.tacs.api.controllers;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.model.ListarProvinciasResponse;
import tp.tacs.api.model.ProvinciaModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProvinciasApiControllerTest {
    @InjectMocks
    private ProvinciasApiController provinciasApiController;

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getProvinciasOk() {
        ResponseEntity<ListarProvinciasResponse> provincias = provinciasApiController.listarProvincias(1L, 0L);
        assertEquals(HttpStatus.OK, provincias.getStatusCode());
    }

    @Test
    public void getProvinciasOk1() {
        //Este test está pensado para que retorne menos cantidad que el tamaño de pagina (porque no hay mas items)
        ResponseEntity<ListarProvinciasResponse> provincias = provinciasApiController.listarProvincias(10L, 2L);
        assertEquals(HttpStatus.OK, provincias.getStatusCode());
    }

    @Test
    public void getProvinciasFail() {
        ResponseEntity<ListarProvinciasResponse> provincias = provinciasApiController.listarProvincias(0L, 0L);
        assertEquals(HttpStatus.NOT_FOUND, provincias.getStatusCode());
    }

    @Test
    public void getProvinciasFail1() {
        ResponseEntity<ListarProvinciasResponse> provincias = provinciasApiController.listarProvincias(10L, -1L);
        assertEquals(HttpStatus.NOT_FOUND, provincias.getStatusCode());
    }

    @Test
    public void getProvinciasFail2() {
        ResponseEntity<ListarProvinciasResponse> provincias = provinciasApiController.listarProvincias(100L, 2L);
        assertEquals(HttpStatus.NOT_FOUND, provincias.getStatusCode());
    }

}
