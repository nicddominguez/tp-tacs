package tp.tacs.api.controllers;

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
public class PartidasApiController implements PartidasApi {
    @Override
    public ResponseEntity<Void> actualizarEstadoPartida(Integer idPartida, @NotNull @Valid EstadoDeJuegoModel estado) {
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
    public ResponseEntity<PartidaModel> getPartida(Integer idPartida) {
        var jugadores = Arrays.asList(new UsuarioModel(), new UsuarioModel());
        var partida = new PartidaModel();
        partida
                .id(1)
                .modoDeJuego(ModoDeJuegoModel.RAPIDO)
                .provincia(new ProvinciaModel())
                .cantidadMunicipios(12)
                .informacionDeJuego(new DatosDeJuegoModel())
                .estado(EstadoDeJuegoModel.ENPROGRESO)
                .fecha(LocalDate.now())
                .jugadores(jugadores);
        return ResponseEntity.ok(partida);
    }

    @Override
    public ResponseEntity<List<PartidaModel>> listarPartidas(@NotNull @Valid Integer page, @Valid LocalDate fechaInicio, @Valid LocalDate fechaFin, @Valid EstadoDeJuegoModel estado, @Valid Integer pageSize) {
        var jugadores_una_partida = Arrays.asList(new UsuarioModel(), new UsuarioModel());

        var partida = new PartidaModel();
        var partida2 = new PartidaModel();

        partida
                .id(1)
                .modoDeJuego(ModoDeJuegoModel.RAPIDO)
                .provincia(new ProvinciaModel())
                .cantidadMunicipios(12)
                .informacionDeJuego(new DatosDeJuegoModel())
                .estado(EstadoDeJuegoModel.ENPROGRESO)
                .fecha(LocalDate.now())
                .jugadores(jugadores_una_partida);
        partida2
                .id(1)
                .modoDeJuego(ModoDeJuegoModel.RAPIDO)
                .provincia(new ProvinciaModel())
                .cantidadMunicipios(12)
                .informacionDeJuego(new DatosDeJuegoModel())
                .estado(EstadoDeJuegoModel.ENPROGRESO)
                .fecha(LocalDate.now())
                .jugadores(jugadores_una_partida);

        List<PartidaModel> partidaModels = Arrays.asList(partida, partida2);
        return ResponseEntity.ok(partidaModels);
    }

    @Override
    public ResponseEntity<MoverGauchosResponse> moverGauchos(Integer idPartida, @Valid MoverGauchosBody body) {
        var moverGauchosResponse = new MoverGauchosResponse();
        moverGauchosResponse
                .municipioOrigen(new MunicipioEnJuegoModel())
                .municipioDestino(new MunicipioEnJuegoModel());
        return ResponseEntity.ok(new MoverGauchosResponse());
    }
}
