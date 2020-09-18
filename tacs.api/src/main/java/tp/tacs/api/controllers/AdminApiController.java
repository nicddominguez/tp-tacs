package tp.tacs.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.model.ScoreboardResponse;
import tp.tacs.api.servicios.ServicioAdmin;

import javax.validation.Valid;
import java.util.Date;

@RestController
public class AdminApiController implements AdminApi {

    @Autowired
    private ServicioAdmin servicioAdmin;

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
}
