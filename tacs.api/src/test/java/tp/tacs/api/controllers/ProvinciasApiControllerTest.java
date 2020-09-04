package tp.tacs.api.controllers;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        ResponseEntity<List<ProvinciaModel>> provincias = provinciasApiController.listarProvincias(0, 10);
        assertEquals(HttpStatus.OK, provincias.getStatusCode());
    }

    @Test
    public void getProvinciasFail() {
        ResponseEntity<List<ProvinciaModel>> provincias = provinciasApiController.listarProvincias(0, 0);
        assertEquals(HttpStatus.NOT_FOUND, provincias.getStatusCode());
    }

    @Test
    public void getProvinciasFail1() {
        ResponseEntity<List<ProvinciaModel>> provincias = provinciasApiController.listarProvincias(-1, -2);
        assertEquals(HttpStatus.NOT_FOUND, provincias.getStatusCode());
    }

    @Test
    public void getProvinciasFail2() {
        ResponseEntity<List<ProvinciaModel>> provincias = provinciasApiController.listarProvincias(100, 2);
        assertEquals(HttpStatus.NOT_FOUND, provincias.getStatusCode());
    }

}
