package tp.tacs.api.daos;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;

import java.util.*;

@Component
public class UsuarioDao implements Dao<Usuario> {

    private static final Map<Long, Usuario> usuarios = new HashMap<>();

    private final UsuarioMapper usuarioMapper = new UsuarioMapper();

    @Override
    public synchronized Usuario get(Long id) {
        return UsuarioDao.usuarios.get(id);
    }

    public synchronized Usuario getByGoogleId(String googleId) {
        return UsuarioDao.usuarios
                .values()
                .stream()
                .filter(usuario -> usuario.getGoogleId().equals(googleId))
                .findFirst()
                .orElse(null);
    }

    public synchronized Usuario getByUsername(String username) {
        return UsuarioDao.usuarios
                .values()
                .stream()
                .filter(usuario -> usuario.getNombre().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized List<Usuario> getAll() {
        return new ArrayList<>(UsuarioDao.usuarios.values());
    }

    @Override
    public synchronized void save(Usuario element) {
        UsuarioDao.usuarios.put(element.getId(), element);
    }

    @Override
    public synchronized void delete(Usuario element) {
        UsuarioDao.usuarios.remove(element.getId());
    }

    public synchronized List<Usuario> getSegunIds(List<Long> idsUsuarios) {
        List<Usuario> usuarios = new ArrayList<>();
        idsUsuarios.forEach(id -> usuarios.add(this.get(id)));
        return usuarios;
    }

    public synchronized EstadisticasDeUsuarioModel estadisticas(Long idUsuario) {
        Usuario usuario = this.get(idUsuario);
        return new EstadisticasDeUsuarioModel()
                .usuario(this.usuarioMapper.wrap(usuario))
                .partidasJugadas(usuario.getPartidasJugadas())
                .partidasGanadas(usuario.getPartidasGanadas())
                .rachaActual(usuario.getRachaActual());
    }

    public synchronized List<EstadisticasDeUsuarioModel> scoreBoard() {
        List<EstadisticasDeUsuarioModel> estadisticasDeUsuarioModels = new ArrayList<>();
        UsuarioDao.usuarios
                .keySet()
                .forEach(idUsuario -> estadisticasDeUsuarioModels.add(this.estadisticas(idUsuario)));
        return estadisticasDeUsuarioModels;
    }

}