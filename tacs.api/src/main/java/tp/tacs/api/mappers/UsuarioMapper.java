package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.http.wrappers.AbstractWrapper;
import tp.tacs.api.model.UsuarioModel;

@Component
public class UsuarioMapper extends AbstractWrapper<Usuario, UsuarioModel> {

    @Override protected UsuarioModel wrapModel(Usuario model) {
        return new UsuarioModel().id(model.getId()).nombreDeUsuario(model.getNombre());
    }

    @Override protected Usuario unwrapModel(UsuarioModel model) {
        return null;
    }
}
