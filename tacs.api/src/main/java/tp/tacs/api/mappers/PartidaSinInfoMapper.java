package tp.tacs.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.UsuarioDaoMemoria;
import tp.tacs.api.dominio.partida.PartidaSinInfo;
import tp.tacs.api.http.externalApis.models.Provincia;
import tp.tacs.api.model.PartidaSinInfoModel;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PartidaSinInfoMapper extends AbstractMapper<PartidaSinInfo, PartidaSinInfoModel> {
    @Autowired
    private ProvinciaMapper provinciaMapper;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private EstadoDeJuegoMapper estadoDeJuegoMapper;
    @Autowired
    private ModoDeJuegoMapper modoDeJuegoMapper;
    @Autowired
    private UsuarioDaoMemoria usuarioDaoMemoria;

    @Override
    protected PartidaSinInfo unwrapModel(PartidaSinInfoModel model) {
        return null;
    }

    @Override
    protected PartidaSinInfoModel wrapModel(PartidaSinInfo partida) {
        return partidaBasica(partida);
    }

    public PartidaSinInfoModel partidaBasica(PartidaSinInfo partida) {
        var jugadores = usuarioMapper.wrapList(usuarioDaoMemoria.getByIds(partida.getJugadoresIds()));
        var provincia = new Provincia();
        provincia.setId(partida.getIdProvincia());
        provincia.setNombre(partida.getNombreProvincia());
        return new PartidaSinInfoModel()
                .jugadores(jugadores)
                .estado(estadoDeJuegoMapper.toModel(partida.getEstado()))
                .cantidadMunicipios((long) partida.getMunicipios().size())
                .fecha(partida.getFechaCreacion())
                .modoDeJuego(modoDeJuegoMapper.toModel(partida.getModoDeJuego()))
                .provincia(provinciaMapper.wrapModel(provincia))
                .id(partida.getId());
    }

    public List<PartidaSinInfoModel> partidasParaListar(List<PartidaSinInfo> partidas) {
        return partidas.stream().map(this::partidaBasica).collect(Collectors.toList());
    }

}
