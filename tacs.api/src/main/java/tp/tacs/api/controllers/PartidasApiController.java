package tp.tacs.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.mappers.EstadoDeJuegoMapper;
import tp.tacs.api.mappers.ModoDeMunicipioMapper;
import tp.tacs.api.mappers.MunicipioEnJuegoMapper;
import tp.tacs.api.mappers.PartidaMapper;
import tp.tacs.api.model.*;
import tp.tacs.api.requerimientos.ServicioPartida;
import tp.tacs.api.requerimientos.models.*;
import tp.tacs.api.utils.Utils;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Date;

@RestController
public class PartidasApiController implements PartidasApi {

    @Autowired
    private ServicioPartida servicioPartida;

    @Autowired
    private Utils utils;

    /**
     * Mappers
     */
    @Autowired
    private PartidaMapper partidaMapper;
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

    private Usuario usuarioA = Usuario.builder().nombre("Juan").id(1L).mail("juan@gmail.com").build();
    private Usuario usuarioD = Usuario.builder().nombre("Nico").id(2L).mail("as@gmailc.om").build();
    @PostConstruct
    public void postConstruct(){
        usuarioDao.save(usuarioA);
        usuarioDao.save(usuarioD);
    }

    @Override
    public ResponseEntity<Void> actualizarEstadoPartida(Long idPartida, @Valid PartidaModel body) {
        var request = ReqActualizarEstadoPartidaRequest
                .builder()
                .estado(estadoDeJuegoMapper.toEntity(body.getEstado()))
                .idPartida(idPartida)
                .build();
        servicioPartida.actualizarEstadoPartida(request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> actualizarMunicipio(Long idPartida, Long idMunicipio, @Valid ActualizarMunicipio body) {
        var municipioAActualizar = municipioDao.get(idMunicipio);
        var nuevaEspecializacion = modoDeMunicipioMapper.toEntity(body.getModo());
        municipioAActualizar.setEspecializacion(nuevaEspecializacion);
        //municipioAActualizar.estaBloqueado(body.isEstaBloqueado());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<AtacarMunicipioResponse> atacarMunicipio(Long idPartida, @Valid AtacarMunicipioBody body) {
        ReqAtacarModel reqAtacarModel = ReqAtacarModel.builder()
                .idPartida(idPartida)
                .idMunicipioAtacante(body.getIdMunicipioAtacante())
                .idMunicipioDefensor(body.getIdMunicipioObjetivo())
                .build();
        AtaqueMunicipiosResponse ataqueMunicipiosResponse = servicioPartida.atacar(reqAtacarModel);

        var response = new AtacarMunicipioResponse()
                .municipioAtacado(municipioEnJuegoMapper.wrap(ataqueMunicipiosResponse.getMunicipioDefensor()))
                .municipioAtacante(municipioEnJuegoMapper.wrap(ataqueMunicipiosResponse.getMunicipioAtacante()));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> crearPartida(@Valid CrearPartidaBody body) {
        servicioPartida.inicializar(body);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PartidaModel> getPartida(Long idPartida) {
        Partida partida = servicioPartida.obtenerPartidaPorId(idPartida);
        return ResponseEntity.ok(partidaMapper.wrap(partida));
    }

    @Override
    public ResponseEntity<ListarPartidasResponse> listarPartidas(@Valid Date fechaInicio,
            @Valid Date fechaFin,
            @Valid EstadoDeJuegoModel estado,
            @Valid String ordenarPor,
            @Valid Long tamanioPagina,
            @Valid Long pagina) {

        var partidas = this.partidaDao.getPartidasFiltradas(fechaInicio, fechaFin, estado);
        var partidaModels = partidaMapper.partidasParaListar(partidas);
        var listaPaginada = utils.obtenerListaPaginada(pagina, tamanioPagina, partidaModels);
        var response = new ListarPartidasResponse().partidas(listaPaginada);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MoverGauchosResponse> moverGauchos(Long idPartida, @Valid MoverGauchosBody body) {
        ReqMoverGauchosModel request = ReqMoverGauchosModel.builder()
                .cantidad(body.getCantidad())
                .idMunicipioDestino(body.getIdMunicipioDestino())
                .idMunicipioOrigen(body.getIdMunicipioOrigen()).idPartida(idPartida).build();
        return ResponseEntity.ok(servicioPartida.moverGauchos(request));
    }

    @Override
    public ResponseEntity<SimularAtacarMunicipioResponse> simularAtacarMunicipio(Long idPartida, @Valid SimularAtacarMunicipioBody body) {
        ReqSimularAtacarMunicipioRequest request = ReqSimularAtacarMunicipioRequest.builder().idMunicipioAtacante(body.getIdMunicipioAtacante())
                .idMunicipioObjectivo(
                        body.getIdMunicipioObjetivo()).idPartida(idPartida).build();
        return ResponseEntity.ok(servicioPartida.simularAtacarMunicipio(request));
    }

}
