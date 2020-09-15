package tp.tacs.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.model.DatosDeJuegoModel;

@Component
public class DatosDeJuegoMapper extends AbstractMapper<Partida,DatosDeJuegoModel> {
    @Autowired
    private MunicipioEnJuegoMapper municipioEnJuegoMapper;
    @Autowired
    private MunicipioDao municipioDao;

    @Override protected DatosDeJuegoModel wrapModel(Partida model) {
        return new DatosDeJuegoModel()
                .municipios(municipioEnJuegoMapper.wrapList(municipioDao.getByIds(model.getMunicipios())))
                .idUsuarioProximoTurno(model.idUsuarioEnTurnoActual());
    }

    @Override protected Partida unwrapModel(DatosDeJuegoModel model) {
        return null;
    }
}
