package tp.tacs.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tp.tacs.api.dominio.usuario.RepoUsuarios;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.model.ListarUsuariosResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class UsuariosApiControllerTest {

    Usuario usuario0 = new Usuario(0l, "mail0@mock.com", "mock0");
    Usuario usuario1 = new Usuario(1l, "mail1@mock.com", "mock1");
    RepoUsuarios repoUsuarios = new RepoUsuarios();

    private UsuariosApiController usuariosApiController = new UsuariosApiController();

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
        usuariosApiController.setRepoUsuarios(repoUsuarios);
    }

    @Test
    void listarUsuariosSinFiltro() {
        ResponseEntity<ListarUsuariosResponse> usuariosResponse = usuariosApiController.listarUsuarios(null, 5L, 0L);
        assertEquals(HttpStatus.OK, usuariosResponse.getStatusCode());
    }

    @Test
    void listarUsuariosConFiltro() {
        ResponseEntity<ListarUsuariosResponse> usuariosResponse = usuariosApiController.listarUsuarios("mock0", 5L, 0L);
        assertEquals(0L, usuariosResponse.getBody().getUsuarios().get(0).getId());
        assertEquals(HttpStatus.OK, usuariosResponse.getStatusCode());
    }

}