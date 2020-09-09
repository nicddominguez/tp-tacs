package tp.tacs.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.model.PartidaModel;

import java.util.ArrayList;
import java.util.List;

@Component
public class PartidaMapper {

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

    public PartidaModel toModel(Partida entity) {
        var jugadores = this.usuarioMapper.mapearUsuarios(entity.getJugadores());
        return new PartidaModel()
                .jugadores(jugadores)
                .estado(estadoDeJuegoMapper.toModel(Estado.EN_CURSO))
                .cantidadMunicipios((long) entity.getMunicipios().size())
                .fecha(entity.getFechaCreacion())
                .modoDeJuego(modoDeJuegoMapper.toModel(entity.getModoDeJuego()))
                .provincia(provinciaMapper.toModel(entity.getProvincia()))
                .informacionDeJuego(datosDeJuegoMapper.toModel(entity))
                .id(24L);
    }

    public List<PartidaModel> toModels(List<Partida> partidas) {
        var partidasModels = new ArrayList<PartidaModel>();
        partidas.forEach(partida -> partidasModels.add(this.toModel(partida)));
        return partidasModels;
    }

}
