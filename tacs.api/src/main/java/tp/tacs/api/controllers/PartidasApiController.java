package tp.tacs.api.controllers;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.handler.PartidaException;
import tp.tacs.api.mappers.*;
import tp.tacs.api.model.*;
import tp.tacs.api.servicios.ServicioMunicipio;
import tp.tacs.api.servicios.ServicioPartida;
import tp.tacs.api.utils.Utils;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

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
    public ResponseEntity<Void> actualizarEstadoPartida(Long idPartida, @Valid ActualizarEstadoPartida body) {
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

    @Override
    public ResponseEntity<PartidaModel> crearPartida(@Valid CrearPartidaBody body) {
        if (body.getIdJugadores().isEmpty())
            return new ResponseEntity("No se puede crear una partida sin usuarios", HttpStatus.BAD_REQUEST);
        if (body.getCantidadMunicipios() <= 0)
            return new ResponseEntity("No se puede crear una partida con 0 municipios o menos.", HttpStatus.BAD_REQUEST);
        if (body.getModoDeJuego() == null)
            return new ResponseEntity("Modo de juego incorrecto", HttpStatus.BAD_REQUEST);
        //validar que los usuarios existan
        for (var id : body.getIdJugadores()) {
            if (usuarioDao.get(id) == null) {
                return ResponseEntity.badRequest().build();
            }
        }
        Partida partida;
        try {
            partida = servicioPartida.inicializar(body);
            PartidaModel response = partidaMapper.wrap(partida);
            return ResponseEntity.ok(response);
        } catch (PartidaException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<MoverGauchosResponse> moverGauchos(Long idPartida, @Valid MoverGauchosBody body) {
        var municipioOrigen = municipioDao.get(body.getIdMunicipioOrigen());
        var municipioDestino = municipioDao.get(body.getIdMunicipioDestino());
        var cantidad = Math.toIntExact(body.getCantidad());
        MoverGauchosResponse response;
        try {
            response = servicioPartida.moverGauchos(municipioOrigen, municipioDestino, cantidad);
        } catch (PartidaException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> pasarTurno(Long idPartida) {
        String usernameRequest = obtenerUsername();
        if(usernameRequest == null)
            return ResponseEntity.status(401).build();

        Partida partida = partidaDao.get(idPartida);
        if(partida == null)
            return ResponseEntity.status(404).build();

        Usuario usuarioRequest = usuarioDao.getByUsername(usernameRequest);
        if(partida.idUsuarioEnTurnoActual() != usuarioRequest.getId())
            return ResponseEntity.status(403).build();

        servicioPartida.pasarTurno(partida);
        return ResponseEntity.ok().build();
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
        var partida = servicioPartida.obtenerPartidaPorId(idPartida);
        if (partida == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(partidaMapper.wrap(partida));
    }

    @Override
    public ResponseEntity<ListarPartidasResponse> listarPartidas(@Valid Date fechaInicio, @Valid Date fechaFin,
            @Valid EstadoDeJuegoModel estado, @Valid String ordenarPor, @Valid Long tamanioPagina, @Valid Long pagina) {
        var partidas = this.partidaDao.getPartidasFiltradas(fechaInicio, fechaFin, estado);
        var partidaModels = partidaSinInfoMapper.partidasParaListar(partidas);

        //TODO recibir si ordenar DESC o ASC
        if (ordenarPor != null) {
            var ordenes = Lists.reverse(Splitter.on(",").trimResults().splitToList(ordenarPor));
            var comparadores = ordenes.stream().map(this::transformarEnComparador).collect(Collectors.toList());
            comparadores.forEach(partidaModels::sort);
        }

        var listaPaginada = utils.obtenerListaPaginada(pagina, tamanioPagina, partidaModels);
        var cantidadTotalDePartidas = Long.valueOf(partidas.size());

        var response = new ListarPartidasResponse().partidas(listaPaginada);

        return ResponseEntity.ok(response);
    }

    public Comparator<PartidaSinInfoModel> transformarEnComparador(String orden) {
        switch (orden) {
        case "fecha":
            return Comparator.comparing(PartidaSinInfoModel::getFecha);
        case "estado":
            return Comparator.comparing(PartidaSinInfoModel::getEstado);
        default:
            throw new RuntimeException("Orden no permitido");
        }
    }

    private String obtenerUsername(){
        var authorization = SecurityContextHolder.getContext().getAuthentication();
        return (String) authorization.getPrincipal();
    }
}

