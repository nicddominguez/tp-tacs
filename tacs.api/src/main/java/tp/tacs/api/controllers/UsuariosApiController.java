package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.model.ListarUsuariosResponse;
import tp.tacs.api.model.UsuarioModel;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuariosApiController implements UsuariosApi {

    @Override
    public ResponseEntity<ListarUsuariosResponse> listarUsuarios(@Valid String filter, @Valid Long tamanioPagina, @Valid Long pagina) {
        List<UsuarioModel> usuarioModels = new ArrayList<>();

        usuarioModels.add(new UsuarioModel().id(1L).nombreDeUsuario("Nico"));
        usuarioModels.add(new UsuarioModel().id(1L).nombreDeUsuario("Franco"));
        usuarioModels.add(new UsuarioModel().id(1L).nombreDeUsuario("Alejo"));
        usuarioModels.add(new UsuarioModel().id(1L).nombreDeUsuario("Pablo"));
        usuarioModels.add(new UsuarioModel().id(1L).nombreDeUsuario("Nico2"));
        usuarioModels.add(new UsuarioModel().id(1L).nombreDeUsuario("Juan"));

        Utils utils = new Utils();

        List<UsuarioModel> listaPaginada = utils.obtenerListaPaginada(pagina, tamanioPagina, usuarioModels);

        if(listaPaginada == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new ListarUsuariosResponse().usuarios(listaPaginada));
    }
}
