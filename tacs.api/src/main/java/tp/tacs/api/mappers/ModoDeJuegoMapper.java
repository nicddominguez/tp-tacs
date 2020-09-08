package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.ModoDeJuego;
import tp.tacs.api.model.ModoDeJuegoModel;

@Component
public class ModoDeJuegoMapper {

    public ModoDeJuegoModel toEntity(ModoDeJuego entity) {
        return ModoDeJuegoModel.fromValue(entity.getNombreAMostrar());
    }

}
