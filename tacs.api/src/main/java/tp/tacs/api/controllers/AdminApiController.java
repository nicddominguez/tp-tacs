package tp.tacs.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.model.ScoreboardResponse;
import tp.tacs.api.servicios.ServicioAdmin;
import tp.tacs.api.servicios.ServicioPartida;
import tp.tacs.api.servicios.ServicioUsuario;

import javax.validation.Valid;
import java.util.Date;

@RestController
public class AdminApiController implements AdminApi {

    @Autowired
    private ServicioAdmin servicioAdmin;
    @Autowired
    private ServicioPartida servicioPartida;
    @Autowired
    private PartidaDao partidaDao;
    @Autowired
    private ServicioUsuario servicioUsuario;

    @Override
    public ResponseEntity<EstadisticasDeJuegoModel> getEstadisticas(@Valid Date fechaInicio, @Valid Date fechaFin) {
        //todo validaciones de formato de fechas
        return ResponseEntity.ok(servicioAdmin.estadisticasDeJuego(fechaInicio, fechaFin));
    }

    @Override
    public ResponseEntity<EstadisticasDeUsuarioModel> getEstadisticasDeUsuario(Long idUsuario) {
        // todo validaciones de inputs
        return ResponseEntity.ok(servicioAdmin.estadisticasDeUsuario(idUsuario));
    }

    @Override
    public ResponseEntity<ScoreboardResponse> getScoreboard(@Valid Long tamanioPagina, @Valid Long pagina) {
        // todo validaciones de inputs
        var listaPaginada = servicioAdmin.tablaDePuntos(tamanioPagina, pagina);
        ScoreboardResponse scoreboardResponse = new ScoreboardResponse().scoreboard(listaPaginada);
        return ResponseEntity.ok(scoreboardResponse);
    }

    @Override public ResponseEntity<Void> pasarTurnoAdmin(Long idPartida) {
        Partida partida = partidaDao.get(idPartida);
        if (partida == null)
            return ResponseEntity.notFound().build();
        servicioPartida.pasarTurno(partida);
        return ResponseEntity.ok().build();
    }

    @Override public ResponseEntity<String> obtenerJwtUsuario(Long idUsuario) {
        String jwtUsuario = servicioUsuario.generarJwtParaUsuarioPorId(idUsuario);
        return ResponseEntity.ok(jwtUsuario);
    }
}
