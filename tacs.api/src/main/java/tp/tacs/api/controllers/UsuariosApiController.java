package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.model.ListarUsuariosResponse;
import tp.tacs.api.model.UsuarioModel;

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

        Long start = pagina * tamanioPagina;
        Long end = start + tamanioPagina;

        if(start > usuarioModels.size() || end > usuarioModels.size() || start < 0 || end <= 0)
            return ResponseEntity.notFound().build();

        List<UsuarioModel> usuariosPaginados = usuarioModels.subList(start.intValue(), end.intValue());

        return ResponseEntity.ok(new ListarUsuariosResponse().usuarios(usuariosPaginados));
    }
}
