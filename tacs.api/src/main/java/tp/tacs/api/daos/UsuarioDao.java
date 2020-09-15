package tp.tacs.api.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class UsuarioDao implements Dao<Usuario> {

    private Map<Long, Usuario> usuarios;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @PostConstruct
    private void postConstruct() {
        usuarios = new HashMap<>();
    }

    @Override
    public Usuario get(Long id) {
        return usuarios.get(id);
    }

    public Usuario getByGoogleId(String googleId) {
        return usuarios
                .values()
                .stream()
                .filter(usuario -> usuario.getGoogleId().equals(googleId))
                .findFirst()
                .orElse(null);
    }

    public Usuario getByUsername(String username) {
        return usuarios
                .values()
                .stream()
                .filter(usuario -> usuario.getNombre().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Usuario> getAll() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public void save(Usuario element) {
        usuarios.put(element.getId(), element);
    }

    @Override
    public void delete(Usuario element) {
        usuarios.remove(element.getId());
    }

    public List<Usuario> getSegunIds(List<Long> idsUsuarios) {
        List<Usuario> usuarios = new ArrayList<>();
        idsUsuarios.forEach(id -> usuarios.add(this.get(id)));
        return usuarios;
    }

    public EstadisticasDeUsuarioModel estadisticas(Long idUsuario) {
        Usuario usuario = this.get(idUsuario);
        return new EstadisticasDeUsuarioModel()
                .usuario(this.usuarioMapper.wrap(usuario))
                .partidasJugadas(usuario.getPartidasJugadas())
                .partidasGanadas(usuario.getPartidasGanadas())
                .rachaActual(usuario.getRachaActual());
    }

    public List<EstadisticasDeUsuarioModel> scoreBoard() {
        List<EstadisticasDeUsuarioModel> estadisticasDeUsuarioModels = new ArrayList<>();
        usuarios.keySet().forEach(idUsuario -> estadisticasDeUsuarioModels.add(this.estadisticas(idUsuario)));
        return estadisticasDeUsuarioModels;
    }

}