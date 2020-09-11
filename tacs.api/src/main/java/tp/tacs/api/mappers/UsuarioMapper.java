package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.model.UsuarioModel;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioMapper {

    public UsuarioModel toModel(Usuario entity) {
        return new UsuarioModel().id(entity.getId()).nombreDeUsuario(entity.getNombre());
    }

    public List<UsuarioModel> mapearUsuarios(List<Usuario> usuarios) {
        List<UsuarioModel> usuarioModels = new ArrayList<>();
        usuarios.forEach(usuario -> usuarioModels.add(this.toModel(usuario)));
        return usuarioModels;
    }
}
