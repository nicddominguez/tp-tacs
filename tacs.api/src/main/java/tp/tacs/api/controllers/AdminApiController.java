package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.LocalDate;
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.model.UsuarioModel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminApiController implements AdminApi {

    @Override
    public ResponseEntity<EstadisticasDeJuegoModel> getEstadisticas(@Valid LocalDate fechaInicio, @Valid LocalDate fechaFin) {
        return ResponseEntity.ok(new EstadisticasDeJuegoModel().partidasCanceladas(30).partidasEnCurso(5).partidasCreadas(50).partidasCanceladas(3));
    }

    @Override
    public ResponseEntity<EstadisticasDeUsuarioModel> getEstadisticasDeUsuario(Integer idUsuario) {
        return ResponseEntity.ok(new EstadisticasDeUsuarioModel().partidasGanadas(5).partidasJugadas(10).rachaActual(3).usuario(new UsuarioModel().id(1).nombreDeUsuario("Nico")));
    }

    @Override
    public ResponseEntity<List<EstadisticasDeUsuarioModel>> getScoreboard(@NotNull @Valid Integer page, @Valid Integer pageSize) {
        List<EstadisticasDeUsuarioModel> estadisticasDeUsuarioModels = new ArrayList<>();

        estadisticasDeUsuarioModels.add(new EstadisticasDeUsuarioModel().partidasGanadas(5).partidasJugadas(10).rachaActual(3).usuario(new UsuarioModel().id(1).nombreDeUsuario("Nico")));
        estadisticasDeUsuarioModels.add(new EstadisticasDeUsuarioModel().partidasGanadas(6).partidasJugadas(11).rachaActual(6).usuario(new UsuarioModel().id(2).nombreDeUsuario("Franco")));
        estadisticasDeUsuarioModels.add(new EstadisticasDeUsuarioModel().partidasGanadas(2).partidasJugadas(14).rachaActual(3).usuario(new UsuarioModel().id(3).nombreDeUsuario("Alejo")));
        estadisticasDeUsuarioModels.add(new EstadisticasDeUsuarioModel().partidasGanadas(8).partidasJugadas(16).rachaActual(1).usuario(new UsuarioModel().id(4).nombreDeUsuario("Pablo")));
        estadisticasDeUsuarioModels.add(new EstadisticasDeUsuarioModel().partidasGanadas(4).partidasJugadas(12).rachaActual(6).usuario(new UsuarioModel().id(5).nombreDeUsuario("Nico2")));
        estadisticasDeUsuarioModels.add(new EstadisticasDeUsuarioModel().partidasGanadas(9).partidasJugadas(15).rachaActual(3).usuario(new UsuarioModel().id(6).nombreDeUsuario("Juan")));

        int start = page * pageSize;
        int end = start + pageSize;

        if (start > estadisticasDeUsuarioModels.size() || end > estadisticasDeUsuarioModels.size() || start < 0 || end <= 0)
            return ResponseEntity.notFound().build();

        List<EstadisticasDeUsuarioModel> scoreboard = estadisticasDeUsuarioModels.subList(start, end);
        return ResponseEntity.ok(scoreboard);
    }
}
