package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.model.UsuarioModel;

@Component
public class UsuarioMapper {

    public UsuarioModel toModel(Usuario entity) {
        return new UsuarioModel().id(entity.getId()).nombreDeUsuario(entity.getNombre());
    }
}
