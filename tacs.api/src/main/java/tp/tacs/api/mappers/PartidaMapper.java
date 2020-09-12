package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.model.PartidaModel;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PartidaMapper extends AbstractMapper<Partida,PartidaModel> {

    private UsuarioMapper usuarioMapper = new UsuarioMapper();

    private EstadoDeJuegoMapper estadoDeJuegoMapper = new EstadoDeJuegoMapper();

    private ModoDeJuegoMapper modoDeJuegoMapper = new ModoDeJuegoMapper();

    private DatosDeJuegoMapper datosDeJuegoMapper = new DatosDeJuegoMapper();

    @Override protected PartidaModel wrapModel(Partida partida) {
        var jugadores = usuarioMapper.wrapList(partida.getJugadores());
        return new PartidaModel()
                .jugadores(jugadores)
                .estado(estadoDeJuegoMapper.toModel(Estado.EN_CURSO))
                .cantidadMunicipios((long) partida.getMunicipios().size())
                .fecha(partida.getFechaCreacion())
                .modoDeJuego(modoDeJuegoMapper.toModel(partida.getModoDeJuego()))
//                .provincia(provinciaMapper.wrapModel(model.getProvincia())) //todo revisar modelo
                .informacionDeJuego(datosDeJuegoMapper.wrap(partida))
                .id(partida.getId());
    }

    @Override protected Partida unwrapModel(PartidaModel model) {
        return null;
    }

    public PartidaModel partidaBasica(Partida partida){
        var jugadores = usuarioMapper.wrapList(partida.getJugadores());
        return new PartidaModel()
                .jugadores(jugadores)
                .estado(estadoDeJuegoMapper.toModel(Estado.EN_CURSO))
                .cantidadMunicipios((long) partida.getMunicipios().size())
                .fecha(partida.getFechaCreacion())
                .modoDeJuego(modoDeJuegoMapper.toModel(partida.getModoDeJuego()))
//                .provincia(provinciaMapper.wrapModel(model.getProvincia())) //todo revisar modelo
//                .informacionDeJuego(datosDeJuegoMapper.wrap(model))
                .id(partida.getId());
    }
    public List<PartidaModel> partidasParaListar(List<Partida> partidas){
        return partidas.stream().map(this::partidaBasica).collect(Collectors.toList());
    }
}
