package tp.tacs.api.mappers;

import org.springframework.stereotype.Component;
import tp.tacs.api.dominio.partida.ModoDeJuego;
import tp.tacs.api.dominio.partida.ModoExtendido;
import tp.tacs.api.dominio.partida.ModoNormal;
import tp.tacs.api.dominio.partida.ModoRapido;
import tp.tacs.api.model.ModoDeJuegoModel;

@Component
public class ModoDeJuegoMapper {

    public ModoDeJuegoModel toModel(ModoDeJuego entity) {
        return ModoDeJuegoModel.fromValue(entity.getNombreAMostrar());
    }

    public ModoDeJuego toEntity(ModoDeJuegoModel model) {
        switch (model.toString()) {
            case "Rapido":
                return new ModoRapido();
            case "Normal":
                return new ModoNormal();
            case "Extendido":
                return new ModoExtendido();
            default:
                throw new RuntimeException("Unexpected value: " + model.toString());
        }
    }
}
