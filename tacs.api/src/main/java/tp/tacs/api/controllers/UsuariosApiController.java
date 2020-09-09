package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.dominio.usuario.RepoUsuarios;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.ListarUsuariosResponse;
import tp.tacs.api.model.UsuarioModel;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsuariosApiController implements UsuariosApi {

    Utils utils = new Utils();
    RepoUsuarios repoUsuarios = RepoUsuarios.instance();
    UsuarioMapper usuarioMapper = new UsuarioMapper();

    public void setRepoUsuarios(RepoUsuarios repoUsuarios) {
        this.repoUsuarios = repoUsuarios;
    }

    @Override
    public ResponseEntity<ListarUsuariosResponse> listarUsuarios(@Valid String filter, @Valid Long tamanioPagina, @Valid Long pagina) {
        List<Usuario> usuarios = this.repoUsuarios.getUsuarios();
        if (!filter.isBlank()) {
            usuarios = usuarios.stream().filter(usuario -> usuario.mismoNombre(filter)).collect(Collectors.toList());
        }
        List<UsuarioModel> usuarioModels =
                usuarios.stream().map(usuario -> this.usuarioMapper.toModel(usuario)).collect(Collectors.toList());


        /*
        usuarioModels.add(new UsuarioModel().id(1L).nombreDeUsuario("Nico"));
        usuarioModels.add(new UsuarioModel().id(1L).nombreDeUsuario("Franco"));
        usuarioModels.add(new UsuarioModel().id(1L).nombreDeUsuario("Alejo"));
        usuarioModels.add(new UsuarioModel().id(1L).nombreDeUsuario("Pablo"));
        usuarioModels.add(new UsuarioModel().id(1L).nombreDeUsuario("Nico2"));
        usuarioModels.add(new UsuarioModel().id(1L).nombreDeUsuario("Juan"));

         */

        List<UsuarioModel> listaPaginada = this.utils.obtenerListaPaginada(pagina, tamanioPagina, usuarioModels);

        if(listaPaginada == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new ListarUsuariosResponse().usuarios(listaPaginada));
    }
}
