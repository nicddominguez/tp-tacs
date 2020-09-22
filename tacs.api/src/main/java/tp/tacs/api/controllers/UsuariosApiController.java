package tp.tacs.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.ListarUsuariosResponse;
import tp.tacs.api.model.UsuarioModel;
import tp.tacs.api.servicios.ServicioUsuario;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsuariosApiController implements UsuariosApi {

    @Autowired
    private Utils utils;
    @Autowired
    private ServicioUsuario servicioUsuario;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public ResponseEntity<ListarUsuariosResponse> listarUsuarios(@Valid String filter, @Valid Long tamanioPagina, @Valid Long pagina) {
        List<Usuario> usuarios = this.servicioUsuario.listarUsuarios(filter);
        List<UsuarioModel> usuarioModels = usuarioMapper.wrapList(usuarios);
        //TODO: el ordenado lo deberia hacer la db (la logiaga deberia estar en el dao)
        usuarioModels.sort(Comparator.comparing(UsuarioModel::getNombreDeUsuario));
        List<UsuarioModel> listaPaginada = utils.obtenerListaPaginada(pagina, tamanioPagina, usuarioModels);

        Long cantidadTotalDeUsuarios = Long.valueOf(usuarios.size());

        return ResponseEntity.ok(new ListarUsuariosResponse().usuarios(listaPaginada));
    }
}
