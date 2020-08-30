package tp.tacs.api.controllers;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.controllers.ProvinciasApiController;
import tp.tacs.api.model.Provincia;

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
    public void getProvincias(){
        ResponseEntity<List<Provincia>> provincias = provinciasApiController.listarProvincias(0,10);
        assertEquals(HttpStatus.OK, provincias.getStatusCode());
    }
}
