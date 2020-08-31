package tp.tacs.api.controllers;

import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.LocalDate;
import tp.tacs.api.model.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@RestController
public class PartidasController implements PartidasApi {
    @Override
    public ResponseEntity<Void> actualizarEstadoPartida(Integer idPartida, @NotNull @Valid EstadoDeJuego estado) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AtacarMunicipioResponse> atacarMunicipio(Integer idPartida, @Valid AtacarMunicipioBody body) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> crearPartida(@Valid CrearPartidaBody body) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Partida> getPartida(Integer idPartida) {
        var jugadores = Arrays.asList(new Usuario(), new Usuario());
        var partida = new Partida();
        partida
                .id(1)
                .modoDeJuego(ModoDeJuego.RAPIDO)
                .provincia(new Provincia())
                .cantidadMunicipios(12)
                .informacionDeJuego(new DatosDeJuego())
                .estado(EstadoDeJuego.ENPROGRESO)
                .fecha(LocalDate.now())
                .jugadores(jugadores);
        return ResponseEntity.ok(partida);
    }

    @Override
    public ResponseEntity<List<Partida>> listarPartidas(@NotNull @Valid Integer page, @Valid LocalDate fechaInicio, @Valid LocalDate fechaFin, @Valid EstadoDeJuego estado, @Valid Integer pageSize) {
        var jugadores_una_partida = Arrays.asList(new Usuario(), new Usuario());

        var partida = new Partida();
        var partida2 = new Partida();

        partida
                .id(1)
                .modoDeJuego(ModoDeJuego.RAPIDO)
                .provincia(new Provincia())
                .cantidadMunicipios(12)
                .informacionDeJuego(new DatosDeJuego())
                .estado(EstadoDeJuego.ENPROGRESO)
                .fecha(LocalDate.now())
                .jugadores(jugadores_una_partida);
        partida2
                .id(1)
                .modoDeJuego(ModoDeJuego.RAPIDO)
                .provincia(new Provincia())
                .cantidadMunicipios(12)
                .informacionDeJuego(new DatosDeJuego())
                .estado(EstadoDeJuego.ENPROGRESO)
                .fecha(LocalDate.now())
                .jugadores(jugadores_una_partida);

        List<Partida> partidas = Arrays.asList(partida, partida2);
        return ResponseEntity.ok(partidas);
    }

    @Override
    public ResponseEntity<MoverGauchosResponse> moverGauchos(Integer idPartida, @Valid MoverGauchosBody body) {
        var moverGauchosResponse = new MoverGauchosResponse();
        moverGauchosResponse
                .municipioOrigen(new MunicipioEnJuego())
                .municipioDestino(new MunicipioEnJuego());
        return ResponseEntity.ok(new MoverGauchosResponse());
    }
}
