package tp.tacs.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.model.UsuarioModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class UsuariosApiControllerTest {

    @InjectMocks
    private UsuariosApiController usuariosApiController;

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void listarUsuarios() {
        ResponseEntity<List<UsuarioModel>> usuariosResponse = usuariosApiController.listarUsuarios(0, null, 5);
        assertEquals(HttpStatus.OK, usuariosResponse.getStatusCode());
    }
}