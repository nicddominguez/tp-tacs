package tp.tacs.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.GregorianCalendar;
import java.util.List;

@RestController
public class AdminApiController implements AdminApi {

    @Autowired
    private Utils utils;
    @Autowired
    private PartidaDao partidaDao;
    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public ResponseEntity<EstadisticasDeJuegoModel> getEstadisticas(@Valid Date fechaInicio, @Valid Date fechaFin) {

        if (fechaInicio == null || fechaFin == null) {
            fechaInicio = new GregorianCalendar(2014, 2, 11).getTime();
            fechaFin = new GregorianCalendar(2077, 2, 11).getTime();
        }
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
