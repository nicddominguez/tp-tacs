package tp.tacs.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.model.ListarUsuariosResponse;
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
        ResponseEntity<ListarUsuariosResponse> usuariosResponse = usuariosApiController.listarUsuarios("", 5L, 0L);
        assertEquals(HttpStatus.OK, usuariosResponse.getStatusCode());
    }

}