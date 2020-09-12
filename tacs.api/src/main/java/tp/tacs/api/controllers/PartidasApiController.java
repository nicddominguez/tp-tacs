package tp.tacs.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.partida.PartidaBuilder;
import tp.tacs.api.dominio.partida.RepoPartidas;
import tp.tacs.api.http.externalApis.ExternalApis;
import tp.tacs.api.mappers.*;
import tp.tacs.api.model.*;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import java.util.Date;

@RestController
public class PartidasApiController implements PartidasApi {

    private PartidaMapper partidaMapper = new PartidaMapper();

    private MunicipioEnJuegoMapper municipioEnJuegoMapper = new MunicipioEnJuegoMapper();

    private EstadoDeJuegoMapper estadoDeJuegoMapper = new EstadoDeJuegoMapper();

    private ModoDeMunicipioMapper modoDeMunicipioMapper = new ModoDeMunicipioMapper();

    private ModoDeJuegoMapper modoDeJuegoMapper = new ModoDeJuegoMapper();

    private RepoPartidas repoPartidas = RepoPartidas.instance();

    UsuarioDao usuarioDao = new UsuarioDao();

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    public ResponseEntity<Void> actualizarEstadoPartida(Long idPartida, @Valid PartidaModel body) {
        try {
            Partida partida = repoPartidas.getPartida(idPartida);
            var nuevoEstado = estadoDeJuegoMapper.toEntity(body.getEstado());
            System.out.println(nuevoEstado);
            partida.setEstado(nuevoEstado);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Void> actualizarMunicipio(Long idPartida, Long idMunicipio, @Valid MunicipioEnJuegoModel body) {
        try {
            var municipioAActualizar = ExternalApis.instance().getMunicipios(idPartida.toString(), idMunicipio.intValue()).get(0);
            var nuevaEspecializacion = modoDeMunicipioMapper.toEntity(body.getModo());
            municipioAActualizar.setEspecializacion(nuevaEspecializacion);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<AtacarMunicipioResponse> atacarMunicipio(Long idPartida, @Valid AtacarMunicipioBody body) {
        try {
            var municipioAtacante = ExternalApis.instance().getMunicipios(idPartida.toString(), body.getIdMunicipioAtacante().intValue()).get(0);
            var municipioObjetivo = ExternalApis.instance().getMunicipios(idPartida.toString(), body.getIdMunicipioObjetivo().intValue()).get(0);
            var municipioAtacanteModel = municipioEnJuegoMapper.wrap(municipioAtacante);
            var municipioObjetivoModel = municipioEnJuegoMapper.wrap(municipioObjetivo);

            var response = new AtacarMunicipioResponse()
                    .municipioAtacado(municipioObjetivoModel)
                    .municipioAtacante(municipioAtacanteModel);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Void> crearPartida(@Valid CrearPartidaBody body) {
        try {
            new PartidaBuilder(body.getIdProvincia().toString())
                    .setCantMunicipios(Math.toIntExact(body.getCantidadMunicipios()))
                    .setParticipantes(this.usuarioDao.getSegunIds(body.getIdJugadores()))
                    .setModoDeJuego(modoDeJuegoMapper.toEntity(body.getModoDeJuego()))
                    .constriur();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<PartidaModel> getPartida(Long idPartida) {
        try {
            var partida = repoPartidas.getPartida(idPartida);
            var partidaModel = this.partidaMapper.wrap(partida);

            return ResponseEntity.ok(partidaModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @Override
    public ResponseEntity<ListarPartidasResponse> listarPartidas(@Valid Date fechaInicio,
                                                                 @Valid Date fechaFin,
                                                                 @Valid EstadoDeJuegoModel estado,
                                                                 @Valid String ordenarPor,
                                                                 @Valid Long tamanioPagina,
                                                                 @Valid Long pagina) {
        try {
            Utils utils = new Utils();

            var partidas = repoPartidas.getPartidasFiltradas(fechaInicio, fechaFin, estado);
            var partidaModels = partidaMapper.wrapList(partidas);
            var listaPaginada = utils.obtenerListaPaginada(pagina, tamanioPagina, partidaModels);
            var response = new ListarPartidasResponse().partidas(listaPaginada);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @Override
    public ResponseEntity<MoverGauchosResponse> moverGauchos(Long idPartida, @Valid MoverGauchosBody body) {
        try {
            var idMunicipioOrigen = body.getIdMunicipioOrigen();
            var idMunicipioDestino = body.getIdMunicipioDestino();
            var municipioOrigen = ExternalApis.instance().getMunicipios(idPartida.toString(), idMunicipioOrigen.intValue()).get(0);
            var municipioDestino = ExternalApis.instance().getMunicipios(idPartida.toString(), idMunicipioDestino.intValue()).get(0);

            municipioOrigen.moverGauchos(municipioDestino, Math.toIntExact(body.getCantidad()));

            var municipioOrigenModel = municipioEnJuegoMapper.wrap(municipioOrigen);
            var municipioDestinoModel = municipioEnJuegoMapper.wrap(municipioDestino);

            var moverGauchosResponse = new MoverGauchosResponse()
                    .municipioOrigen(municipioOrigenModel)
                    .municipioDestino(municipioDestinoModel);
            return ResponseEntity.ok(moverGauchosResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @Override
    public ResponseEntity<SimularAtacarMunicipioResponse> simularAtacarMunicipio(Long idPartida, @Valid SimularAtacarMunicipioBody body) {
        try {
            var municipioAtacante = ExternalApis.instance().getMunicipios(idPartida.toString(), body.getIdMunicipioAtacante().intValue()).get(0);
            var municipioAtacado = ExternalApis.instance().getMunicipios(idPartida.toString(), body.getIdMunicipioObjetivo().intValue()).get(0);

            var resultadoAtaque = municipioAtacante.ataqueExitoso(municipioAtacado);

            var response = new SimularAtacarMunicipioResponse().exitoso(resultadoAtaque);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
