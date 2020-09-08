package tp.tacs.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.Partida;
import tp.tacs.api.model.DatosDeJuegoModel;
import tp.tacs.api.model.MunicipioEnJuegoModel;

@Component
public class DatosDeJuegoMapper {

    @Autowired
    MunicipioEnJuegoMapper municipioEnJuegoMapper;

    //TODO
    public DatosDeJuegoModel toModel(Partida entity) {
        return new DatosDeJuegoModel()
                .municipios(municipioEnJuegoMapper.mapearMunicipios(entity.getMunicipios()));
    }
}
