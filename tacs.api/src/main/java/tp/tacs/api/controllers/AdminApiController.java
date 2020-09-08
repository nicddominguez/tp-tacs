package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.LocalDate;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.model.ScoreboardResponse;
import tp.tacs.api.model.UsuarioModel;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class AdminApiController implements AdminApi {

    @Override
    public ResponseEntity<EstadisticasDeJuegoModel> getEstadisticas(@Valid Date fechaInicio, @Valid Date fechaFin) {
        return ResponseEntity.ok(new EstadisticasDeJuegoModel().partidasCanceladas(30L).partidasEnCurso(5L).partidasCreadas(50L).partidasCanceladas(3L));
    }

    @Override
    public ResponseEntity<EstadisticasDeUsuarioModel> getEstadisticasDeUsuario(Long idUsuario) {
        return ResponseEntity.ok(new EstadisticasDeUsuarioModel().partidasGanadas(5L).partidasJugadas(10L).rachaActual(3L).usuario(new UsuarioModel().id(1L).nombreDeUsuario("Nico")));
    }

    @Override
    public ResponseEntity<ScoreboardResponse> getScoreboard(@Valid Long tamanioPagina, @Valid Long pagina) {
        List<EstadisticasDeUsuarioModel> estadisticasDeUsuarioModels = new ArrayList<>();

        estadisticasDeUsuarioModels.add(new EstadisticasDeUsuarioModel().partidasGanadas(5L).partidasJugadas(10L).rachaActual(3L).usuario(new UsuarioModel().id(1L).nombreDeUsuario("Nico")));
        estadisticasDeUsuarioModels.add(new EstadisticasDeUsuarioModel().partidasGanadas(6L).partidasJugadas(11L).rachaActual(6L).usuario(new UsuarioModel().id(2L).nombreDeUsuario("Franco")));
        estadisticasDeUsuarioModels.add(new EstadisticasDeUsuarioModel().partidasGanadas(2L).partidasJugadas(14L).rachaActual(3L).usuario(new UsuarioModel().id(3L).nombreDeUsuario("Alejo")));
        estadisticasDeUsuarioModels.add(new EstadisticasDeUsuarioModel().partidasGanadas(8L).partidasJugadas(16L).rachaActual(1L).usuario(new UsuarioModel().id(4L).nombreDeUsuario("Pablo")));
        estadisticasDeUsuarioModels.add(new EstadisticasDeUsuarioModel().partidasGanadas(4L).partidasJugadas(12L).rachaActual(6L).usuario(new UsuarioModel().id(5L).nombreDeUsuario("Nico2")));
        estadisticasDeUsuarioModels.add(new EstadisticasDeUsuarioModel().partidasGanadas(9L).partidasJugadas(15L).rachaActual(3L).usuario(new UsuarioModel().id(6L).nombreDeUsuario("Juan")));

        Utils utils = new Utils();

        List<EstadisticasDeUsuarioModel> listaPaginada = utils.obtenerListaPaginada(pagina, tamanioPagina, estadisticasDeUsuarioModels);

        if(listaPaginada == null)
            return ResponseEntity.notFound().build();

        ScoreboardResponse scoreboardResponse = new ScoreboardResponse().scoreboard(listaPaginada);
        return ResponseEntity.ok(scoreboardResponse);
    }
}
