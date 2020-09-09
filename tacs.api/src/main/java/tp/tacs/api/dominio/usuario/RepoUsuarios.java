package tp.tacs.api.dominio.usuario;

import tp.tacs.api.mappers.UsuarioMapper;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepoUsuarios {

    private List<Usuario> usuarios = new ArrayList<>();

    private UsuarioMapper usuarioMapper = new UsuarioMapper();

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

    public Usuario getUsuario(Long usuarioId) {
        return this.usuarios.stream().filter(usuario -> usuario.mismoId(usuarioId)).collect(Collectors.toList()).get(0);
    }

    public EstadisticasDeUsuarioModel estadisticas(Usuario usuario) {
        return new EstadisticasDeUsuarioModel()
                .usuario(this.usuarioMapper.toModel(usuario))
                .partidasJugadas(usuario.getPartidasJugadas())
                .partidasGanadas(usuario.getPartidasGanadas())
                .rachaActual(usuario.getRachaActual());
    }

    public List<EstadisticasDeUsuarioModel> scoreBoard() {
        List<EstadisticasDeUsuarioModel> estadisticasDeUsuarioModels = new ArrayList<>();
        this.usuarios.forEach(usuario -> estadisticasDeUsuarioModels.add(this.estadisticas(usuario)));
        return estadisticasDeUsuarioModels;
    }
}
