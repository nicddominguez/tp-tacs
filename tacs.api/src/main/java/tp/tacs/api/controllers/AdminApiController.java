package tp.tacs.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.LocalDate;
import tp.tacs.api.model.EstadisticasDeJuego;
import tp.tacs.api.model.EstadisticasDeUsuario;
import tp.tacs.api.model.Usuario;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminApiController implements AdminApi {

    @Override
    public ResponseEntity<EstadisticasDeJuego> getEstadisticas(@Valid LocalDate fechaInicio, @Valid LocalDate fechaFin) {
        return ResponseEntity.ok(new EstadisticasDeJuego().partidasCanceladas(30).partidasEnCurso(5).partidasCreadas(50).partidasCanceladas(3));
    }

    @Override
    public ResponseEntity<EstadisticasDeUsuario> getEstadisticasDeUsuario(Integer idUsuario) {
        return ResponseEntity.ok(new EstadisticasDeUsuario().partidasGanadas(5).partidasJugadas(10).rachaActual(3).usuario(new Usuario().id(1).nombreDeUsuario("Nico")));
    }

    @Override
    public ResponseEntity<List<EstadisticasDeUsuario>> getScoreboard(@NotNull @Valid Integer page, @Valid Integer pageSize) {
        List<EstadisticasDeUsuario> estadisticasDeUsuarios = new ArrayList<>();

        estadisticasDeUsuarios.add(new EstadisticasDeUsuario().partidasGanadas(5).partidasJugadas(10).rachaActual(3).usuario(new Usuario().id(1).nombreDeUsuario("Nico")));
        estadisticasDeUsuarios.add(new EstadisticasDeUsuario().partidasGanadas(6).partidasJugadas(11).rachaActual(6).usuario(new Usuario().id(2).nombreDeUsuario("Franco")));
        estadisticasDeUsuarios.add(new EstadisticasDeUsuario().partidasGanadas(2).partidasJugadas(14).rachaActual(3).usuario(new Usuario().id(3).nombreDeUsuario("Alejo")));
        estadisticasDeUsuarios.add(new EstadisticasDeUsuario().partidasGanadas(8).partidasJugadas(16).rachaActual(1).usuario(new Usuario().id(4).nombreDeUsuario("Pablo")));
        estadisticasDeUsuarios.add(new EstadisticasDeUsuario().partidasGanadas(4).partidasJugadas(12).rachaActual(6).usuario(new Usuario().id(5).nombreDeUsuario("Nico2")));
        estadisticasDeUsuarios.add(new EstadisticasDeUsuario().partidasGanadas(9).partidasJugadas(15).rachaActual(3).usuario(new Usuario().id(6).nombreDeUsuario("Juan")));

        int start = page * pageSize;
        int end = start + pageSize;

        if (start > estadisticasDeUsuarios.size() || end > estadisticasDeUsuarios.size() || start < 0 || end <= 0)
            return ResponseEntity.notFound().build();

        List<EstadisticasDeUsuario> scoreboard = estadisticasDeUsuarios.subList(start, end);
        return ResponseEntity.ok(scoreboard);
    }
}
