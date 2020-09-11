package tp.tacs.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.http.wrappers.AbstractWrapper;
import tp.tacs.api.model.DatosDeJuegoModel;

@Component
public class DatosDeJuegoMapper extends AbstractWrapper<Partida,DatosDeJuegoModel> {

    @Autowired
    private MunicipioEnJuegoMapper municipioEnJuegoMapper;

    @Override protected DatosDeJuegoModel wrapModel(Partida model) {
        return new DatosDeJuegoModel()
                .municipios(municipioEnJuegoMapper.wrapList(model.getMunicipios()))
                .idUsuarioProximoTurno(model.usuarioEnTurnoActual().getId());
    }

    @Override protected Partida unwrapModel(DatosDeJuegoModel model) {
        return null;
    }
}
