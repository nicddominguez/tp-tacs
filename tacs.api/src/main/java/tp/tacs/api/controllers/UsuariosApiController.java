package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.model.Usuario;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuariosApiController implements UsuariosApi {

    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios(@NotNull @Valid Integer page, @Valid String filter, @Valid Integer pageSize) {
        List<Usuario> usuarios = new ArrayList<>();

        usuarios.add(new Usuario().id(1).nombreDeUsuario("Nico"));
        usuarios.add(new Usuario().id(1).nombreDeUsuario("Franco"));
        usuarios.add(new Usuario().id(1).nombreDeUsuario("Alejo"));
        usuarios.add(new Usuario().id(1).nombreDeUsuario("Pablo"));
        usuarios.add(new Usuario().id(1).nombreDeUsuario("Nico2"));
        usuarios.add(new Usuario().id(1).nombreDeUsuario("Juan"));

        int start = page * pageSize;
        int end = start + pageSize;

        if(start > usuarios.size() || end > usuarios.size() || start < 0 || end <= 0)
            return ResponseEntity.notFound().build();
        List<Usuario> usuariosPaginados = usuarios.subList(start, end);

        return ResponseEntity.ok(usuariosPaginados);
    }
}
