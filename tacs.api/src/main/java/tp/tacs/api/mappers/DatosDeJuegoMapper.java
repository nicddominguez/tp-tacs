package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.model.DatosDeJuegoModel;

@Component
public class DatosDeJuegoMapper extends AbstractMapper<Partida,DatosDeJuegoModel> {

    private MunicipioEnJuegoMapper municipioEnJuegoMapper = new MunicipioEnJuegoMapper();

    @Override protected DatosDeJuegoModel wrapModel(Partida model) {
        return new DatosDeJuegoModel()
                .municipios(municipioEnJuegoMapper.wrapList(model.getMunicipios()))
                .idUsuarioProximoTurno(model.usuarioEnTurnoActual().getId());
    }

    @Override protected Partida unwrapModel(DatosDeJuegoModel model) {
        return null;
    }
}
