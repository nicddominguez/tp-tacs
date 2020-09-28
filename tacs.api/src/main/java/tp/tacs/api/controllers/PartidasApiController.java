package tp.tacs.api.controllers;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import tp.tacs.api.daos.PartidaDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.dominio.usuario.Usuario;
import tp.tacs.api.handler.MunicipioException;
import tp.tacs.api.handler.PartidaException;
import tp.tacs.api.mappers.EstadoDeJuegoMapper;
import tp.tacs.api.mappers.ModoDeMunicipioMapper;
import tp.tacs.api.mappers.PartidaMapper;
import tp.tacs.api.mappers.PartidaSinInfoMapper;
import tp.tacs.api.model.*;
import tp.tacs.api.servicios.ServicioMunicipio;
import tp.tacs.api.servicios.ServicioPartida;
import tp.tacs.api.utils.Utils;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
public class PartidasApiController implements PartidasApi {

    @Autowired
    Environment environment;

    Boolean debugMode;

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
    private EstadoDeJuegoMapper estadoDeJuegoMapper;
    @Autowired
    private ModoDeMunicipioMapper modoDeMunicipioMapper;
    /**
     * Daos
     */
    @Autowired
    private PartidaDao partidaDao;
    @Autowired
    private UsuarioDao usuarioDao;

    @PostConstruct
    private void postConstruct() {
        debugMode = Boolean.parseBoolean(environment.getProperty("application.debug-mode"));
    }

    @Override
    public ResponseEntity<Void> actualizarEstadoPartida(Long idPartida, @Valid ActualizarEstadoPartida body) {
        var estado = estadoDeJuegoMapper.toEntity(body.getEstado());
        var partida = partidaDao.get(idPartida);
        if(estado == null)
            return new ResponseEntity("No se especificó el nuevo estado de la partida", HttpStatus.BAD_REQUEST);
        if (partida == null)
            return new ResponseEntity("No existe la partida solicitada", HttpStatus.BAD_REQUEST);

        if (!usuarioTienePermisos(partida))
            return new ResponseEntity("El usuario no puede actualizar el estado de la partida, no es su turno", HttpStatus.BAD_REQUEST);

        servicioPartida.actualizarEstadoPartida(partida, estado);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> actualizarMunicipio(Long idPartida, Long idMunicipio, @Valid ActualizarMunicipio body) {
        var modo = body.getModo();
        if(modo == null)
            return new ResponseEntity("No se especificó el nuevo modo del municipio", HttpStatus.BAD_REQUEST);
        Partida partida = partidaDao.get(idPartida);
        if (partida == null)
            return new ResponseEntity("No existe la partida solicitada", HttpStatus.BAD_REQUEST);
        if (!usuarioTienePermisos(partida))
            return new ResponseEntity("El usuario no tiene permisos para actualizar el municipio en este turno", HttpStatus.BAD_REQUEST);

        var nuevaEspecializacion = modoDeMunicipioMapper.toEntity(modo);
        this.servicioMunicipio.actualizarMunicipio(idMunicipio, nuevaEspecializacion, body.isEstaBloqueado());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<AtacarMunicipioResponse> atacarMunicipio(Long idPartida, @Valid AtacarMunicipioBody body) {
        var idMunicipioAtacante = body.getIdMunicipioAtacante();
        var idMunicipioObjetivo = body.getIdMunicipioObjetivo();
        if(idMunicipioAtacante == null || idMunicipioObjetivo == null)
            return new ResponseEntity("Se requieren los municipios involucrados en el ataque", HttpStatus.BAD_REQUEST);
        Partida partida = partidaDao.get(idPartida);
        if (partida == null)
            return new ResponseEntity("No se pudo atacar: No existe la partida solicitada", HttpStatus.BAD_REQUEST);
        if(!partida.getMunicipios().containsAll(Arrays.asList(idMunicipioAtacante, idMunicipioObjetivo)))
            return new ResponseEntity("No se pudo atacar: los municipios no pertenecen a la partida indicada", HttpStatus.BAD_REQUEST);
        if (!usuarioTienePermisos(partida))
            return new ResponseEntity("El usuario no tiene permisos para atacar en este turno", HttpStatus.BAD_REQUEST);
        try {
            var response = servicioPartida.atacar(partida, idMunicipioAtacante, idMunicipioObjetivo);
            return ResponseEntity.ok(response);

        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.badRequest().build();
        }
        catch(PartidaException | MunicipioException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<PartidaModel> crearPartida(@Valid CrearPartidaBody body) {
        var idsJugadores = body.getIdJugadores();
        var cantidadMunicipios = body.getCantidadMunicipios();
        if(body.getIdProvincia() == null)
            return new ResponseEntity("No se puede crear una partida sin especificar una provincia", HttpStatus.BAD_REQUEST);
        if (idsJugadores == null || idsJugadores.isEmpty())
            return new ResponseEntity("No se puede crear una partida sin usuarios", HttpStatus.BAD_REQUEST);
        if (cantidadMunicipios == null || cantidadMunicipios <= 0)
            return new ResponseEntity("No se puede crear una partida con 0 municipios o menos.", HttpStatus.BAD_REQUEST);
        if (idsJugadores.size() > cantidadMunicipios)
            return new ResponseEntity("La cantidad de jugadores debe ser mayor o igual a la cantidad de municipios", HttpStatus.BAD_REQUEST);
        if (body.getModoDeJuego() == null)
            return new ResponseEntity("Modo de juego incorrecto", HttpStatus.BAD_REQUEST);
        //validar que los usuarios existan
        for (var id : idsJugadores) {
            if (usuarioDao.get(id) == null) {
                return new ResponseEntity("No existe el usuario solicitado", HttpStatus.BAD_REQUEST);
            }
        }
        Partida partida;
        try {
            partida = servicioPartida.inicializar(body);
            PartidaModel response = partidaMapper.wrap(partida);
            return ResponseEntity.ok(response);
        } catch (PartidaException | MunicipioException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<MoverGauchosResponse> moverGauchos(Long idPartida, @Valid MoverGauchosBody body) {

        if(body.getIdMunicipioDestino() == null || body.getIdMunicipioOrigen() == null)
            return new ResponseEntity("Se requieren los municipios involucrados en la acción", HttpStatus.BAD_REQUEST);
        if(body.getCantidad() == null)
            return new ResponseEntity("No se indicó la cantidad de gauchos a mover", HttpStatus.BAD_REQUEST);
        Partida partida = partidaDao.get(idPartida);
        if (partida == null)
            return new ResponseEntity("No existe la partida solicitada", HttpStatus.BAD_REQUEST);
        if (!usuarioTienePermisos(partida))
            return new ResponseEntity("El usuario no tiene permisos para mover gauchos en este turno", HttpStatus.FORBIDDEN);

        var cantidad = Math.toIntExact(body.getCantidad());
        MoverGauchosResponse response;
        try {
            response = servicioPartida.moverGauchos(body.getIdMunicipioOrigen(), body.getIdMunicipioDestino(), cantidad);
        } catch (MunicipioException | PartidaException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> pasarTurno(Long idPartida) {

        Partida partida = partidaDao.get(idPartida);
        if (partida == null)
            return new ResponseEntity("No existe la partida solicitada", HttpStatus.BAD_REQUEST);

        if (!usuarioTienePermisos(partida))
            return new ResponseEntity("El usuario no tiene permisos para pasar de turno", HttpStatus.BAD_REQUEST);

        servicioPartida.pasarTurno(partida);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<SimularAtacarMunicipioResponse> simularAtacarMunicipio(Long idPartida, @Valid SimularAtacarMunicipioBody body) {
        if(body.getIdMunicipioAtacante() == null || body.getIdMunicipioObjetivo() == null)
            return new ResponseEntity("Se requieren los municipios involucrados en la accion", HttpStatus.BAD_REQUEST);
        var partida = partidaDao.get(idPartida);
        if (partida == null)
            return new ResponseEntity("No existe la partida solicitada", HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(servicioPartida.simularAtacarMunicipio(partida, body.getIdMunicipioAtacante(), body.getIdMunicipioObjetivo()));
    }

    @Override
    public ResponseEntity<PartidaModel> getPartida(Long idPartida) {
        var partida = servicioPartida.obtenerPartidaPorId(idPartida);
        if (partida == null)
            return new ResponseEntity("No existe la partida solicitada", HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(partidaMapper.wrap(partida));
    }

    @Override
    public ResponseEntity<ListarPartidasResponse> listarPartidas(@Valid Date fechaInicio, @Valid Date fechaFin,
                                                                 @Valid EstadoDeJuegoModel estado, @Valid String ordenarPor, @Valid Long tamanioPagina, @Valid Long pagina) {
        var partidas = this.partidaDao.getPartidasFiltradas(fechaInicio, fechaFin, estado);
        var partidaModels = partidaSinInfoMapper.partidasParaListar(partidas);

        //TODO recibir si ordenar DESC o ASC
        try {
            if (ordenarPor != null) {
                var ordenes = Lists.reverse(Splitter.on(",").trimResults().splitToList(ordenarPor));
                var comparadores = ordenes.stream().map(this::transformarEnComparador).collect(Collectors.toList());
                comparadores.forEach(partidaModels::sort);
            }
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        var listaPaginada = utils.obtenerListaPaginada(pagina, tamanioPagina, partidaModels);
        var cantidadTotalDePartidas = Long.valueOf(partidas.size());

        var response = new ListarPartidasResponse().partidas(listaPaginada).cantidadTotalDePartidas(cantidadTotalDePartidas);

        return ResponseEntity.ok(response);
    }

    public Comparator<PartidaSinInfoModel> transformarEnComparador(String orden) {
        switch (orden.toLowerCase()) {
            case "fecha":
                return Comparator.comparing(PartidaSinInfoModel::getFecha);
            case "estado":
                return Comparator.comparing(PartidaSinInfoModel::getEstado);
            default:
                throw new RuntimeException("Orden no permitido");
        }
    }

    private String obtenerUsername() {
        var authorization = SecurityContextHolder.getContext().getAuthentication();
        return (String) authorization.getPrincipal();
    }

    private Boolean usuarioTienePermisos(Partida partida) {
        if (debugMode)
            return true;

        String usernameRequest = obtenerUsername();
        if (usernameRequest == null)
            return false;

        Usuario usuarioRequest = usuarioDao.getByUsername(usernameRequest);
        if (usuarioRequest == null)
            return false;
        return partida.idUsuarioEnTurnoActual().equals(usuarioRequest.getId());
    }
}

