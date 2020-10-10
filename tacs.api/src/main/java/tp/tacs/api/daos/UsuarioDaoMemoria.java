package tp.tacs.api.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository("usuarioDaoMemoria")
@ConditionalOnProperty(prefix="application", name="persistance-implementation", havingValue = "memoria")
public class UsuarioDaoMemoria implements UsuarioDao {

    private List<Usuario> usuarios;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @PostConstruct
    private void postConstruct() {
        usuarios = new ArrayList<>();
        Usuario nico = Usuario.builder().rachaActual(0L).partidasJugadas(0L).partidasGanadas(0L).isAdmin(false).googleId("").nombre("Nico").id("90L").mail("asd@gmail.com").build();
        Usuario juan = Usuario.builder().rachaActual(0L).partidasJugadas(0L).partidasGanadas(0L).isAdmin(false).googleId("").nombre("Juan").id("91L").mail("asd@gmail.com").build();
        Usuario alejo = Usuario.builder().rachaActual(0L).partidasJugadas(0L).partidasGanadas(0L).isAdmin(false).googleId("").nombre("Alejo").id("92L").mail("asd@gmail.com").build();
        Usuario pablo = Usuario.builder().rachaActual(0L).partidasJugadas(0L).partidasGanadas(0L).isAdmin(false).googleId("").nombre("Pablo").id("93L").mail("asd@gmail.com").build();
        usuarios.add(nico);
        usuarios.add(juan);
        usuarios.add(alejo);
        usuarios.add(pablo);

    }

    @Override
    public Usuario get(String id) {
        return usuarios.stream().filter(usuario -> usuario.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Usuario> getAll() {
        return usuarios;
    }

    @Override
    public void save(Usuario element) {
        if(!usuarios.contains(element))
            usuarios.add(element);
    }

    @Override
    public void delete(Usuario element) {
        usuarios = usuarios.stream().filter(usuario -> !usuario.getId().equals(element.getId())).collect(Collectors.toList());
    }

    @Override
    public Usuario getByGoogleId(String googleId) {
        return usuarios.stream().filter(usuario -> usuario.getGoogleId().equals(googleId)).findFirst().orElse(null);
    }

    @Override
    public Usuario getByUsername(String username) {
        return usuarios.stream().filter(usuario -> usuario.getNombre().equals(username)).findFirst().orElse(null);
    }

    @Override
    public List<Usuario> getByIds(List<String> idsUsuarios) {
        return usuarios.stream().filter(municipio -> idsUsuarios.contains(municipio.getId())).collect(Collectors.toList());
    }

    @Override
    public EstadisticasDeUsuarioModel estadisticas(String idUsuario) {
        Usuario usuario = this.get(idUsuario);
        return new EstadisticasDeUsuarioModel()
                .usuario(this.usuarioMapper.wrap(usuario))
                .partidasJugadas(usuario.getPartidasJugadas())
                .partidasGanadas(usuario.getPartidasGanadas())
                .rachaActual(usuario.getRachaActual());
    }

    @Override
    public List<EstadisticasDeUsuarioModel> scoreBoard() {
        List<EstadisticasDeUsuarioModel> estadisticasDeUsuarioModels = new ArrayList<>();
        usuarios.forEach(idUsuario -> estadisticasDeUsuarioModels.add(estadisticas(idUsuario.getId())));
        return estadisticasDeUsuarioModels;
    }

}
