package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.model.UsuarioModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuariosApiController implements UsuariosApi {

    @Override
    public ResponseEntity<List<UsuarioModel>> listarUsuarios(@NotNull @Valid Integer page, @Valid String filter, @Valid Integer pageSize) {
        List<UsuarioModel> usuarioModels = new ArrayList<>();

        usuarioModels.add(new UsuarioModel().id(1).nombreDeUsuario("Nico"));
        usuarioModels.add(new UsuarioModel().id(1).nombreDeUsuario("Franco"));
        usuarioModels.add(new UsuarioModel().id(1).nombreDeUsuario("Alejo"));
        usuarioModels.add(new UsuarioModel().id(1).nombreDeUsuario("Pablo"));
        usuarioModels.add(new UsuarioModel().id(1).nombreDeUsuario("Nico2"));
        usuarioModels.add(new UsuarioModel().id(1).nombreDeUsuario("Juan"));

        int start = page * pageSize;
        int end = start + pageSize;

        if(start > usuarioModels.size() || end > usuarioModels.size() || start < 0 || end <= 0)
            return ResponseEntity.notFound().build();
        List<UsuarioModel> usuariosPaginados = usuarioModels.subList(start, end);

        return ResponseEntity.ok(usuariosPaginados);
    }
}
