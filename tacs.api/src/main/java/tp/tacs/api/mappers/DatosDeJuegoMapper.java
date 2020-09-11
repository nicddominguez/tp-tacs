package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.model.DatosDeJuegoModel;

@Component
public class DatosDeJuegoMapper {

    MunicipioEnJuegoMapper municipioEnJuegoMapper = new MunicipioEnJuegoMapper();

    public DatosDeJuegoModel toModel(Partida entity) {
        return new DatosDeJuegoModel()
                .municipios(municipioEnJuegoMapper.municipiosToModel(entity.getMunicipios()))
                .idUsuarioProximoTurno(entity.usuarioEnTurnoActual().getId());
    }
}
