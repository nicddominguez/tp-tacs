package tp.tacs.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.LocalDate;
import tp.tacs.api.model.*;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class PartidasApiController implements PartidasApi {

    @Override
    public ResponseEntity<Void> actualizarEstadoPartida(Long idPartida, @Valid PartidaModel body) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> actualizarMunicipio(Long idPartida, Long idMunicipio, @Valid MunicipioEnJuegoModel body) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<AtacarMunicipioResponse> atacarMunicipio(Long idPartida, @Valid AtacarMunicipioBody body) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> crearPartida(@Valid CrearPartidaBody body) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PartidaModel> getPartida(Long idPartida) {
        var jugadores = Arrays.asList(new UsuarioModel(), new UsuarioModel());
        var partida = new PartidaModel();
        partida
                .id(1L)
                .modoDeJuego(ModoDeJuegoModel.RAPIDO)
                .provincia(new ProvinciaModel())
                .cantidadMunicipios(12L)
                .informacionDeJuego(new DatosDeJuegoModel())
                .estado(EstadoDeJuegoModel.ENPROGRESO)
                .fecha(new Date())
                .jugadores(jugadores);
        return ResponseEntity.ok(partida);
    }

    @Override
    public ResponseEntity<ListarPartidasResponse> listarPartidas(@Valid Date fechaInicio, @Valid Date fechaFin, @Valid EstadoDeJuegoModel estado, @Valid String ordenarPor, @Valid Long tamanioPagina, @Valid Long pagina) {
        var jugadores_una_partida = Arrays.asList(new UsuarioModel(), new UsuarioModel());

        var partida = new PartidaModel();
        var partida2 = new PartidaModel();

        partida
                .id(1L)
                .modoDeJuego(ModoDeJuegoModel.RAPIDO)
                .provincia(new ProvinciaModel())
                .cantidadMunicipios(12L)
                .informacionDeJuego(new DatosDeJuegoModel())
                .estado(EstadoDeJuegoModel.ENPROGRESO)
                .fecha(new Date())
                .jugadores(jugadores_una_partida);
        partida2
                .id(1L)
                .modoDeJuego(ModoDeJuegoModel.RAPIDO)
                .provincia(new ProvinciaModel())
                .cantidadMunicipios(12L)
                .informacionDeJuego(new DatosDeJuegoModel())
                .estado(EstadoDeJuegoModel.ENPROGRESO)
                .fecha(new Date())
                .jugadores(jugadores_una_partida);

        List<PartidaModel> partidaModels = Arrays.asList(partida, partida2);
        Utils utils = new Utils();

        List<PartidaModel> listaPaginada = utils.obtenerListaPaginada(pagina, tamanioPagina, partidaModels);

        if(listaPaginada == null)
            return ResponseEntity.notFound().build();

        ListarPartidasResponse response = new ListarPartidasResponse().partidas(listaPaginada);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MoverGauchosResponse> moverGauchos(Long idPartida, @Valid MoverGauchosBody body) {
        var moverGauchosResponse = new MoverGauchosResponse();
        moverGauchosResponse
                .municipioOrigen(new MunicipioEnJuegoModel())
                .municipioDestino(new MunicipioEnJuegoModel());
        return ResponseEntity.ok(new MoverGauchosResponse());
    }

    @Override
    public ResponseEntity<SimularAtacarMunicipioResponse> simularAtacarMunicipio(Long idPartida, @Valid SimularAtacarMunicipioBody body) {
        SimularAtacarMunicipioResponse response = new SimularAtacarMunicipioResponse().exitoso(Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
