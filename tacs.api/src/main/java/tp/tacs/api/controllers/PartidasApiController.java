package tp.tacs.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.partida.PartidaBuilder;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.*;
import tp.tacs.api.model.*;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;

@RestController
public class PartidasApiController implements PartidasApi {

    private PartidaMapper partidaMapper = new PartidaMapper();

    private MunicipioEnJuegoMapper municipioEnJuegoMapper = new MunicipioEnJuegoMapper();

    private EstadoDeJuegoMapper estadoDeJuegoMapper = new EstadoDeJuegoMapper();

    private ModoDeMunicipioMapper modoDeMunicipioMapper = new ModoDeMunicipioMapper();

    private ModoDeJuegoMapper modoDeJuegoMapper = new ModoDeJuegoMapper();

    private PartidaDao partidaDao = new PartidaDao();

    private MunicipioDao municipioDao = new MunicipioDao();

    UsuarioDao usuarioDao = new UsuarioDao();

    private Usuario usuarioA = new Usuario(1L, "as@gmailc.om", "nd");
    private Usuario usuarioD = new Usuario(2L, "as@gmailc.om", "ndd");

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public void setPartidaBuilder(PartidaBuilder partidaBuilder) {
        this.partidaBuilder = partidaBuilder;
    }

    private PartidaBuilder partidaBuilder = new PartidaBuilder();

    @Override
    public ResponseEntity<Void> actualizarEstadoPartida(Long idPartida, @Valid PartidaModel body) {
        Partida partida = this.partidaDao.get(idPartida);
        Estado nuevoEstado = estadoDeJuegoMapper.toEntity(body.getEstado());
        partida.setEstado(nuevoEstado);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> actualizarMunicipio(Long idPartida, Long idMunicipio, @Valid MunicipioEnJuegoModel body) {
        var municipioAActualizar = municipioDao.get(idMunicipio);
        var nuevaEspecializacion = modoDeMunicipioMapper.toEntity(body.getModo());
        municipioAActualizar.setEspecializacion(nuevaEspecializacion);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<AtacarMunicipioResponse> atacarMunicipio(Long idPartida, @Valid AtacarMunicipioBody body) {
        var municipioAtacante = municipioDao.get(body.getIdMunicipioAtacante());
        var municipioObjetivo = municipioDao.get(body.getIdMunicipioObjetivo());

        municipioAtacante.atacar(municipioObjetivo);

        var municipioAtacanteModel = municipioEnJuegoMapper.wrap(municipioAtacante);
        var municipioObjetivoModel = municipioEnJuegoMapper.wrap(municipioObjetivo);

        var response = new AtacarMunicipioResponse()
                .municipioAtacado(municipioObjetivoModel)
                .municipioAtacante(municipioAtacanteModel);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> crearPartida(@Valid CrearPartidaBody body) {
        partidaBuilder
                .setIdProvincia(body.getIdProvincia().toString())
                .setCantMunicipios(Math.toIntExact(body.getCantidadMunicipios()))
//                .setParticipantes(this.usuarioDao.getSegunIds(body.getIdJugadores())) //Implementación reL
                .setParticipantes(Arrays.asList(usuarioA, usuarioD)) //TODO cambiar por la implementación real cuando se puedan crear usaurios desde alguna ruta
                .setModoDeJuego(modoDeJuegoMapper.toEntity(body.getModoDeJuego()))
                .constriur();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PartidaModel> getPartida(Long idPartida) {
        var partida = this.partidaDao.get(idPartida);
        var partidaModel = this.partidaMapper.wrap(partida);
        return ResponseEntity.ok(partidaModel);
    }

    @Override
    public ResponseEntity<ListarPartidasResponse> listarPartidas(@Valid Date fechaInicio,
                                                                 @Valid Date fechaFin,
                                                                 @Valid EstadoDeJuegoModel estado,
                                                                 @Valid String ordenarPor,
                                                                 @Valid Long tamanioPagina,
                                                                 @Valid Long pagina) {
        Utils utils = new Utils();

        var partidas = this.partidaDao.getPartidasFiltradas(fechaInicio, fechaFin, estado);
        var partidaModels = partidaMapper.partidasParaListar(partidas);
        var listaPaginada = utils.obtenerListaPaginada(pagina, tamanioPagina, partidaModels);
        var response = new ListarPartidasResponse().partidas(listaPaginada);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MoverGauchosResponse> moverGauchos(Long idPartida, @Valid MoverGauchosBody body) {
        var idMunicipioOrigen = body.getIdMunicipioOrigen();
        var idMunicipioDestino = body.getIdMunicipioDestino();
        var municipioOrigen = municipioDao.get(idMunicipioOrigen);
        var municipioDestino = municipioDao.get(idMunicipioDestino);

        municipioOrigen.moverGauchos(municipioDestino, Math.toIntExact(body.getCantidad()));

        var municipioOrigenModel = municipioEnJuegoMapper.wrap(municipioOrigen);
        var municipioDestinoModel = municipioEnJuegoMapper.wrap(municipioDestino);

        var moverGauchosResponse = new MoverGauchosResponse()
                .municipioOrigen(municipioOrigenModel)
                .municipioDestino(municipioDestinoModel);
        return ResponseEntity.ok(moverGauchosResponse);
    }

    @Override
    public ResponseEntity<SimularAtacarMunicipioResponse> simularAtacarMunicipio(Long idPartida, @Valid SimularAtacarMunicipioBody body) {
        var municipioAtacante = municipioDao.get(body.getIdMunicipioAtacante());
        var municipioAtacado = municipioDao.get(body.getIdMunicipioObjetivo());

        var resultadoAtaque = municipioAtacante.ataqueExitoso(municipioAtacado);

        var response = new SimularAtacarMunicipioResponse().exitoso(resultadoAtaque);
        return ResponseEntity.ok(response);
    }

}
