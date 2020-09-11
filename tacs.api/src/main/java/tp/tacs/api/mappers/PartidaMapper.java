package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Estado;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.model.PartidaModel;

import java.util.ArrayList;
import java.util.List;

@Component
public class PartidaMapper {

    private UsuarioMapper usuarioMapper = new UsuarioMapper();

    private EstadoDeJuegoMapper estadoDeJuegoMapper = new EstadoDeJuegoMapper();

    private ModoDeJuegoMapper modoDeJuegoMapper = new ModoDeJuegoMapper();

    private ProvinciaMapper provinciaMapper = new ProvinciaMapper();

    private DatosDeJuegoMapper datosDeJuegoMapper = new DatosDeJuegoMapper();

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
