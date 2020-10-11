package tp.tacs.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.dominio.partida.ModoDeJuego;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.http.externalApis.models.Provincia;
import tp.tacs.api.model.MunicipiosLivianosModel;
import tp.tacs.api.model.MunicipiosPorJugadorLivianoModel;
import tp.tacs.api.model.PartidaLivianaModel;
import tp.tacs.api.model.PartidaModel;
import tp.tacs.api.servicios.ServicioMunicipio;
import tp.tacs.api.servicios.ServicioPartida;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PartidaMapper extends AbstractMapper<Partida, PartidaModel> {
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private EstadoDeJuegoMapper estadoDeJuegoMapper;
    @Autowired
    private ModoDeJuegoMapper modoDeJuegoMapper;
    @Autowired
    private DatosDeJuegoMapper datosDeJuegoMapper;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private ProvinciaMapper provinciaMapper;
    @Autowired
    private ModoDeMunicipioMapper modoDeMunicipioMapper;
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private ServicioPartida servicioPartida;
    @Autowired
    private ServicioMunicipio servicioMunicipio;

    @Override
    protected PartidaModel wrapModel(Partida partida) {
        var ganador = partida.getGanador();
        var partidaBasica = this.partidaBasica(partida)
                .informacionDeJuego(datosDeJuegoMapper.wrap(partida));
        if (ganador != null)
            partidaBasica.idGanador(ganador.getId().toString());
        return partidaBasica;
    }

    @Override
    protected Partida unwrapModel(PartidaModel model) {
        return null;
    }

    public PartidaModel partidaBasica(Partida partida) {
        var jugadores = usuarioMapper.wrapList(usuarioDao.getByIds(partida.getIdsJugadoresOriginales()));
        var provincia = new Provincia();
        provincia.setId(partida.getIdProvincia());
        provincia.setNombre(partida.getNombreProvincia());
        return new PartidaModel()
                .jugadores(jugadores)
                .estado(estadoDeJuegoMapper.toModel(partida.getEstado()))
                .cantidadMunicipios((long) partida.getMunicipios().size())
                .fecha(partida.getFechaCreacion())
                .modoDeJuego(modoDeJuegoMapper.toModel(partida.getModoDeJuego()))
                .provincia(provinciaMapper.wrapModel(provincia))
                .id(partida.getId());
    }

    public List<PartidaModel> partidasParaListar(List<Partida> partidas) {
        return partidas.stream().map(this::partidaBasica).collect(Collectors.toList());
    }

    public List<MunicipiosPorJugadorLivianoModel> municipiosPorJugador(Partida partida) {
        var municipiosConDuenio = municipioDao.getByIds(partida.getMunicipios()).stream()
                .filter(municipio -> municipio.getDuenio() != null).collect(Collectors.toSet());
        var municipiosPorJugador = municipiosConDuenio.stream().collect(Collectors
                .groupingBy(municipio -> municipio.getDuenio().getId()));
        List<MunicipiosPorJugadorLivianoModel> municipiosPorJugadorLiviano = new ArrayList<>();
        municipiosPorJugador.forEach((idJugador, municipios) -> municipiosPorJugadorLiviano
                .add(this.municipiosPorJugadorLivianoModel(idJugador, municipios, partida.getModoDeJuego())));
        return municipiosPorJugadorLiviano;
    }

    public MunicipiosPorJugadorLivianoModel municipiosPorJugadorLivianoModel(String idJugador, List<Municipio> municipios, ModoDeJuego modoDeJuego) {
        var municipiosLivianos = municipios.stream()
                .map(muni -> municipioLivianosModel(muni, modoDeJuego))
                .collect(Collectors.toList());
        return new MunicipiosPorJugadorLivianoModel().idJugador(idJugador).municipios(municipiosLivianos);
    }


    public MunicipiosLivianosModel municipioLivianosModel(Municipio municipio, ModoDeJuego modoDeJuego) {
        return new MunicipiosLivianosModel()
                .id(municipio.getId())
                .estaBloqueado(municipio.isBloqueado())
                .gauchos(municipio.getCantGauchos().longValue())
                .modo(modoDeMunicipioMapper.toModel(municipio.getEspecializacion()))
                .produccionDeGauchos(municipio.getNivelDeProduccion().longValue())
                .puntosDeDefensa(municipio.getEspecializacion().multDefensa(modoDeJuego));
    }

    public PartidaLivianaModel aPartidaLivianaModel(Partida partida) {
        var ganador = partida.getGanador();
        var municipiosPorJugador = this.municipiosPorJugador(partida);
        var partidaLiviana = new PartidaLivianaModel()
                .id(partida.getId())
                .estado(estadoDeJuegoMapper.toModel(partida.getEstado()))
                .idUsuarioProximoTurno(servicioPartida.idUsuarioEnTurnoActual(partida))
                .municipiosPorJugador(municipiosPorJugador);
        if (ganador != null)
            partidaLiviana.idGanador(ganador.getId().toString());
        return partidaLiviana;
    }
}
