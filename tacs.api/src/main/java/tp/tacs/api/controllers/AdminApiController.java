package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.dominio.partida.RepoPartidas;
import tp.tacs.api.dominio.usuario.RepoUsuarios;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.model.ScoreboardResponse;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class AdminApiController implements AdminApi {

    Utils utils = new Utils();

    RepoPartidas repoPartidas = RepoPartidas.instance();

    RepoUsuarios repoUsuarios = RepoUsuarios.instance();


    public void setRepoPartidas(RepoPartidas repoPartidas) {
        this.repoPartidas = repoPartidas;
    }

    public void setRepoUsuarios(RepoUsuarios repoUsuarios) {
        this.repoUsuarios = repoUsuarios;
    }

    @Override
    public ResponseEntity<EstadisticasDeJuegoModel> getEstadisticas(@Valid Date fechaInicio, @Valid Date fechaFin) {
        return ResponseEntity.ok(this.repoPartidas.estadisticas(fechaInicio, fechaFin));
    }

    @Override
    public ResponseEntity<EstadisticasDeUsuarioModel> getEstadisticasDeUsuario(Long idUsuario) {
        Usuario usuario = this.repoUsuarios.getUsuario(idUsuario);
        return ResponseEntity.ok(this.repoUsuarios.estadisticas(usuario));
    }

    @Override
    public ResponseEntity<ScoreboardResponse> getScoreboard(@Valid Long tamanioPagina, @Valid Long pagina) {

        List<EstadisticasDeUsuarioModel> listaPaginada = this.utils.obtenerListaPaginada(pagina, tamanioPagina, this.repoUsuarios.scoreBoard());

        if (listaPaginada == null)
            return ResponseEntity.notFound().build();

        ScoreboardResponse scoreboardResponse = new ScoreboardResponse().scoreboard(listaPaginada);
        return ResponseEntity.ok(scoreboardResponse);
    }
}
