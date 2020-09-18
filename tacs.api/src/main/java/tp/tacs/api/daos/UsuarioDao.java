package tp.tacs.api.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioDao implements Dao<Usuario> {

    private List<Usuario> usuarios;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @PostConstruct
    private void postConstruct() {
        usuarios = new ArrayList<>();
    }

    @Override
    public Usuario get(Long id) {
        return usuarios.stream().filter(usuario -> usuario.getId().equals(id)).findFirst().orElse(null);
    }

    public Usuario getByGoogleId(String googleId) {
        return usuarios.stream().filter(usuario -> usuario.getGoogleId().equals(googleId)).findFirst().orElse(null);
    }

    public Usuario getByUsername(String username) {
        return usuarios.stream().filter(usuario -> usuario.getNombre().equals(username)).findFirst().orElse(null);
    }

    @Override
    public List<Usuario> getAll() {
        return usuarios;
    }

    @Override
    public void save(Usuario element) {
        usuarios.add(element);
    }

    @Override
    public void delete(Usuario element) {
        usuarios = usuarios.stream().filter(usuario -> !usuario.getId().equals(element.getId())).collect(Collectors.toList());
    }

    public List<Usuario> getByIds(List<Long> idsUsuarios) {
        return usuarios.stream().filter(municipio -> idsUsuarios.contains(municipio.getId())).collect(Collectors.toList());
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
        usuarios.forEach(idUsuario -> estadisticasDeUsuarioModels.add(estadisticas(idUsuario.getId())));
        return estadisticasDeUsuarioModels;
    }

}