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
        if (filter != null) {
            usuarios = usuarios.stream().filter(usuario -> usuario.mismoNombre(filter)).collect(Collectors.toList());
        }
        List<UsuarioModel> usuarioModels =
                usuarios.stream().map(usuario -> this.usuarioMapper.toModel(usuario)).collect(Collectors.toList());

        List<UsuarioModel> listaPaginada = this.utils.obtenerListaPaginada(pagina, tamanioPagina, usuarioModels);

        return ResponseEntity.ok(new ListarUsuariosResponse().usuarios(listaPaginada));
    }
}
