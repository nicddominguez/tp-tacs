package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.model.CoordenadasModel;

@Component
public class CoordenadasMapper {

    public CoordenadasModel toModel(Double latitud, Double longitud){
        return new CoordenadasModel().lat(latitud.floatValue()).lon(longitud.floatValue());
    }
}
