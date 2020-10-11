package tp.tacs.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.daos.MunicipioDao;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.model.DatosDeJuegoModel;
import tp.tacs.api.servicios.ServicioPartida;

@Component
public class DatosDeJuegoMapper extends AbstractMapper<Partida,DatosDeJuegoModel> {
    @Autowired
    private MunicipioEnJuegoMapper municipioEnJuegoMapper;
    @Autowired
    private MunicipioDao municipioDao;
    @Autowired
    private ServicioPartida servicioPartida;

    @Override protected DatosDeJuegoModel wrapModel(Partida partida) {
        return new DatosDeJuegoModel()
                .municipios(municipioEnJuegoMapper.toModelList(municipioDao.getByIds(partida.getMunicipios()), partida.getModoDeJuego()))
                .idUsuarioProximoTurno(servicioPartida.idUsuarioEnTurnoActual(partida));
    }

    @Override protected Partida unwrapModel(DatosDeJuegoModel model) {
        return null;
    }
}
