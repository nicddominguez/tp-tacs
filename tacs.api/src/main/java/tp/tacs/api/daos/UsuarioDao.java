package tp.tacs.api.daos;

import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;

import java.util.List;

public interface UsuarioDao extends Dao<Usuario> {

    Usuario getByGoogleId(String googleId);

    Usuario getByUsername(String username);

    List<Usuario> getByIds(List<String> idsUsuarios);

    EstadisticasDeUsuarioModel estadisticas(String idUsuario);

    Usuario get(String id);

    List<EstadisticasDeUsuarioModel> scoreBoard();

    Long cantidadTotalUsuarios();
}
