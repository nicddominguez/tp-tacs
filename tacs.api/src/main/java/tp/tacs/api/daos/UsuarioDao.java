package tp.tacs.api.daos;

import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;

import java.util.List;

public interface UsuarioDao extends Dao<Usuario> {

    Usuario getByGoogleId(String googleId);

    Usuario getByUsername(String username);

    List<Usuario> getByIds(List<Long> idsUsuarios);

    EstadisticasDeUsuarioModel estadisticas(Long idUsuario);

    List<EstadisticasDeUsuarioModel> scoreBoard();

}
