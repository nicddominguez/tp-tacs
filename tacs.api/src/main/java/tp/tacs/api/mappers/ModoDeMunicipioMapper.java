package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Especializacion;
import tp.tacs.api.model.ModoDeMunicipioModel;

@Component
public class ModoDeMunicipioMapper {

    public ModoDeMunicipioModel toModel(Especializacion entity){
        return ModoDeMunicipioModel.fromValue(entity.getNombreAMostrar());
    }
}
