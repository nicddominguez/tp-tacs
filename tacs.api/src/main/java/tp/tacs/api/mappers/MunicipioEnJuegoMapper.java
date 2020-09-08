package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Municipio;
import tp.tacs.api.model.MunicipioEnJuegoModel;

import java.util.ArrayList;
import java.util.List;

@Component
public class MunicipioEnJuegoMapper {

    //TODO
    public MunicipioEnJuegoModel toModel(Municipio entity){
        return new MunicipioEnJuegoModel()
                .altura(entity.getAltura().longValue());
    }

    public List<MunicipioEnJuegoModel> mapearMunicipios(List<Municipio> municipios){
        List<MunicipioEnJuegoModel> municipioModels = new ArrayList<>();
        municipios.forEach(municipio -> municipioModels.add(this.toModel(municipio)));
        return municipioModels;
    }
}
