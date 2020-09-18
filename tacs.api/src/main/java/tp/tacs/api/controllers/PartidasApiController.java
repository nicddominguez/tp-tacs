package tp.tacs.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.*;
import tp.tacs.api.model.*;
import tp.tacs.api.requerimientos.ServicioMunicipio;
import tp.tacs.api.requerimientos.ServicioPartida;
import tp.tacs.api.utils.Utils;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Date;

@RestController
public class PartidasApiController implements PartidasApi {

    @Autowired
    private ServicioPartida servicioPartida;
    @Autowired
    private ServicioMunicipio servicioMunicipio;

    @Autowired
    private Utils utils;

    /**
     * Mappers
     */
    @Autowired
    private PartidaMapper partidaMapper;
    @Autowired
    private PartidaSinInfoMapper partidaSinInfoMapper;
    @Autowired
    private MunicipioEnJuegoMapper municipioEnJuegoMapper;
    @Autowired
    private EstadoDeJuegoMapper estadoDeJuegoMapper;
    @Autowired
    private ModoDeMunicipioMapper modoDeMunicipioMapper;
    /**
     * Daos
     */
    @Autowired
    private PartidaDao partidaDao;
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public ResponseEntity<Void> actualizarEstadoPartida(Long idPartida, @Valid PartidaModel body) {
        var partida = partidaDao.get(idPartida);
        var estado = estadoDeJuegoMapper.toEntity(body.getEstado());
        servicioPartida.actualizarEstadoPartida(partida, estado);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> actualizarMunicipio(Long idPartida, Long idMunicipio, @Valid ActualizarMunicipio body) {
        var municipioAActualizar = municipioDao.get(idMunicipio);
        var nuevaEspecializacion = modoDeMunicipioMapper.toEntity(body.getModo());
        this.servicioMunicipio.actualizarMunicipio(municipioAActualizar, nuevaEspecializacion);
        municipioAActualizar.setBloqueado(body.isEstaBloqueado());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<AtacarMunicipioResponse> atacarMunicipio(Long idPartida, @Valid AtacarMunicipioBody body) {
        try {
            var partida = partidaDao.get(idPartida);

            var municipioAtacante = municipioDao.get(body.getIdMunicipioAtacante());
            var municipioAtacado = municipioDao.get(body.getIdMunicipioObjetivo());

            servicioPartida.atacar(partida, municipioAtacante, municipioAtacado);

            var response = new AtacarMunicipioResponse()
                    .municipioAtacado(municipioEnJuegoMapper.wrap(municipioAtacado))
                    .municipioAtacante(municipioEnJuegoMapper.wrap(municipioAtacante));
            return ResponseEntity.ok(response);
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override public ResponseEntity<PartidaModel> crearPartida(@Valid CrearPartidaBody body) {
        Partida partida = servicioPartida.inicializar(body);
        PartidaModel response = partidaMapper.wrap(partida);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MoverGauchosResponse> moverGauchos(Long idPartida, @Valid MoverGauchosBody body) {
        var municipioOrigen = municipioDao.get(body.getIdMunicipioOrigen());
        var municipioDestino = municipioDao.get(body.getIdMunicipioDestino());
        var cantidad = Math.toIntExact(body.getCantidad());
        return ResponseEntity.ok(servicioPartida.moverGauchos(municipioOrigen, municipioDestino, cantidad));
    }

    @Override
    public ResponseEntity<SimularAtacarMunicipioResponse> simularAtacarMunicipio(Long idPartida, @Valid SimularAtacarMunicipioBody body) {
        var partida = partidaDao.get(idPartida);
        var municipioAtacante = municipioDao.get(body.getIdMunicipioAtacante());
        var municipioAtacado = municipioDao.get(body.getIdMunicipioObjetivo());
        return ResponseEntity.ok(servicioPartida.simularAtacarMunicipio(partida, municipioAtacante, municipioAtacado));
    }

    @Override
    public ResponseEntity<PartidaModel> getPartida(Long idPartida) {
        Partida partida;
        try {
            partida = servicioPartida.obtenerPartidaPorId(idPartida);
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(partidaMapper.wrap(partida));
    }

    @Override public ResponseEntity<ListarPartidasResponse> listarPartidas(@Valid Date fechaInicio, @Valid Date fechaFin,
            @Valid EstadoDeJuegoModel estado, @Valid String ordenarPor, @Valid Long tamanioPagina, @Valid Long pagina) {
        var partidas = this.partidaDao.getPartidasFiltradas(fechaInicio, fechaFin, estado);
        var partidaModels = partidaSinInfoMapper.partidasParaListar(partidas);
        var listaPaginada = utils.obtenerListaPaginada(pagina, tamanioPagina, partidaModels);
        var response = new ListarPartidasResponse().partidas(listaPaginada);
        return ResponseEntity.ok(response);
    }

}
