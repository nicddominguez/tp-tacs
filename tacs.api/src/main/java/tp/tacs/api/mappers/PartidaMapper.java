package tp.tacs.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.model.PartidaModel;

@Component
public class PartidaMapper extends AbstractMapper<Partida,PartidaModel> {
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private EstadoDeJuegoMapper estadoDeJuegoMapper;
    @Autowired
    private ModoDeJuegoMapper modoDeJuegoMapper;
    @Autowired
    private ProvinciaMapper provinciaMapper;
    @Autowired
    private DatosDeJuegoMapper datosDeJuegoMapper;

    @Override protected PartidaModel wrapModel(Partida model) {
        var jugadores = usuarioMapper.wrapList(model.getJugadores());
        return new PartidaModel()
                .jugadores(jugadores)
                .estado(estadoDeJuegoMapper.toModel(Estado.EN_CURSO))
                .cantidadMunicipios((long) model.getMunicipios().size())
                .fecha(model.getFechaCreacion())
                .modoDeJuego(modoDeJuegoMapper.toModel(model.getModoDeJuego()))
//                .provincia(provinciaMapper.wrapModel(model.getProvincia())) //todo revisar modelo
                .informacionDeJuego(datosDeJuegoMapper.wrap(model))
                .id(24L);
    }

    @Override protected Partida unwrapModel(PartidaModel model) {
        return null;
    }
}
