package tp.tacs.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.UsuarioDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.http.externalApis.models.Provincia;
import tp.tacs.api.model.PartidaModel;

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

    @Override
    protected PartidaModel wrapModel(Partida partida) {
        var ganador = partida.getGanador();
        var partidaBasica = this.partidaBasica(partida)
                .informacionDeJuego(datosDeJuegoMapper.wrap(partida));
        if(ganador != null)
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
}
