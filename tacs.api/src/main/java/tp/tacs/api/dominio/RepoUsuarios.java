package tp.tacs.api.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.model.ScoreboardResponse;

import java.util.ArrayList;
import java.util.List;

public class RepoUsuarios {

    private List<Usuario> usuarios = new ArrayList<>();

    @Autowired
    private UsuarioMapper usuarioMapper;

    private static RepoUsuarios instancia = null;

    public static RepoUsuarios instance() {
        if (instancia == null) {
            instancia = new RepoUsuarios();
        }
        return instancia;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public EstadisticasDeUsuarioModel estadisticas(Usuario usuario) {
        return new EstadisticasDeUsuarioModel()
                .usuario(this.usuarioMapper.toModel(usuario))
                .partidasJugadas(usuario.getPartidasJugadas())
                .partidasGanadas(usuario.getPartidasGanadas())
                .rachaActual(usuario.getRachaActual());
    }

    public ScoreboardResponse scoreBoard() {
        ScoreboardResponse scoreboardResponse = new ScoreboardResponse();
        this.usuarios.forEach(usuario -> scoreboardResponse.addScoreboardItem(this.estadisticas(usuario)));
        return scoreboardResponse;
    }
}
