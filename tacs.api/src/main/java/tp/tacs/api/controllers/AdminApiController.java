package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.model.ScoreboardResponse;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class AdminApiController implements AdminApi {

    private Utils utils = new Utils();

    private PartidaDao partidaDao = new PartidaDao();

    private UsuarioDao usuarioDao = new UsuarioDao();

    public void setPartidaDao(PartidaDao partidaDao) {
        this.partidaDao = partidaDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    public ResponseEntity<EstadisticasDeJuegoModel> getEstadisticas(@Valid Date fechaInicio, @Valid Date fechaFin) {
        return ResponseEntity.ok(this.partidaDao.estadisticas(fechaInicio, fechaFin));
    }

    @Override
    public ResponseEntity<EstadisticasDeUsuarioModel> getEstadisticasDeUsuario(Long idUsuario) {
        return ResponseEntity.ok(this.usuarioDao.estadisticas(idUsuario));
    }

    @Override
    public ResponseEntity<ScoreboardResponse> getScoreboard(@Valid Long tamanioPagina, @Valid Long pagina) {

        List<EstadisticasDeUsuarioModel> listaPaginada = this.utils.obtenerListaPaginada(pagina,
                tamanioPagina, this.usuarioDao.scoreBoard());

        ScoreboardResponse scoreboardResponse = new ScoreboardResponse().scoreboard(listaPaginada);
        return ResponseEntity.ok(scoreboardResponse);
    }
}
