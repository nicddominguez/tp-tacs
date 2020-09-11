package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.municipio.Defensa;
import tp.tacs.api.dominio.municipio.Especializacion;
import tp.tacs.api.dominio.municipio.Produccion;
import tp.tacs.api.model.ModoDeMunicipioModel;

@Component
public class ModoDeMunicipioMapper {

    public ModoDeMunicipioModel toModel(Especializacion entity){
        return ModoDeMunicipioModel.fromValue(entity.getNombreAMostrar());
    }

    public Especializacion toEntity(ModoDeMunicipioModel model){
        switch (model.toString()){
            case "Defensa":
                return new Defensa();
            case "Produccion":
                return new Produccion();
            default:
                throw new RuntimeException("Unexpected value: " + model.toString());
        }
    }
}
